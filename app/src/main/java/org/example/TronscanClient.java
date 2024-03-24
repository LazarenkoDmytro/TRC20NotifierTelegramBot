package org.example;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Client for interacting with the Tronscan API to fetch transaction data.
 */
public class TronscanClient {
    // API key for Tronscan API authentication
    private final String apiKey;
    // HTTP client for making requests
    private final HttpClient client;

    /**
     * Constructs a new TronscanClient with the specified API key.
     *
     * @param apiKey The API key for authenticating with the Tronscan API.
     */
    public TronscanClient(String apiKey) {
        this.apiKey = apiKey;
        client = HttpClients.createDefault();
    }

    /**
     * Sends an HTTP GET request to the specified endpoint and returns the response body as a string.
     *
     * @param endPoint The URL endpoint to which the GET request is sent.
     * @return The response body as a string.
     */
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

    /**
     * Fetches a list of TRC20 transactions for a specific address from the Tronscan API.
     *
     * @param address The blockchain address for which to retrieve transactions.
     * @return A {@link Root} object containing the list of transactions.
     */
    public Root getTRC20TransactionsList(String address) {
        String baseUrl = "https://apilist.tronscanapi.com/api/transaction?sort=%s&limit=%s&address=%s";
        String endPoint = String.format(baseUrl, "-timestamp", 50, address);

        String requestResponse = getRequestResponse(endPoint);
        return JsonConverter.fromJson(requestResponse, Root.class);
    }

    /**
     * Fetches transaction information for a given transaction hash.
     *
     * @param hash The hash of the transaction to retrieve information for.
     * @return A {@link TransactionInfo} object containing details of the transaction.
     */
    public TransactionInfo getTransactionInfo(String hash) {
        String baseUrl = "https://apilist.tronscanapi.com/api/transaction-info?hash=%s";
        String endPoint = String.format(baseUrl, hash);

        String requestResponse = getRequestResponse(endPoint);
        return JsonConverter.fromJson(requestResponse, TransactionInfo.class);
    }
}
