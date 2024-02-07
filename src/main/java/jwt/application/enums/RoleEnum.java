package jwt.application.enums;

public enum RoleEnum {
    ADMIN("Admin"),
    MEMBER("Member"),
    EXTERNAL("External");

    private static final boolean IS_CLAIM_ROLE_CASE_SENSITIVE = false;

    private final String caseSensitiveName;

    public String getCaseSensitiveName() {
        return caseSensitiveName;
    }

    RoleEnum(String caseSensitiveName) {
        this.caseSensitiveName = caseSensitiveName;
    }

    public static RoleEnum valueOfByName(String value) {
        if (IS_CLAIM_ROLE_CASE_SENSITIVE)
            return valueOfByNameCaseSensitive(value);

        return valueOfByNameCaseInsensitive(value);
    }

    private static RoleEnum valueOfByNameCaseSensitive(String value) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.caseSensitiveName.equals(value)) return role;
        }

        throw new IllegalArgumentException();
    }

    private static RoleEnum valueOfByNameCaseInsensitive(String value) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.name().equals(value.toUpperCase())) return role;
        }

        throw new IllegalArgumentException();
    }
}
