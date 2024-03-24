package org.example;

/**
 * Represents an update received from the Telegram Bot API, which may include a message.
 */
public class Update {
    // Unique identifier for this update
    private long update_id;
    // The message associated with this update
    private Message message;

    /**
     * Retrieves the update's unique identifier.
     *
     * @return The update ID.
     */
    public long getUpdate_id() {
        return update_id;
    }

    /**
     * Retrieves the message associated with this update, if present.
     *
     * @return The {@link Message} object, or {@code null} if no message is associated with this update.
     */
    public Message getMessage() {
        return message;
    }
}
