package org.example;

import java.util.Objects;

/**
 * Represents a blockchain transaction, encapsulating details such as transaction hash,
 * timestamp, sender and receiver addresses, amount transferred, and the token symbol.
 */
public class Transaction {
    // Transaction unique identifier
    private String hash;
    // Transaction timestamp
    private long timestamp;
    // Sender's wallet address
    private String ownerAddress;
    // Receiver's wallet address
    private String toAddress;
    // Amount transferred in the transaction
    private long amount;
    // Symbol of the token type
    private String tokenTypeSymbol = "TRX";
    // Decimal precision of the token amount
    private int decimals = 6;

    // Standard getters and setters for each field
    public String getHash() {
        return hash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns a formatted date string from the transaction timestamp.
     *
     * @return Formatted date string.
     */
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

    /**
     * Returns a string representation of the transaction details.
     *
     * @return String representation of the transaction.
     */
    @Override
    public String toString() {
        return "Transaction time: " + getFormattedDate() +
                "\nSender Address: " + getOwnerAddress() +
                "\nReceiver Address: " + getToAddress() +
                "\nAmount: " + (double) getAmount() / Math.pow(10, decimals) + " tokens" +
                "\nToken type: " + getTokenTypeSymbol();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param object the reference object with which to compare.
     * @return true if this object is the same as the object argument; false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }

        final Transaction other = (Transaction) object;

        return Objects.equals(this.hash, other.hash);
    }
}