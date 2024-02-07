package jwt.application.service.jwt.validator;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import jwt.Utils;
import jwt.application.exception.jwtvalidation.ClaimRoleNotAllowedException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static jwt.TestConstants.JWT_SECRET;
import static jwt.TestConstants.ROLE;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoleClaimValidatorTest {
    private RoleClaimValidator roleClaimValidator;
    private Map<String, Claim> claims;

    @Test
    void shouldThrowsExceptionWhenJWTClaimRoleHasForbiddenValues() {
        final var jwt = JWT.create()
                .withClaim(ROLE, "MEMBER")
                .sign(Algorithm.HMAC256(JWT_SECRET));

        claims = Utils.getClaims(jwt);

        roleClaimValidator = new RoleClaimValidator(claims);

        assertThrows(ClaimRoleNotAllowedException.class, () -> roleClaimValidator.validateClaim(claims.get(ROLE)));
    }
}