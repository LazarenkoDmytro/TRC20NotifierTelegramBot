package org.example;

public final class TokenTransfer {
    private String transaction_id;
    private long block_ts;
    private String from_address;
    private String to_address;
    private long quant;

    public String transaction_id() {
        return transaction_id;
    }

    public long block_ts() {
        return block_ts;
    }

    public String from_address() {
        return from_address;
    }

    public String to_address() {
        return to_address;
    }

    public long quant() {
        return quant;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transaction_id +
                "\nTransaction time: " + block_ts() +
                "\nFrom Address: " + from_address() +
                "\nTo Address: " + to_address() +
                "\nQuantity: " + (double) quant / 1e6 + "$";
    }
}