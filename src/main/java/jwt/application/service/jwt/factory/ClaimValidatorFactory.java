package jwt.application.service.jwt.factory;

import com.auth0.jwt.interfaces.Claim;
import jwt.application.exception.jwtvalidation.InvalidClaimsException;
import jwt.application.service.jwt.validator.ClaimValidator;
import jwt.application.service.jwt.validator.NameClaimValidator;
import jwt.application.service.jwt.validator.RoleClaimValidator;
import jwt.application.service.jwt.validator.SeedClaimValidator;

import java.util.Map;

public class ClaimValidatorFactory {
    public static final String ROLE = "Role";
    public static final String SEED = "Seed";
    public static final String NAME = "Name";

    public static ClaimValidator getValidator(String claimName, Map<String, Claim> claims) {
        return switch (claimName) {
            case ROLE -> new RoleClaimValidator(claims);
            case SEED -> new SeedClaimValidator(claims);
            case NAME -> new NameClaimValidator(claims);
            default -> throw new InvalidClaimsException(claims);
        };
    }
}
