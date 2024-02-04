package jwt.application.impl;

import jwt.application.service.jwt.JwtValidatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static jwt.TestConstants.JWT_EXAMPLE;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class ValidationsApplicationImplTest {
    @InjectMocks
    private ValidationsApplicationImpl validationsApplication;

    @Mock
    private JwtValidatorService jwtValidatorService;

    @Test
    void shouldReturnTrueWhenValidationSucceeds() {
        doNothing().when(jwtValidatorService).validate(any());

        final var isValid = validationsApplication.validate(JWT_EXAMPLE);

        assertTrue(isValid);
    }
}