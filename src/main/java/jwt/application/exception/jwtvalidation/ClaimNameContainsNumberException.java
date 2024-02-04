package jwt.application.exception.jwtvalidation;

import com.auth0.jwt.interfaces.Claim;
import jwt.application.exception.jwtvalidation.base.JwtValidationException;

import java.util.Map;

public class ClaimNameContainsNumberException extends JwtValidationException {

    public static final String CLAIM_NAME_CONTAINS_NUMBER_MESSAGE = "Claim 'Name' contains numbers.";

    public ClaimNameContainsNumberException(Map<String, Claim> claims) {
        super(CLAIM_NAME_CONTAINS_NUMBER_MESSAGE, claims);
    }
}
