package jwt.application.impl;

import io.micronaut.context.annotation.Bean;
import jwt.application.ValidationsApplication;
import jwt.application.service.jwt.JwtValidatorService;

@Bean
public class ValidationsApplicationImpl implements ValidationsApplication {

    private final JwtValidatorService jwtValidatorService;

    public ValidationsApplicationImpl(JwtValidatorService jwtValidatorService) {
        this.jwtValidatorService = jwtValidatorService;
    }

    @Override
    public boolean validate(String jwt) {
        jwtValidatorService.validate(jwt);

        return true;
    }
}
