package jwt.application.exception.jwtvalidation.base;

import com.auth0.jwt.interfaces.Claim;

import java.util.Map;

public class JwtValidationException extends RuntimeException {

    private Map<String, Claim> claims;

    public Map<String, Claim> getClaims() {
        return claims;
    }

    public JwtValidationException(String message, Map<String, Claim> claims) {
        super(message);
        this.claims = claims;
    }

    public JwtValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
