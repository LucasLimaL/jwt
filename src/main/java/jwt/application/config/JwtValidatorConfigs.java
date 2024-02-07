package jwt.application.config;

import java.util.Set;

public final class JwtValidatorConfigs {
    public final static Set<String> REQUIRED_CLAIMS = Set.of("Role", "Seed", "Name");
    public final static int MAX_CHARACTERS_FOR_CLAIM_NAME = 256;
}
