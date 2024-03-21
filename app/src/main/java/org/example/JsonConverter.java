package org.example;

import com.google.gson.Gson;

public class JsonConverter {
    private static final Gson gson;

    static {
        gson = new Gson();
    }

    public static Root fromJson(String responseBody) {
        return gson.fromJson(responseBody, Root.class);
    }
}
