package com.service;

import java.io.IOException;
import java.net.URISyntaxException;

public interface HttpService<R> {
    R query(String query, long startTime, long endTime) throws URISyntaxException, IOException;
}