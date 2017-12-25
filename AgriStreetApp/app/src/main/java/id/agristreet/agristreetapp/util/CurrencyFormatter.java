package id.agristreet.agristreetapp.util;

import java.text.NumberFormat;
import java.util.Locale;

public final class CurrencyFormatter {

    private static NumberFormat format;

    static {
        format = NumberFormat.getNumberInstance(Locale.GERMANY);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
    }

    public static String format(int number) {
        try {
            return "Rp. " + format.format(number);
        } catch (Exception e) {
            e.printStackTrace();
            return "-";
        }
    }

    public static String format(long number) {
        try {
            return "Rp. " + format.format(number);
        } catch (Exception e) {
            e.printStackTrace();
            return "-";
        }
    }

    public static String format(double number) {
        try {
            return "Rp. " + format.format(number);
        } catch (Exception e) {
            e.printStackTrace();
            return "-";
        }
    }
}
