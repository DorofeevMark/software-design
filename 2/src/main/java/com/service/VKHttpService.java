package com.service;


import com.client.UrlReader;
import com.config.VKConfig;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.VKResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static java.lang.String.format;

public class VKHttpService implements HttpService<VKResponse> {
    private VKConfig config;
    static class ApiMethods {
        public final static String search = "/method/newsfeed.search";
    }

    @Override
    public VKResponse query(String query, long startTime, long endTime) throws IOException, URISyntaxException {
        ArrayList<NameValuePair> queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("access_token", config.getToken()));
        queryParameters.add(new BasicNameValuePair("start_time", String.valueOf(startTime)));
        queryParameters.add(new BasicNameValuePair("end_time", String.valueOf(endTime)));
        queryParameters.add(new BasicNameValuePair("v", config.getVersion()));
        queryParameters.add(new BasicNameValuePair("q", query));
        UrlReader urlReader = new UrlReader(format("https://%s/%s", config.getHost(), ApiMethods.search), queryParameters);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(objectMapper.readTree(urlReader.getBody()).get("response").toString(), VKResponse.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public VKHttpService(VKConfig config) {
        this.config = config;
    }
}
