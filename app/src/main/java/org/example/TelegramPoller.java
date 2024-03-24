package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Handles polling of Telegram updates and processes commands received from users.
 */
public class TelegramPoller {
    // Executor service for scheduling the polling task at fixed intervals
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    // Client for interacting with the Telegram Bot API
    private final TelegramBotClient telegramBotClient;
    // Client for managing transactions and associated addresses
    private final TransactionPoller transactionPoller;
    // Tracks the ID of the last update processed to avoid repeating updates
    private long lastUpdateId = 0;

    /**
     * Constructs a TelegramPoller with the specified TelegramBotClient and TransactionPoller.
     *
     * @param telegramBotClient The client used for interacting with the Telegram Bot API.
     * @param transactionPoller The poller used for managing transactions.
     */
    public TelegramPoller(TelegramBotClient telegramBotClient, TransactionPoller transactionPoller) {
        this.telegramBotClient = telegramBotClient;
        this.transactionPoller = transactionPoller;
    }

    /**
     * Starts the polling of Telegram updates. New updates are checked every 5 seconds.
     * Commands from users are processed and appropriate actions are taken based on the command received.
     */
    public void startPolling() {
        final Runnable poller = () -> {
            UpdateResponse updateResponse = telegramBotClient.getUpdates(lastUpdateId + 1);

            for (Update update : updateResponse.getResult()) {
                Message message = update.getMessage();
                String text;

                if (message != null && (text = message.getText()) != null) {
                    Chat chat = message.getChat();

                    if (chat != null) {
                        long id = chat.getId();

                        // Process commands from users and take actions accordingly
                        if (text.equals("/start")) {
                            telegramBotClient.sendMessage(id, "Please enter your address:");
                        } else if (text.startsWith("/trackAddress ")) {
                            // Handle address tracking
                            String address = text.substring(14);

                            if (!address.matches("T[A-Za-z1-9]{33}")) {
                                telegramBotClient.sendMessage(id, "The address is incorrect. Try again");
                            } else {
                                telegramBotClient.sendMessage(id, "Successfully tracking the " + address);
                                transactionPoller.addAddress(address);
                                AddressManager.addReceiver(address, id);
                            }
                        } else if (text.startsWith("/untrackAddress ")) {
                            // Handle address untracking
                            String address = text.substring(16);

                            if (!address.matches("T[A-Za-z1-9]{33}")) {
                                telegramBotClient.sendMessage(id, "The address is incorrect. Try again");
                            } else {
                                telegramBotClient.sendMessage(id, "Successfully untracking the " + address);
                                transactionPoller.removeAddress(address);
                                AddressManager.removeReceiver(address, id);
                            }
                        }
                    }
                }

                lastUpdateId = update.getUpdate_id();
            }
        };

        // Schedule the polling task to run every 5 seconds
        scheduler.scheduleAtFixedRate(poller, 0, 5, TimeUnit.SECONDS);
    }
}
