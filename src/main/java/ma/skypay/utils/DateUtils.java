package ma.skypay.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// I used this in my first version of this code, but I moved it now to the Transaction Class instead :)
public class DateUtils {
    public static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }
}
