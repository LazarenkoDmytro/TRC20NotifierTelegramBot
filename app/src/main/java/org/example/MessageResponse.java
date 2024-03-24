package org.example;

/**
 * Represents the response of a message operation, indicating success or failure.
 * This simple class is used to parse a JSON query.
 */
public class MessageResponse {
    // Indicator of operation success
    private boolean ok;

    /**
     * Checks if the operation represented by this response was successful.
     *
     * @return {@code true} if the operation was successful, {@code false} otherwise.
     */
    public boolean isOk() {
        return ok;
    }
}
