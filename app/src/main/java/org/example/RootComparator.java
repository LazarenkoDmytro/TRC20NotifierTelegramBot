package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for comparing two instances of {@link Root} to determine the difference in their data.
 * Specifically, it identifies new transactions that have occurred between two different states of data.
 */
public class RootComparator {
    /**
     * Compares two {@link Root} instances and returns a list of {@link Transaction} objects that are present in the
     * newRoot but not in the oldRoot.
     *
     * @param oldRoot The previous state of the Root object, may be {@code null} if no previous state exists.
     * @param newRoot The current state of the Root object, may be {@code null} if no data is available.
     * @return A list of {@link Transaction} objects that are new in the newRoot compared to the oldRoot. Returns
     * an empty list if newRoot is {@code null} or if there are no new transactions.
     */
    public static List<Transaction> getDifference(Root oldRoot, Root newRoot) {
        if (newRoot == null) {
            return List.of();
        }

        if (oldRoot == null) {
            return newRoot.getData();
        }

        List<Transaction> difference = new ArrayList<>();
        List<Transaction> oldRootData = oldRoot.getData();
        List<Transaction> newRootData = newRoot.getData();

        // Identify transactions that are only present in the new root.
        for (Transaction transaction : newRootData) {
            if (!oldRootData.contains(transaction)) {
                difference.add(transaction);
            }
        }

        return difference;
    }
}
