package org.example;

import java.util.Objects;

public class Transaction {
    private String hash;
    private long timestamp;
    private String ownerAddress;
    private String toAddress;
    private long amount;
    private String tokenTypeSymbol = "TRX";
    private int decimals = 6;

    public String getHash() {
        return hash;
    }

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

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getTokenTypeSymbol() {
        return tokenTypeSymbol;
    }

    public void setTokenTypeSymbol(String tokenTypeSymbol) {
        this.tokenTypeSymbol = tokenTypeSymbol;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    @Override
    public String toString() {
        return "Transaction time: " + getFormattedDate() +
                "\nSender Address: " + getOwnerAddress() +
                "\nReceiver Address: " + getToAddress() +
                "\nAmount: " + (double) getAmount() / Math.pow(10, decimals) + " tokens" +
                "\nToken type: " + getTokenTypeSymbol();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }

        final Transaction other = (Transaction) object;

        return Objects.equals(this.hash, other.hash);
    }
}