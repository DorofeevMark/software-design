package com.client;

import com.model.VKResponse;
import com.service.HttpService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StatisticClient {
    private static final Duration DEFAULT_DURATION = Duration.ofHours(1);
    private final Duration duration;
    private final HttpService<VKResponse> httpClient;

    public StatisticClient(HttpService<VKResponse> httpClient) {
        this(httpClient, DEFAULT_DURATION);
    }

    public StatisticClient(HttpService<VKResponse> httpClient, Duration duration) {
        this.httpClient = httpClient;
        this.duration = duration;
    }

    public List<Integer> getStatistic(String hashTag, int hoursCount) {
        if (hoursCount < 1 || hoursCount > 24) {
            throw new IllegalArgumentException("'hoursCount' must be between 1 and 24");
        }
        List<Long> timeIntervals = IntStream
                .range(0, hoursCount + 1)
                .mapToLong(i -> System.currentTimeMillis() / 1000L - (hoursCount + 1 - i) * duration.toSeconds())
                .boxed()
                .collect(Collectors.toList());

        return IntStream.range(0, hoursCount).parallel()
                .mapToObj(i -> {
                    try {
                        return httpClient.query(hashTag, timeIntervals.get(i), timeIntervals.get(i + 1));
                    } catch (URISyntaxException | IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).filter(Objects::nonNull)
                .map(VKResponse::getCount)
                .collect(Collectors.toList());
    }
}
