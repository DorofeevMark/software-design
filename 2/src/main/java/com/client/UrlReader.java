package com.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class UrlReader {
    private final HttpResponse response;

    public UrlReader(String query, ArrayList<NameValuePair> queryParameters) throws URISyntaxException, IOException {
        HttpClient httpClient = HttpClients
                .custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder(query);
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Content-Type", "application/json");
        response = httpClient.execute(httpGet);
        response.getStatusLine().getStatusCode();

    }

    public int getStatusCode() {
        return response.getStatusLine().getStatusCode();
    }

    public String getBody() throws IOException {
        return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
    }
}