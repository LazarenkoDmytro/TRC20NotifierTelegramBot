package org.example;

import com.google.gson.Gson;

public class JsonConverter {
    private static final Gson gson;

    static {
        gson = new Gson();
    }

    public static <T> T fromJson(String jsonString, Class<T> tClass) {
        return gson.fromJson(jsonString, tClass);
    }
}
