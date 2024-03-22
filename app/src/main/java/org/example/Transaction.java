package org.example;

public final class Transaction {
    private long timestamp;
    private String ownerAddress;
    private String toAddress;
    private long amount;

    public long getTimestamp() {
        return timestamp;
    }

    public String getFormattedDate() {
        return DateUtils.formatTimestamp(timestamp);
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction time: " + getFormattedDate() +
                "\nOwner Address: " + ownerAddress +
                "\nTo Address: " + toAddress +
                "\nAmount: " + (double) amount / 1e6;
    }
}