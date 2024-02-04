package jwt.application.exception.jwtvalidation;

import com.auth0.jwt.interfaces.Claim;
import jwt.application.exception.jwtvalidation.base.JwtValidationException;

import java.util.Map;

public class InvalidClaimsException extends JwtValidationException {

    public static final String INVALID_CLAIMS_MESSAGE = "JWT contains invalid claims.";

    public InvalidClaimsException(Map<String, Claim> claims) {
        super(INVALID_CLAIMS_MESSAGE, claims);
    }
}
