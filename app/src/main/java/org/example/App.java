package org.example;

import java.util.ArrayList;

/**
 * Main application entry point for a system that integrates with Tronscan and Telegram APIs.
 * Initializes clients for Tronscan and Telegram based on API keys and starts polling services.
 */
public class App {
    public static void main(String[] args) {
        // Path to the configuration properties file
        String pathToProperties = "app/src/main/resources/config.properties";
        // Extract properties from the file
        PropertyExtractor propertyExtractor = new PropertyExtractor(pathToProperties);

        // Extract Tronscan API key and initialize its client
        String tronscanApiKey = propertyExtractor.getProperty("tronscanApiKey");
        TronscanClient tronscanClient = new TronscanClient(tronscanApiKey);

        // Extract Telegram Bot API key and initialize its client
        String telegramBotApiKey = propertyExtractor.getProperty("telegramBotApiKey");
        TelegramBotClient telegramBotClient = new TelegramBotClient(telegramBotApiKey);

        // Initialize and start the transaction polling service
        TransactionPoller transactionPoller = new TransactionPoller(tronscanClient, telegramBotClient, new ArrayList<>());
        transactionPoller.startPolling();

        // Initialize and start the Telegram polling service
        TelegramPoller telegramPoller = new TelegramPoller(telegramBotClient, transactionPoller);
        telegramPoller.startPolling();
    }
}
