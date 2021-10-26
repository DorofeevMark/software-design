package com.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VKResponse {
    private final int count;

    public VKResponse() {
        this(0);
    }

    @JsonCreator
    public VKResponse(@JsonProperty("total_count") int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }


    public static void main(String[] args) throws JsonProcessingException {
        String body = "{\"items\":[],\"count\":900,\"total_count\":900}";
        ObjectMapper objectMapper = new ObjectMapper();
        VKResponse response = objectMapper.readValue(body, VKResponse.class);
        System.out.println(response.getCount());
    }
}
