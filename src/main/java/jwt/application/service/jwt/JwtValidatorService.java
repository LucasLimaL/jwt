package jwt.application.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.micronaut.context.annotation.Bean;
import jwt.application.enums.RoleEnum;
import jwt.application.exception.jwtvalidation.*;
import jwt.application.service.Utils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Set;

@Bean
public class JwtValidatorService {

    private Map<String, Claim> claims;

    public static final Integer MAX_CHARACTERS_FOR_CLAIM_NAME = 256;
    public static final String ROLE = "Role";
    public static final String SEED = "Seed";
    public static final String NAME = "Name";
    public static final Set<String> REQUIRED_CLAIMS = Set.of(ROLE, SEED, NAME);

    public void validate(String jwt) {
        claims = decodeJWT(jwt).getClaims();

        validateClaimsAreAllowed();
        validateClaimRole();
        validateClaimSeed();
        validateClaimName();
    }

    private DecodedJWT decodeJWT(String jwt) {
        try {
            return JWT.decode(jwt);
        } catch (JWTDecodeException e) {
            throw new InvalidJwtException(e);
        }
    }

    private void validateClaimsAreAllowed() {
        if (!REQUIRED_CLAIMS.equals(claims.keySet())) {
            throw new InvalidClaimsException(claims);
        }
    }

    private void validateClaimRole() {
        final var claimRoleValue = claims.get(ROLE).asString();

        if (StringUtils.isBlank(claimRoleValue)) throw new ClaimRoleIsNullOrEmptyException(claims);

        try {
            RoleEnum.valueOfByName(claimRoleValue);
        } catch (IllegalArgumentException e) {
            throw new ClaimRoleNotAllowedException(claims);
        }
    }

    private void validateClaimSeed() {
        final var claimSeedValue = claims.get(SEED).asString();

        if (StringUtils.isBlank(claimSeedValue)) throw new ClaimSeedIsNullOrEmptyException(claims);

        try {
            final var claimSeedValueAsLong = Long.parseLong(claimSeedValue);

            if (Utils.isNumberNotPrime(claimSeedValueAsLong))
                throw new ClaimSeedIsNotPrimeNumberException(claims);
        } catch (NumberFormatException e) {
            throw new ClaimSeedIsNotNumberException(claims);
        }
    }

    private void validateClaimName() {
        final var claimNameValue = claims.get(NAME).asString();

        if (StringUtils.isBlank(claimNameValue)) throw new ClaimNameIsNullOrEmptyException(claims);

        if (claimNameValue.length() > MAX_CHARACTERS_FOR_CLAIM_NAME)
            throw new ClaimNameSizeException(claims);

        if (Utils.textContainsAnyNumber(claimNameValue))
            throw new ClaimNameContainsNumberException(claims);
    }
}
