package jwt.application.service.jwt.validator;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import jwt.Utils;
import jwt.application.exception.jwtvalidation.ClaimSeedIsNotNumberException;
import jwt.application.exception.jwtvalidation.ClaimSeedIsNotPrimeNumberException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static jwt.TestConstants.JWT_SECRET;
import static jwt.TestConstants.SEED;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SeedClaimValidatorTest {
    private SeedClaimValidator seedClaimValidator;
    private Map<String, Claim> claims;

    @Test
    void shouldThrowsExceptionWhenJWTClaimSeedHasNonNumericCharacters() {
        final var jwt = JWT.create()
                .withClaim(SEED, "146asd27")
                .sign(Algorithm.HMAC256(JWT_SECRET));

        claims = Utils.getClaims(jwt);

        seedClaimValidator = new SeedClaimValidator(claims);

        assertThrows(ClaimSeedIsNotNumberException.class, () -> seedClaimValidator.validateClaim(claims.get(SEED)));
    }

    @Test
    void shouldThrowsExceptionWhenJWTClaimSeedNotAPrimeNumber() {
        final var jwt = JWT.create()
                .withClaim(SEED, "8")
                .sign(Algorithm.HMAC256(JWT_SECRET));

        claims = Utils.getClaims(jwt);

        seedClaimValidator = new SeedClaimValidator(claims);

        assertThrows(ClaimSeedIsNotPrimeNumberException.class, () -> seedClaimValidator.validateClaim(claims.get(SEED)));
    }

    @Test
    void shouldNotThrowExceptionWhenJWTClaimSeedHasOnlyNumericCharactersButIsString() {
        final var jwt = JWT.create()
                .withClaim(SEED, "14627")
                .sign(Algorithm.HMAC256(JWT_SECRET));

        claims = Utils.getClaims(jwt);

        seedClaimValidator = new SeedClaimValidator(claims);

        assertDoesNotThrow(() -> seedClaimValidator.validateClaim(claims.get(SEED)));
    }
}