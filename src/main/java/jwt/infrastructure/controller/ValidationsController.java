package jwt.infrastructure.controller;

import io.micronaut.http.annotation.Controller;
import jwt.application.ValidationsApplication;
import jwt.controller.ValidationsApi;
import jwt.model.JwtValidationResponse;

@Controller
public class ValidationsController implements ValidationsApi {

    private final ValidationsApplication validationsApplication;

    public ValidationsController(ValidationsApplication validationsApplication) {
        this.validationsApplication = validationsApplication;
    }

    @Override
    public JwtValidationResponse validate(String jwt) {
        final var isValid = validationsApplication.validate(jwt);

        return new JwtValidationResponse(isValid);
    }
}
