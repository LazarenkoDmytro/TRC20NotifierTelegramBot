package org.example;

import java.util.*;
import java.util.concurrent.*;

/**
 * Polls for new transactions on specified addresses and notifies subscribed users via Telegram.
 */
public class TransactionPoller {
    // Executor service for scheduling the polling task at fixed intervals
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    // Client for interacting with the blockchain to fetch transactions
    private final TronscanClient tronscanClient;
    // Client for sending notifications via Telegram
    private final TelegramBotClient telegramBotClient;
    // A set of addresses to monitor for new transactions
    private final Set<String> addresses;
    // A map to keep track of the last known state of transactions for each address
    private final Map<String, Root> roots;

    /**
     * Constructs a new TransactionPoller.
     *
     * @param tronscanClient The client used for blockchain interactions.
     * @param telegramBotClient The client used for Telegram notifications.
     * @param addresses Initial list of addresses to monitor.
     */
    public TransactionPoller(TronscanClient tronscanClient, TelegramBotClient telegramBotClient, List<String> addresses) {
        this.tronscanClient = tronscanClient;
        this.telegramBotClient = telegramBotClient;
        this.addresses = new CopyOnWriteArraySet<>(addresses);
        this.roots = new ConcurrentHashMap<>();
    }

    /**
     * Starts the process of polling for new transactions at fixed intervals. When new transactions are detected,
     * subscribed users are notified via Telegram.
     */
    public void startPolling() {
        for (String address : addresses) {
            roots.put(address, tronscanClient.getTRC20TransactionsList(address));
        }

        final Runnable poller = () -> {
            for (String address : addresses) {
                Root oldRoot = roots.get(address);
                Root newRoot = tronscanClient.getTRC20TransactionsList(address);

                List<Transaction> difference = RootComparator.getDifference(oldRoot, newRoot);
                if (!difference.isEmpty()) {
                    String ending = difference.size() == 1 ? "" : "s";
                    StringBuilder messageBuilder = new StringBuilder("New transaction" + ending + " for address " + address + ":\n");

                    for (Transaction currentTransaction : difference.reversed()) {
                        messageBuilder.append("\n").append(currentTransaction).append("\n");
                    }

                    String message = messageBuilder.toString();
                    for (long chatId : AddressManager.getReceivers(address)) {
                        telegramBotClient.sendMessage(chatId, message);
                    }

                    roots.put(address, newRoot);
                }
            }
        };

        scheduler.scheduleAtFixedRate(poller, 30, 30, TimeUnit.SECONDS);
    }

    /**
     * Adds an address to the list of addresses being monitored for transactions.
     *
     * @param address The address to monitor.
     */
    public void addAddress(String address) {
        addresses.add(address);
        roots.put(address, tronscanClient.getTRC20TransactionsList(address));
    }

    /**
     * Removes an address from the list of addresses being monitored.
     *
     * @param address The address to stop monitoring.
     */
    public void removeAddress(String address) {
        addresses.remove(address);
    }
}
