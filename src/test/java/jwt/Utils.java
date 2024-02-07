package jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import jwt.application.exception.jwtvalidation.InvalidJwtException;

import java.util.Map;

public class Utils {

    public static Map<String, Claim> getClaims(String jwt) {
        try {
            return JWT.decode(jwt).getClaims();
        } catch (JWTDecodeException e) {
            throw new InvalidJwtException(e);
        }
    }
}
