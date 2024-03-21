/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.example;

import com.google.gson.Gson;

public class App {
    public static void main(String[] args) {
        String address = "TMHnkLQseDqUyN9LxBxrgMrYfv24s6aaiM";

        String pathToProperties = "app/src/main/resources/config.properties";
        PropertyExtractor propertyExtractor = new PropertyExtractor(pathToProperties);

        String tronscanApiKey = propertyExtractor.getProperty("tronscanApiKey");

        TronscanClient tronscanClient = new TronscanClient(tronscanApiKey);

        Root root = JsonConverter.fromJson(tronscanClient, address);
        System.out.println(root.toString());
    }
}
