/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.example;

import com.google.gson.Gson;

public class App {
    public static void main(String[] args) {
        String pathToProperties = "app/src/main/resources/config.properties";
        PropertyExtractor propertyExtractor = new PropertyExtractor(pathToProperties);

        String tronscanApiKey = propertyExtractor.getProperty("tronscanApiKey");

        TronscanClient tronscanClient = new TronscanClient(tronscanApiKey);
        String TRC20TransfersList = tronscanClient.getTRC20TransfersList("TMHnkLQseDqUyN9LxBxrgMrYfv24s6aaiM");

        Gson gson = new Gson();
        Root root = gson.fromJson(TRC20TransfersList, Root.class);
        System.out.println(root.toString());
    }
}
