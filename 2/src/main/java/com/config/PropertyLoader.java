package com.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
    public static Properties loadProperties(String filename) {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(filename)) {
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            System.err.println(String.format("File '%s' not found", filename));
        } catch (IOException e) {
            System.err.println(String.format("Error while working with the file '%s'", filename));
        }
        return properties;
    }
}
