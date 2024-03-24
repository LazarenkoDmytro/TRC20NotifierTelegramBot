package org.example;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Client for interacting with the Telegram Bot API, allowing for sending messages and receiving updates.
 */
public class TelegramBotClient {
    // Token for accessing the Telegram Bot API
    private final String botToken;
    // HttpClient for making HTTP requests
    private final HttpClient client;

    /**
     * Constructs a new TelegramBotClient using the given bot token.
     *
     * @param botToken The token provided by BotFather for bot operations.
     */
    public TelegramBotClient(String botToken) {
        this.botToken = botToken;
        client = HttpClients.createDefault();
    }

    /**
     * Helper method to execute a GET request and retrieve the response body as a string.
     *
     * @param endPoint The complete URL to which the GET request is sent.
     * @return The response body as a string.
     */
    private String getRequestResponse(String endPoint) {
        StringBuilder responseBody = new StringBuilder();

        try {
            HttpGet request = new HttpGet(endPoint);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(client.execute(request).getEntity().getContent()))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseBody.toString();
    }

    /**
     * Fetches new updates from Telegram.
     *
     * @param offset The offset to control which updates should be fetched. Typically, the last update ID received + 1.
     * @return An UpdateResponse object containing the updates.
     */
    public UpdateResponse getUpdates(long offset) {
        String baseUrl = "https://api.telegram.org/bot%s/getUpdates?offset=%s";
        String endPoint = String.format(baseUrl, botToken, offset);

        String requestResponse = getRequestResponse(endPoint);
        return JsonConverter.fromJson(requestResponse, UpdateResponse.class);
    }

    /**
     * Sends a text message to a specified chat on Telegram.
     *
     * @param chatId The ID of the chat to send the message to.
     * @param text   The text of the message to be sent.
     */
    public void sendMessage(long chatId, String text) {
        String baseUrl = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
        String endPoint = String.format(baseUrl, botToken, chatId, text
                .replaceAll(" ", "%20")
                .replaceAll(":", "%3A")
                .replaceAll("\n", "%0A"));

        MessageResponse messageResponse = JsonConverter.fromJson(getRequestResponse(endPoint), MessageResponse.class);
        // Retry sending the message until successful
        while (!messageResponse.isOk()) {
            messageResponse = JsonConverter.fromJson(getRequestResponse(endPoint), MessageResponse.class);
        }
    }
}
