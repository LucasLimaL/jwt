package jwt.application.exception.jwtvalidation;

import com.auth0.jwt.interfaces.Claim;
import jwt.application.exception.jwtvalidation.base.JwtValidationException;

import java.util.Map;

public class ClaimSeedIsNotPrimeNumberException extends JwtValidationException {

    public static final String CLAIM_SEED_IS_NOT_A_PRIME_NUMBER_MESSAGE = "Claim 'Seed' is not a prime number.";

    public ClaimSeedIsNotPrimeNumberException(Map<String, Claim> claims) {
        super(CLAIM_SEED_IS_NOT_A_PRIME_NUMBER_MESSAGE, claims);
    }
}
