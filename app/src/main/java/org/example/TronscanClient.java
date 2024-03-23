package org.example;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TronscanClient {
    private final String apiKey;
    private final HttpClient client;

    public TronscanClient(String apiKey) {
        this.apiKey = apiKey;
        client = HttpClients.createDefault();
    }

    private String getRequestResponse(String endPoint) {
        StringBuilder responseBody = new StringBuilder();

        try {
            HttpGet request = new HttpGet(endPoint);
            request.addHeader("TRON-PRO-API-KEY", apiKey);

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

    public Root getTRC20TransactionsList(String address) {
        String baseUrl = "https://apilist.tronscanapi.com/api/transaction?sort=%s&limit=%s&address=%s";
        String endPoint = String.format(baseUrl, "-timestamp", 50, address);

        String requestResponse = getRequestResponse(endPoint);
        return JsonConverter.fromJson(requestResponse, Root.class);
    }

    public TransactionInfo getTransactionInfo(String hash) {
        String baseUrl = "https://apilist.tronscanapi.com/api/transaction-info?hash=%s";
        String endPoint = String.format(baseUrl, hash);

        String requestResponse = getRequestResponse(endPoint);
        return JsonConverter.fromJson(requestResponse, TransactionInfo.class);
    }
}
