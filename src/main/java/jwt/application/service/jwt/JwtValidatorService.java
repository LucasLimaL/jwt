package jwt.application.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.micronaut.context.annotation.Bean;
import jwt.application.exception.jwtvalidation.InvalidClaimsException;
import jwt.application.exception.jwtvalidation.InvalidJwtException;
import jwt.application.service.jwt.factory.ClaimValidatorFactory;
import jwt.application.service.jwt.validator.ClaimValidator;

import java.util.Map;

import static jwt.application.config.JwtValidatorConfigs.REQUIRED_CLAIMS;

@Bean
public class JwtValidatorService {
    private Map<String, Claim> claims;

    public void validate(String jwt) {
        claims = decodeJWT(jwt).getClaims();

        validateClaimsAreAllowed(claims);
        claims.forEach(this::validateClaim);
    }

    private DecodedJWT decodeJWT(String jwt) {
        try {
            return JWT.decode(jwt);
        } catch (JWTDecodeException e) {
            throw new InvalidJwtException(e);
        }
    }

    private void validateClaimsAreAllowed(Map<String, Claim> claims) {
        if (!REQUIRED_CLAIMS.equals(claims.keySet())) {
            throw new InvalidClaimsException(claims);
        }
    }

    private void validateClaim(String claimName, Claim claim) {
        ClaimValidator validator = ClaimValidatorFactory.getValidator(claimName, claims);
        validator.validateClaim(claim);
    }
}
