package jwt.infrastructure.monitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Singleton;
import jwt.infrastructure.monitor.model.LogData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Arrays;

@Singleton
public class Monitor {

    private static final Logger LOG = LoggerFactory.getLogger(Monitor.class);
    @Value("${splunk-cloud.base-url}")
    private String splunk222;
    @Value("${splunk-cloud.http-event-service.port}")
    private String splunkHttpEventPort;
    @Value("${splunk-cloud.http-event-service.endpoint}")
    private String splunkHttpEventEndpoint;

    private final HttpClient httpClient;

    private static final String TLS = "TLS";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    public Monitor() throws NoSuchAlgorithmException, KeyManagementException {
        var sslContext = SSLContext.getInstance(TLS);
        sslContext.init(null, new TrustManager[]{getTrustManagerForAllSSLCertificates()}, new SecureRandom());

        httpClient = HttpClient.newBuilder()
                .sslContext(sslContext)
                .build();
    }

    public void log(LogData logData) {
        try {
            final var httpBodyRequest = HttpRequest.newBuilder()
                    .uri(URI.create(splunk222 + splunkHttpEventPort + splunkHttpEventEndpoint))
                    .header(AUTHORIZATION_HEADER, "Splunk 7c416f40-4d01-40ef-b61b-9f82186a606b")
                    .POST(HttpRequest.BodyPublishers.ofString(getJson(logData)))
                    .build();

            final var response = httpClient.send(httpBodyRequest, HttpResponse.BodyHandlers.ofString());

            if (requestHasFailed(response))
                LOG.error("Unable to send logs to Splunk. Response: " + response);

        } catch (JsonProcessingException e) {
            LOG.error("Error parsing logData to JSON. ExceptionMessage: " + e.getMessage() +
                    "StackTrace: " + Arrays.toString(e.getStackTrace()));
        } catch (IOException | InterruptedException e) {
            LOG.error("Error sending request to Splunk. ExceptionMessage: " + e.getMessage() +
                    "StackTrace: " + Arrays.toString(e.getStackTrace()));
        }
    }

    private String getJson(LogData logData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(logData);
    }

    private static boolean requestHasFailed(HttpResponse<String> response) {
        return response.statusCode() != HttpStatus.OK.getCode();
    }

    private X509ExtendedTrustManager getTrustManagerForAllSSLCertificates() {
        return new X509ExtendedTrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) {
            }
        };
    }
}