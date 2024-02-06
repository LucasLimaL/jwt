package jwt.infrastructure.monitor;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Singleton;
import jwt.infrastructure.monitor.model.LogData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Singleton
public class Monitor {

    private static final Logger LOG = LoggerFactory.getLogger(Monitor.class);

    private final HttpClient httpClient = HttpClient.newBuilder().build();

    @Value("${splunk-cloud.base-url}")
    private String splunk222;
    @Value("${splunk-cloud.http-event-service.port}")
    private String splunkHttpEventPort;
    @Value("${splunk-cloud.http-event-service.endpoint}")
    private String splunkHttpEventEndpoint;


    public void Log(LogData logData) {
        try {
            final var httpBodyRequest = HttpRequest.newBuilder()
                    .uri(URI.create(splunk222 + splunkHttpEventPort + splunkHttpEventEndpoint))
                    .header("Authorization", "Splunk 7c416f40-4d01-40ef-b61b-9f82186a606b")
                    .POST(HttpRequest.BodyPublishers.ofString(logData.toString()))
                    .build();

            final var response = httpClient.send(httpBodyRequest, HttpResponse.BodyHandlers.ofString());

            if (requestHasFailed(response))
                LOG.error("Unable to send logs to Splunk. Response: " + response);

        } catch (IOException | InterruptedException e) {
            LOG.error("Error sending request to Splunk. ExceptionMessage: " + e.getMessage());
        }
    }

    private static boolean requestHasFailed(HttpResponse<String> response) {
        return response.statusCode() != HttpStatus.OK.getCode();
    }
}
