package org.example;

/**
 * Encapsulates information about a transaction, primarily focusing on the token transfer aspect.
 */
public class TransactionInfo {
    // Detailed information about the token transfer within the transaction
    private TokenTransferInfo tokenTransferInfo;

    /**
     * Retrieves the token transfer information associated with this transaction.
     *
     * @return The {@link TokenTransferInfo} object representing the details of the token transfer.
     */
    public TokenTransferInfo getTokenTransferInfo() {
        return tokenTransferInfo;
    }
}
