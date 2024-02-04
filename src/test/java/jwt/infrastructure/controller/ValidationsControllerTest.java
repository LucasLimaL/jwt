package jwt.infrastructure.controller;

import jwt.application.ValidationsApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static jwt.TestConstants.JWT_EXAMPLE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidationsControllerTest {
    @InjectMocks
    private ValidationsController validationsController;

    @Mock
    private ValidationsApplication validationsApplication;

    @Test
    void shouldVerifyValidateIsCalled() {
        when(validationsApplication.validate(any())).thenReturn(true);

        validationsController.validate(JWT_EXAMPLE);

        verify(validationsApplication, times(1)).validate(any());
    }

    @Test
    void shouldReturnIsValidFalseWhenValidationFails() {
        when(validationsApplication.validate(any())).thenReturn(false);

        final var jwtValidationResponse = validationsController.validate(JWT_EXAMPLE);

        assertFalse(jwtValidationResponse.getIsValid());
    }

    @Test
    void shouldReturnIsValidTrueWhenValidationSucceeds() {
        when(validationsApplication.validate(any())).thenReturn(true);

        final var jwtValidationResponse = validationsController.validate(JWT_EXAMPLE);

        assertTrue(jwtValidationResponse.getIsValid());
    }
}