package jwt.application.service.jwt.validator;

import com.auth0.jwt.interfaces.Claim;
import jwt.application.exception.jwtvalidation.ClaimSeedIsNotNumberException;
import jwt.application.exception.jwtvalidation.ClaimSeedIsNotPrimeNumberException;
import jwt.application.exception.jwtvalidation.ClaimSeedIsNullOrEmptyException;
import jwt.application.service.Utils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class SeedClaimValidator extends ClaimValidator {

    public SeedClaimValidator(Map<String, Claim> claims) {
        super(claims);
    }

    @Override
    public void validateClaim(Claim claim) {
        final var claimSeedValue = claim.asString();

        if (StringUtils.isBlank(claimSeedValue)) throw new ClaimSeedIsNullOrEmptyException(claims);

        try {
            final var claimSeedValueAsLong = Long.parseLong(claimSeedValue);

            if (Utils.isNumberNotPrime(claimSeedValueAsLong))
                throw new ClaimSeedIsNotPrimeNumberException(claims);
        } catch (NumberFormatException e) {
            throw new ClaimSeedIsNotNumberException(claims);
        }
    }
}
