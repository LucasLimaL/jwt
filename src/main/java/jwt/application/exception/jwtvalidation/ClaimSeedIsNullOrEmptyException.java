package jwt.application.exception.jwtvalidation;

import com.auth0.jwt.interfaces.Claim;
import jwt.application.exception.jwtvalidation.base.JwtValidationException;

import java.util.Map;

public class ClaimSeedIsNullOrEmptyException extends JwtValidationException {

    public static final String CLAIM_SEED_IS_NULL_OR_EMPTY_MESSAGE = "Claim 'Seed' is null or empty.";

    public ClaimSeedIsNullOrEmptyException(Map<String, Claim> claims) {
        super(CLAIM_SEED_IS_NULL_OR_EMPTY_MESSAGE, claims);
    }
}
