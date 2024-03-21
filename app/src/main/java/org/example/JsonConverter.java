package org.example;

import com.google.gson.Gson;

public class JsonConverter {
    private static final Gson gson;

    static {
        gson = new Gson();
    }

    public static Root fromJson(TronscanClient tronscanClient, String address) {
        String TRC20TransfersList = tronscanClient.getTRC20TransfersList(address);
        return gson.fromJson(TRC20TransfersList, Root.class);
    }
}
