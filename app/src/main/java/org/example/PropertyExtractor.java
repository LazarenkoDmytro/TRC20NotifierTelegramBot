package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Extracts properties from a properties file. This class encapsulates the handling of property files,
 * allowing for easy retrieval of configuration values.
 */
public class PropertyExtractor {
    // Loaded properties from the specified file
    private Properties properties;

    /**
     * Constructs a PropertyExtractor and initializes it with properties loaded from the specified path.
     *
     * @param path The path to the properties file.
     */
    public PropertyExtractor(String path) {
        initializeProperties(path);
    }

    /**
     * Initializes the properties object by loading properties from a file at the given path.
     *
     * @param path The file path from which to load the properties.
     */
    private void initializeProperties(String path) {
        properties = new Properties();
        try (InputStream input = new FileInputStream(path)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the property value associated with the given key.
     *
     * @param key The property key.
     * @return The property value associated with the key, or {@code null} if the key is not found.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
