package utils;

import javafx.util.converter.LocalDateTimeStringConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
    public static final LocalDateTimeStringConverter STRING_TO_DATE = new LocalDateTimeStringConverter();
    public static final int INF = 99999;

    public static final String ERROR_CODE_INVALID_FN = "eroare prenume\n";
    public static final String ERROR_CODE_INVALID_LN = "eroare nume\n";
    public static final String ERROR_CODE_INVALID_UN = "eroare username\n";
    public static final String ERROR_CODE_INVALID_P = "eroare parola\n";

    public static final String imageAvatar = "file:/C:/Bogdan/UBB/MAP/SN/proiectextins/src/main/resources/imgs/avatar.png";

    public static final int APPLICATION_WIDTH = 650;
    public static final int APPLICATION_HEIGHT = 450;
    public static final int LOGIN_SIZE = 400;
    public static final int NOTIFICATION_WIDTH = 300;
    public static final int NOTIFICATION_HEIGHT = 100;
}
