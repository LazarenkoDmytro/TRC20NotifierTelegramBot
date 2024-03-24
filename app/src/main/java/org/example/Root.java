package org.example;

import java.util.List;

/**
 * Represents the root of a data structure, used for deserializing complex JSON responses.
 * Encapsulates a list of {@link Transaction} objects.
 */
public final class Root {
    // A list of Transaction objects contained within this root object.
    private List<Transaction> data;

    /**
     * Gets the data held by this Root object, specifically a list of transactions.
     *
     * @return A list of {@link Transaction} objects contained within this root.
     */
    public List<Transaction> getData() {
        return data;
    }

    /**
     * Generates a string representation of this Root object, including all its contained transactions.
     *
     * @return A string representation of the Root object and its contents.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Transaction token_transfer : data) {
            stringBuilder.append("\n").append("\n").append(token_transfer.toString());
        }

        return stringBuilder.toString();
    }
}
