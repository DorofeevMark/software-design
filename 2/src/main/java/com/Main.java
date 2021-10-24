package com;

import com.client.StatisticClient;
import com.service.HttpService;
import com.service.VKHttpService;
import com.config.PropertyLoader;
import com.config.VKConfig;
import com.model.VKResponse;

public class Main {
    private static final String PROPERTIES = "src/main/resources/application.properties";

    public static void main(String[] args) {
        HttpService<VKResponse> client = new VKHttpService(new VKConfig(PropertyLoader.loadProperties(PROPERTIES)));
        StatisticClient statistics = new StatisticClient(client);
        statistics.getStatistic("software", 12).forEach(System.out::println);
    }
}
