package org.example;

import com.google.gson.Gson;

/**
 * Utility class for JSON conversion operations.
 * Leverages the Gson library to convert between JSON strings and Java objects.
 */
public class JsonConverter {
    // Gson instance used for all JSON conversions. Initialized in a static block for reuse.
    private static final Gson gson;

    static {
        gson = new Gson();
    }

    /**
     * Converts a JSON string to an instance of a specified class.
     * This method is generic and can be used to convert JSON to any type of Java object.
     *
     * @param jsonString The JSON string to convert.
     * @param tClass     The class of the object to which the JSON is to be converted.
     * @param <T>        The type of the object.
     * @return An instance of the specified class, populated with data from the JSON string.
     */
    public static <T> T fromJson(String jsonString, Class<T> tClass) {
        return gson.fromJson(jsonString, tClass);
    }
}
