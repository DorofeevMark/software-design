package com.config;

public class PropertyNotFoundException extends RuntimeException {
    public PropertyNotFoundException(String propertyName) {
        super(String.format("Property '%s' not found", propertyName));
    }
}
