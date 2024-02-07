package jwt.infrastructure.exception;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Singleton;
import jwt.application.exception.jwtvalidation.base.JwtValidationException;
import jwt.infrastructure.monitor.Monitor;
import jwt.infrastructure.monitor.model.Event;
import jwt.infrastructure.monitor.model.LogData;
import jwt.infrastructure.monitor.model.SeverityEnum;
import jwt.model.JwtValidationResponse;

@Singleton
public class ExceptionHandler implements io.micronaut.http.server.exceptions.ExceptionHandler<JwtValidationException, HttpResponse<JwtValidationResponse>> {
    private final Monitor monitor;

    public ExceptionHandler(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public HttpResponse<JwtValidationResponse> handle(HttpRequest request, JwtValidationException exception) {
        final var logData = getLogData(exception);

        monitor.log(logData);

        return HttpResponse.ok(new JwtValidationResponse(true));
    }

    private LogData getLogData(JwtValidationException exception) {
        final var event = new Event(exception.getMessage(),
                SeverityEnum.INFO,
                exception.getClaims().toString()
        );

        return new LogData(event);
    }
}