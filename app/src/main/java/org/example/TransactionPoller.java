package org.example;

import java.util.*;
import java.util.concurrent.*;

public class TransactionPoller {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final TronscanClient tronscanClient;
    private final TelegramBotClient telegramBotClient;
    private final Set<String> addresses;
    private final Map<String, Root> roots;

    public TransactionPoller(TronscanClient tronscanClient, TelegramBotClient telegramBotClient, List<String> addresses) {
        this.tronscanClient = tronscanClient;
        this.telegramBotClient = telegramBotClient;
        this.addresses = new CopyOnWriteArraySet<>(addresses);
        this.roots = new ConcurrentHashMap<>();
    }

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
                    System.out.println("New transaction(s) for address " + address + ":");

                    for (Transaction currentTransaction : difference.reversed()) {
                        messageBuilder.append("\n").append(currentTransaction).append("\n");
                        System.out.println("\n" + currentTransaction + "\n");
                    }

                    String message = messageBuilder.toString();
                    for (long chatId : AddressManager.getReceivers(address)) {
                        telegramBotClient.sendMessage(chatId, message);
                    }

                    roots.put(address, newRoot);
                } else {
                    System.out.println("No new transactions for address " + address);
                }
            }
        };

        scheduler.scheduleAtFixedRate(poller, 30, 30, TimeUnit.SECONDS);
    }

    public void addAddress(String address) {
        addresses.add(address);
        roots.put(address, tronscanClient.getTRC20TransactionsList(address));
    }

    public void removeAddress(String address) {
        addresses.remove(address);
    }
}
