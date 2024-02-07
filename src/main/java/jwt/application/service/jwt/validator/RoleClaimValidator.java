package jwt.application.service.jwt.validator;

import com.auth0.jwt.interfaces.Claim;
import jwt.application.enums.RoleEnum;
import jwt.application.exception.jwtvalidation.ClaimRoleIsNullOrEmptyException;
import jwt.application.exception.jwtvalidation.ClaimRoleNotAllowedException;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class RoleClaimValidator extends ClaimValidator {

    public RoleClaimValidator(Map<String, Claim> claims) {
        super(claims);
    }

    @Override
    public void validateClaim(Claim claim) {
        String claimValue = claim.asString();
        if (StringUtils.isBlank(claimValue)) throw new ClaimRoleIsNullOrEmptyException(claims);

        try {
            RoleEnum.valueOfByName(claimValue);
        } catch (IllegalArgumentException e) {
            throw new ClaimRoleNotAllowedException(claims);
        }
    }
}
