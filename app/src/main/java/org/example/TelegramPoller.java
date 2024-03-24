package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TelegramPoller {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final TelegramBotClient telegramBotClient;
    private final TransactionPoller transactionPoller;
    private long lastUpdateId = 0;

    public TelegramPoller(TelegramBotClient telegramBotClient, TransactionPoller transactionPoller) {
        this.telegramBotClient = telegramBotClient;
        this.transactionPoller = transactionPoller;
    }

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

                        if (text.equals("/start")) {
                            telegramBotClient.sendMessage(id, "Please enter your address:");
                        } else if (text.startsWith("/trackAddress ")) {
                            String address = text.substring(14);

                            if (!address.matches("T[A-Za-z1-9]{33}")) {
                                telegramBotClient.sendMessage(id, "The address is incorrect. Try again");
                            } else {
                                telegramBotClient.sendMessage(id, "Successfully tracking for " + address);
                                transactionPoller.addAddress(address);
                                AddressManager.addReceiver(address, id);
                            }
                        }
                    }
                }

                lastUpdateId = update.getUpdate_id();
            }
        };

        scheduler.scheduleAtFixedRate(poller, 0, 5, TimeUnit.SECONDS);
    }
}
