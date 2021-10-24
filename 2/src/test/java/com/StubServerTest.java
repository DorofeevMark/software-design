package com;

import com.client.UrlReader;
import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.method;

public class StubServerTest {
    private static final int PORT = 3535;

    private UrlReader urlReader;

    @Test
    public void goodTest() {
        withStubServer(s -> {
                    whenHttp(s)
                            .match(method(Method.GET))
                            .then(stringContent("result"));

                    try {
                        urlReader = new UrlReader(String.format("http://localhost:%d/test", PORT), new ArrayList<>());
                    } catch (URISyntaxException | IOException e) {
                        e.printStackTrace();
                    }

                    String body = null;

                    try {
                        body = urlReader.getBody();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Assert.assertEquals(body, "result");
                }
        );
    }

    @Test()
    public void test404() {
        withStubServer(s -> {
                    whenHttp(s)
                            .match(method(Method.GET))
                            .then(status(HttpStatus.BAD_REQUEST_400));

                    try {
                        urlReader = new UrlReader(String.format("http://localhost:%d/test", PORT), new ArrayList<>());
                    } catch (URISyntaxException | IOException e) {
                        e.printStackTrace();
                    }

                    Assert.assertEquals(urlReader.getStatusCode(), 400);
                }
        );
    }

    private void withStubServer(Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(StubServerTest.PORT).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }
}
