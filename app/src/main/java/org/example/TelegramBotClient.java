package org.example;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TelegramBotClient {
    private final String botToken;
    private final HttpClient client;

    public TelegramBotClient(String botToken) {
        this.botToken = botToken;
        client = HttpClients.createDefault();
    }

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

    public UpdateResponse getUpdates(long offset) {
        String baseUrl = "https://api.telegram.org/bot%s/getUpdates?offset=%s";
        String endPoint = String.format(baseUrl, botToken, offset);

        String requestResponse = getRequestResponse(endPoint);
        return JsonConverter.fromJson(requestResponse, UpdateResponse.class);
    }

    public void sendMessage(long chatId, String text) {
        String baseUrl = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
        String endPoint = String.format(baseUrl, botToken, chatId, text
                .replaceAll(" ", "%20")
                .replaceAll(":", "%3A")
                .replaceAll("\n", "%0A"));

        MessageResponse messageResponse = JsonConverter.fromJson(getRequestResponse(endPoint), MessageResponse.class);
        while (!messageResponse.isOk()) {
            messageResponse = JsonConverter.fromJson(getRequestResponse(endPoint), MessageResponse.class);
        }
    }
}
