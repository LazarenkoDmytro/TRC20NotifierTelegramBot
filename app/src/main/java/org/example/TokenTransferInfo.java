package org.example;

public class TokenTransferInfo {
    private String symbol;
    private String to_address;
    private int decimals;
    private long amount_str;

    public String getSymbol() {
        return symbol;
    }

    public String getTo_address() {
        return to_address;
    }

    public int getDecimals() {
        return decimals;
    }

    public long getAmount_str() {
        return amount_str;
    }
}
