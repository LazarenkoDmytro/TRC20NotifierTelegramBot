package org.example;

/**
 * Represents information about a token transfer, including the token symbol, recipient address,
 * token decimals, and the amount transferred.
 */
public class TokenTransferInfo {
    // Symbol of the token transferred
    private String symbol;
    // Address to which the token was transferred
    private String to_address;
    // Number of decimals the token uses
    private int decimals;
    // Amount of the token transferred, as a string to preserve precision
    private long amount_str;

    /**
     * Gets the symbol of the token.
     *
     * @return The symbol of the token.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets the address to which the token was transferred.
     *
     * @return The recipient's address.
     */
    public String getTo_address() {
        return to_address;
    }

    /**
     * Gets the number of decimals for the token's smallest unit.
     *
     * @return The number of decimals.
     */
    public int getDecimals() {
        return decimals;
    }

    /**
     * Gets the amount of the token that was transferred.
     *
     * @return The amount of the token transferred.
     */
    public long getAmount_str() {
        return amount_str;
    }
}
