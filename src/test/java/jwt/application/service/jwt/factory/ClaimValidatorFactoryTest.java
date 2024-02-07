package jwt.application.service.jwt.factory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jwt.Utils;
import jwt.application.exception.jwtvalidation.InvalidClaimsException;
import org.junit.jupiter.api.Test;

import static jwt.TestConstants.*;
import static jwt.application.enums.RoleEnum.MEMBER;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClaimValidatorFactoryTest {

    @Test
    void shouldThrowsExceptionWhenJWTDoesNotHaveOneRequiredClaim() {
        final var jwt = JWT.create()
                .withClaim(ROLE, MEMBER.getCaseSensitiveName())
                .withClaim(NAME, "Valdir Aranha")
                .sign(Algorithm.HMAC256(JWT_SECRET));

        final var claims = Utils.getClaims(jwt);

        assertThrows(InvalidClaimsException.class, () -> ClaimValidatorFactory.getValidator(INVALID_CLAIM, claims));
    }
}