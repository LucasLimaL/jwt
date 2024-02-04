package jwt.application.exception.jwtvalidation;

import com.auth0.jwt.interfaces.Claim;
import jwt.application.exception.jwtvalidation.base.JwtValidationException;

import java.util.Map;

public class ClaimRoleNotAllowedException extends JwtValidationException {

    public static final String CLAIM_ROLE_NOT_ALLOWED_MESSAGE = "Claim 'Role' not allowed.";

    public ClaimRoleNotAllowedException(Map<String, Claim> claims) {
        super(CLAIM_ROLE_NOT_ALLOWED_MESSAGE, claims);
    }
}
