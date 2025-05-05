package app.udala.alice.shared.converter;

import java.time.LocalDateTime;

public class LocalDateTimeConverter {

    public static String convertToString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.toString();
    }

    public static LocalDateTime convertToLocalDateTime(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateString);
    }
}
