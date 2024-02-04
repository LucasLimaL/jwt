package jwt.application.exception.jwtvalidation;

import com.auth0.jwt.interfaces.Claim;
import jwt.application.exception.jwtvalidation.base.JwtValidationException;

import java.util.Map;

public class ClaimNameIsNullOrEmptyException extends JwtValidationException {

    public static final String CLAIM_NAME_IS_NULL_OR_EMPTY_MESSAGE = "Claim 'Name' is null or empty.";

    public ClaimNameIsNullOrEmptyException(Map<String, Claim> claims) {
        super(CLAIM_NAME_IS_NULL_OR_EMPTY_MESSAGE, claims);
    }
}
