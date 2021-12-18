package utils;

public enum RequestStatus {
    PENDING,
    APPROVED,
    REJECTED;

    public static RequestStatus StringToStatus(String status)
    {
        return switch (status) {
            case "PENDING" -> PENDING;
            case "APPROVED" -> APPROVED;
            case "REJECTED" -> REJECTED;
            default -> null;
        };
    }
}
