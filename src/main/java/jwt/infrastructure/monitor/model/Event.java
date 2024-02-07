package jwt.infrastructure.monitor.model;

public record Event(
        String message,
        SeverityEnum severity,
        String localTimeBR,
        Object data
) {
}
