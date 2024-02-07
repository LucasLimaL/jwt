package jwt.application.service.jwt.validator;

import com.auth0.jwt.interfaces.Claim;

import java.util.Map;

public abstract class ClaimValidator {
    protected final Map<String, Claim> claims;

    public ClaimValidator(Map<String, Claim> claims) {
        this.claims = claims;
    }

    public abstract void validateClaim(Claim claim);
}
