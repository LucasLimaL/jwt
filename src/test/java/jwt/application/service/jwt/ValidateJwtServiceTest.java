package jwt.application.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jwt.application.exception.jwtvalidation.*;
import org.junit.jupiter.api.Test;

import static jwt.TestConstants.*;
import static jwt.application.enums.RoleEnum.MEMBER;
import static jwt.application.service.jwt.JwtValidatorService.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

    @Test
    void shouldThrowsExceptionWhenJWTDoesNotHaveOneRequiredClaim() {
        final var jwt = JWT.create()
                .withClaim(ROLE, MEMBER.getCaseSensitiveName())
                .withClaim(NAME, "Valdir Aranha")
                .sign(Algorithm.HMAC256(JWT_SECRET));

        assertThrows(InvalidClaimsException.class, () -> validateJwtService.validate(jwt));
    }

    @Test
    void shouldThrowsExceptionWhenJWTClaimRoleHasForbiddenValues() {
        final var jwt = JWT.create()
                .withClaim(ROLE, "MEMBER")
                .withClaim(SEED, "14627")
                .withClaim(NAME, "Valdir Aranha")
                .sign(Algorithm.HMAC256(JWT_SECRET));

        assertThrows(ClaimRoleNotAllowedException.class, () -> validateJwtService.validate(jwt));
    }

    @Test
    void shouldThrowsExceptionWhenJWTClaimSeedHasNonNumericCharacters() {
        final var jwt = JWT.create()
                .withClaim(ROLE, MEMBER.getCaseSensitiveName())
                .withClaim(SEED, "146asd27")
                .withClaim(NAME, "Valdir Aranha")
                .sign(Algorithm.HMAC256(JWT_SECRET));

        assertThrows(ClaimSeedIsNotNumberException.class, () -> validateJwtService.validate(jwt));
    }

    @Test
    void shouldThrowsExceptionWhenJWTClaimSeedNotAPrimeNumber() {
        final var jwt = JWT.create()
                .withClaim(ROLE, MEMBER.getCaseSensitiveName())
                .withClaim(SEED, "8")
                .withClaim(NAME, "Valdir Aranha")
                .sign(Algorithm.HMAC256(JWT_SECRET));

        assertThrows(ClaimSeedIsNotPrimeNumberException.class, () -> validateJwtService.validate(jwt));
    }

    @Test
    void shouldNotThrowExceptionWhenJWTClaimSeedHasOnlyNumericCharactersButIsString() {
        final var jwt = JWT.create()
                .withClaim(ROLE, MEMBER.getCaseSensitiveName())
                .withClaim(SEED, "14627")
                .withClaim(NAME, "Valdir Aranha")
                .sign(Algorithm.HMAC256(JWT_SECRET));

        assertDoesNotThrow(() -> validateJwtService.validate(jwt));
    }

    @Test
    void shouldNotThrowExceptionWhenJWTClaimNameHasLengthEquals256() {
        final var jwt = JWT.create()
                .withClaim(ROLE, MEMBER.getCaseSensitiveName())
                .withClaim(SEED, "14627")
                .withClaim(NAME, TEXT_256_CHARACTERS)
                .sign(Algorithm.HMAC256(JWT_SECRET));

        assertDoesNotThrow(() -> validateJwtService.validate(jwt));
    }

    @Test
    void shouldThrowsExceptionWhenJWTClaimNameHasLengthEquals257() {
        final var jwt = JWT.create()
                .withClaim(ROLE, MEMBER.getCaseSensitiveName())
                .withClaim(SEED, "14627")
                .withClaim(NAME, TEXT_257_CHARACTERS)
                .sign(Algorithm.HMAC256(JWT_SECRET));

        assertThrows(ClaimNameSizeException.class, () -> validateJwtService.validate(jwt));
    }

    @Test
    void shouldThrowsExceptionWhenJWTClaimNameContainsNumber() {
        final var jwt = JWT.create()
                .withClaim(ROLE, MEMBER.getCaseSensitiveName())
                .withClaim(SEED, "14627")
                .withClaim(NAME, "aaaa124")
                .sign(Algorithm.HMAC256(JWT_SECRET));

        assertThrows(ClaimNameContainsNumberException.class, () -> validateJwtService.validate(jwt));
    }
}