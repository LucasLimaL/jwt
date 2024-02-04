package jwt.application.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoleEnumTest {

    @Test
    void shouldNotThrowIllegalArgumentExceptionWhenValueIsEqualEnumNameProperty() {
        assertDoesNotThrow(() -> RoleEnum.valueOfByName("Member"));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAllLettersAreUpperCase() {
        assertThrows(IllegalArgumentException.class, () -> RoleEnum.valueOfByName("MEMBER"));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCaseSensitive() {
        assertThrows(IllegalArgumentException.class, () -> RoleEnum.valueOfByName("MemBeR"));
    }
}