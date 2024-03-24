package org.example;

/**
 * Represents a message within a chat, containing the chat details and the message text.
 * This simple class is used to parse a JSON query.
 */
public class Message {
    // The chat to which this message belongs
    private Chat chat;
    // The text content of the message
    private String text;

    /**
     * Retrieves the chat associated with this message.
     *
     * @return The {@link Chat} object representing the chat session of this message.
     */
    public Chat getChat() {
        return chat;
    }

    /**
     * Retrieves the text content of the message.
     *
     * @return The text content of the message.
     */
    public String getText() {
        return text;
    }
}
