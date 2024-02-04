package jwt.infrastructure.exception;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Singleton;
import jwt.application.exception.jwtvalidation.base.JwtValidationException;
import jwt.model.JwtValidationResponse;

@Singleton
public class ExceptionHandler implements io.micronaut.http.server.exceptions.ExceptionHandler<JwtValidationException, HttpResponse<JwtValidationResponse>> {

    @Override
    public HttpResponse<JwtValidationResponse> handle(HttpRequest request, JwtValidationException exception) {
        //TODO: loggar erro

        return HttpResponse.ok(new JwtValidationResponse(false));
    }
}