package jwt.application.service.jwt.validator;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import jwt.Utils;
import jwt.application.exception.jwtvalidation.ClaimNameContainsNumberException;
import jwt.application.exception.jwtvalidation.ClaimNameSizeException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static jwt.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NameClaimValidatorTest {
    private NameClaimValidator nameClaimValidator;
    private Map<String, Claim> claims;

    @Test
    void shouldNotThrowExceptionWhenJWTClaimNameHasLengthEquals256() {
        final var jwt = JWT.create()
                .withClaim(NAME, TEXT_256_CHARACTERS)
                .sign(Algorithm.HMAC256(JWT_SECRET));

        claims = Utils.getClaims(jwt);

        nameClaimValidator = new NameClaimValidator(claims);

        assertDoesNotThrow(() -> nameClaimValidator.validateClaim(claims.get(NAME)));
    }

    @Test
    void shouldThrowsExceptionWhenJWTClaimNameHasLengthEquals257() {
        final var jwt = JWT.create()
                .withClaim(NAME, TEXT_257_CHARACTERS)
                .sign(Algorithm.HMAC256(JWT_SECRET));

        claims = Utils.getClaims(jwt);

        nameClaimValidator = new NameClaimValidator(claims);

        assertThrows(ClaimNameSizeException.class, () -> nameClaimValidator.validateClaim(claims.get(NAME)));
    }

    @Test
    void shouldThrowsExceptionWhenJWTClaimNameContainsNumber() {
        final var jwt = JWT.create()
                .withClaim(NAME, "aaaa124")
                .sign(Algorithm.HMAC256(JWT_SECRET));

        claims = Utils.getClaims(jwt);

        nameClaimValidator = new NameClaimValidator(claims);

        assertThrows(ClaimNameContainsNumberException.class, () -> nameClaimValidator.validateClaim(claims.get(NAME)));
    }
}