package org.example;

import java.util.ArrayList;
import java.util.List;

public class RootComparator {
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

        for (Transaction transaction : newRootData) {
            if (!oldRootData.contains(transaction)) {
                difference.add(transaction);
            }
        }

        return difference;
    }
}
