package org.example;

import java.util.List;

public final class Root {
    private List<Transaction> data;

    public List<Transaction> getData() {
        return data;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Transaction token_transfer : data) {
            stringBuilder.append("\n").append("\n").append(token_transfer.toString());
        }

        return stringBuilder.toString();
    }
}