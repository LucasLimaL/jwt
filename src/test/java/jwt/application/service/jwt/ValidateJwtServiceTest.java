package jwt.application.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jwt.application.exception.jwtvalidation.InvalidClaimsException;
import jwt.application.exception.jwtvalidation.InvalidJwtException;
import org.junit.jupiter.api.Test;

import static jwt.TestConstants.*;
import static jwt.application.enums.RoleEnum.MEMBER;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidateJwtServiceTest {
    private final JwtValidatorService validateJwtService = new JwtValidatorService();

    @Test
    void shouldThrowsExceptionWhenJWTIsInvalid() {
        assertThrows(InvalidJwtException.class, () -> validateJwtService.validate(INVALID_JWT));
    }

    @Test
    void shouldThrowsExceptionWhenJWTHasForbiddenClaims() {
        final var jwt = JWT.create()
                .withClaim(ROLE, MEMBER.getCaseSensitiveName())
                .withClaim("Org", "BR")
                .withClaim(SEED, "14627")
                .withClaim(NAME, "Valdir Aranha")
                .sign(Algorithm.HMAC256(JWT_SECRET));

        assertThrows(InvalidClaimsException.class, () -> validateJwtService.validate(jwt));
    }
}