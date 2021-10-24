package com;

import com.client.StatisticClient;
import com.model.VKResponse;
import com.service.HttpService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class StatisticClientTest {
    private StatisticClient statisticClient;

    @Mock
    private HttpService<VKResponse> httpService;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
        statisticClient = new StatisticClient(httpService);
    }

    @Test
    public void testEmptyResponse() throws IOException, URISyntaxException {
        when(httpService.query(anyString(), anyLong(), anyLong())).thenReturn(new VKResponse(0));
        List<Integer> results = statisticClient.getStatistic("tag", 1);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(0), results.get(0));
    }

    @Test
    public void testSomeResponse() throws IOException, URISyntaxException {
        when(httpService.query(anyString(), anyLong(), anyLong())).thenReturn(new VKResponse(42));
        List<Integer> results = statisticClient.getStatistic("tag", 1);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(42), results.get(0));
    }

    @Test
    public void testTwelveHoursResponse() throws IOException, URISyntaxException {
        when(httpService.query(anyString(), anyLong(), anyLong())).thenReturn(new VKResponse(42));
        List<Integer> results = statisticClient.getStatistic("tag", 12);
        Assert.assertEquals(12, results.size());
        Assert.assertEquals(Integer.valueOf(42), results.get(1));
    }
}
