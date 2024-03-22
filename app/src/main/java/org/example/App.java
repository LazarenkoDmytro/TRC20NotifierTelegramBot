/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.example;

public class App {
    public static void main(String[] args) {
        String address = "TEJiTDnQ3AbtvTUKKpRRqmvBqGSo29Gw16";

        String pathToProperties = "app/src/main/resources/config.properties";
        PropertyExtractor propertyExtractor = new PropertyExtractor(pathToProperties);

        String tronscanApiKey = propertyExtractor.getProperty("tronscanApiKey");

        TronscanClient tronscanClient = new TronscanClient(tronscanApiKey);

        Root root = tronscanClient.getTRC20TransfersList(address);
        System.out.println(root.toString());
    }
}
