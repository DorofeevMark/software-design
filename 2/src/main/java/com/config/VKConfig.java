package com.config;

import java.util.Properties;

public class VKConfig {
    private static final String HOST_PROPERTY_NAME = "api.vk.host";
    private static final String TOKEN_PROPERTY_NAME = "api.vk.token";
    private static final String VERSION_PROPERTY_NAME = "api.vk.version";

    private final String host;
    private final String token;
    private final String version;

    public VKConfig(Properties properties) {
        host = getOrThrow(properties, HOST_PROPERTY_NAME);
        token = getOrThrow(properties, TOKEN_PROPERTY_NAME);
        version = getOrThrow(properties, VERSION_PROPERTY_NAME);
    }


    private String getOrThrow(Properties properties, String propertyName) {
        String value = properties.getProperty(propertyName);
        if (value == null) {
            throw new PropertyNotFoundException(propertyName);
        }
        return value;
    }

    public String getToken() {
        return token;
    }

    public String getVersion() {
        return version;
    }

    public String getHost() {
        return host;
    }
}
