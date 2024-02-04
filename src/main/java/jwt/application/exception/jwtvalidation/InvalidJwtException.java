package jwt.application.exception.jwtvalidation;

import jwt.application.exception.jwtvalidation.base.JwtValidationException;

public class InvalidJwtException extends JwtValidationException {

    public static final String INVALID_JWT_MESSAGE = "The token provided is not a valid JWT.";

    public InvalidJwtException(RuntimeException e) {
        super(INVALID_JWT_MESSAGE, e);
    }
}
