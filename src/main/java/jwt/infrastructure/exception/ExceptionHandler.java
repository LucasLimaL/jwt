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

import java.time.LocalDateTime;

@Singleton
public class ExceptionHandler implements io.micronaut.http.server.exceptions.ExceptionHandler<JwtValidationException, HttpResponse<JwtValidationResponse>> {
    private final Monitor monitor;

    public ExceptionHandler(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public HttpResponse<JwtValidationResponse> handle(HttpRequest request, JwtValidationException exception) {
        final var logData = getLogData(exception);

        monitor.Log(logData);

        return HttpResponse.ok(new JwtValidationResponse(false));
    }

    private LogData getLogData(JwtValidationException exception) {
        //TODO: formatar datetime
        final var event = new Event(exception.getMessage(), SeverityEnum.INFO, LocalDateTime.now().toString(), exception.getClaims());

        return new LogData(event);
    }
}