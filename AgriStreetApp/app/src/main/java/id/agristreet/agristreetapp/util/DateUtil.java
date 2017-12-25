package id.agristreet.agristreetapp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
    private static DateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public static String format(Date date) {
        return dateFormat.format(date);
    }
}
