package jwt.application.exception.jwtvalidation;

import com.auth0.jwt.interfaces.Claim;
import jwt.application.exception.jwtvalidation.base.JwtValidationException;

import java.util.Map;

public class ClaimRoleIsNullOrEmptyException extends JwtValidationException {

    public static final String CLAIM_ROLE_IS_NULL_OR_EMPTY_MESSAGE = "Claim 'Role' is null or empty.";

    public ClaimRoleIsNullOrEmptyException(Map<String, Claim> claims) {
        super(CLAIM_ROLE_IS_NULL_OR_EMPTY_MESSAGE, claims);
    }
}
