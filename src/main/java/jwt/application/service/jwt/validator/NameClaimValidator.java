package jwt.application.service.jwt.validator;

import com.auth0.jwt.interfaces.Claim;
import jwt.application.exception.jwtvalidation.ClaimNameContainsNumberException;
import jwt.application.exception.jwtvalidation.ClaimNameIsNullOrEmptyException;
import jwt.application.exception.jwtvalidation.ClaimNameSizeException;
import jwt.application.service.Utils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static jwt.application.config.JwtValidatorConfigs.MAX_CHARACTERS_FOR_CLAIM_NAME;

public class NameClaimValidator extends ClaimValidator {

    public NameClaimValidator(Map<String, Claim> claims) {
        super(claims);
    }

    @Override
    public void validateClaim(Claim claim) {
        final var claimNameValue = claim.asString();

        if (StringUtils.isBlank(claimNameValue)) throw new ClaimNameIsNullOrEmptyException(claims);

        if (claimNameValue.length() > MAX_CHARACTERS_FOR_CLAIM_NAME)
            throw new ClaimNameSizeException(claims);

        if (Utils.textContainsAnyNumber(claimNameValue))
            throw new ClaimNameContainsNumberException(claims);
    }
}
