package org.example;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TransactionPoller {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final TronscanClient tronscanClient;
    private final String address;
    private Root root;

    public TransactionPoller(TronscanClient tronscanClient, String address) {
        this.tronscanClient = tronscanClient;
        this.address = address;
    }

    public void startPolling() {
        root = tronscanClient.getTRC20TransactionsList(address);

        final Runnable poller = () -> {
            Root newRoot = tronscanClient.getTRC20TransactionsList(address);

            List<Transaction> difference = RootComparator.getDifference(root, newRoot);
            if (difference.isEmpty()) {
                System.out.println("No difference yet\n");
            } else {
                System.out.println("New transaction(s):");

                ListIterator<Transaction> listIterator = difference.listIterator(difference.size());
                while (listIterator.hasPrevious()) {
                    Transaction currentTransaction = listIterator.previous();

                    if (currentTransaction.getAmount() == 0) {
                        TransactionInfo transactionInfo = tronscanClient.getTransactionInfo(currentTransaction.getHash());
                        TokenTransferInfo tokenTransferInfo = transactionInfo.getTokenTransferInfo();

                        currentTransaction.setToAddress(tokenTransferInfo.getTo_address());
                        currentTransaction.setAmount(tokenTransferInfo.getAmount_str());
                        currentTransaction.setTokenTypeSymbol(tokenTransferInfo.getSymbol());
                        currentTransaction.setDecimals(tokenTransferInfo.getDecimals());
                    }

                    System.out.println("\n" + currentTransaction + "\n");
                }

                root = newRoot;
            }
        };

        scheduler.scheduleAtFixedRate(poller, 30, 30, TimeUnit.SECONDS);
    }
}