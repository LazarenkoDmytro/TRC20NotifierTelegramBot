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

    public String getTRC20TransfersList(String address) {
        String baseUrl = "https://apilist.tronscanapi.com/api/token_trc20/transfers?relatedAddress=%s";
        String endPoint = String.format(baseUrl, address);
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
}
