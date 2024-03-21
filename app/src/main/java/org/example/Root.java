package org.example;

import java.util.List;

public final class Root {
    private int total;
    private List<TokenTransfer> token_transfers;

    public int total() {
        return total;
    }

    public List<TokenTransfer> token_transfers() {
        return token_transfers;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Total: " + total);
        for (TokenTransfer token_transfer : token_transfers) {
            stringBuilder.append("\n").append("\n").append(token_transfer.toString());
        }

        return stringBuilder.toString();
    }
}