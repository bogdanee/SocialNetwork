package utils;

public enum UsersStatus {
    FRIENDS,
    REQUESTSENT,
    REQUESTRECEIVED,
    REJECTED,
    NOTFRIENDS;

    public static UsersStatus StringToStatus(String status)
    {
        return switch (status) {
            case "FRIENDS" -> FRIENDS;
            case "REQUESTSENT" -> REQUESTSENT;
            case "REQUESTRECEIVED" -> REQUESTRECEIVED;
            case "REJECTED" -> REJECTED;
            case "NOTFRIENDS" -> NOTFRIENDS;
            default -> null;
        };
    }
}
