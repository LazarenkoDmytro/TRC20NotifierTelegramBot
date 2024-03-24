package org.example;

import java.util.List;

/**
 * Represents a response from the Telegram Bot API containing a list of {@link Update} objects.
 */
public class UpdateResponse {
    // List of updates received in the response
    private List<Update> result;

    /**
     * Retrieves the list of updates contained in this response.
     *
     * @return A list of {@link Update} objects representing the updates received.
     */
    public List<Update> getResult() {
        return result;
    }
}
