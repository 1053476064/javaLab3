import java.text.DecimalFormat;

public class DataUtil {
    // Format a number string: add suffixes like K, M, or B if the number is large.
    public static String formatNumber(String numStr) {
        try {
            double num = Double.parseDouble(numStr);
            if (num >= 1_000_000_000) {
                return new DecimalFormat("0.0B").format(num / 1_000_000_000.0);
            } else if (num >= 1_000_000) {
                return new DecimalFormat("0.0M").format(num / 1_000_000.0);
            } else if (num >= 1_000) {
                return new DecimalFormat("0.0K").format(num / 1_000.0);
            } else {
                return numStr;
            }
        } catch (NumberFormatException e) {
            return numStr;
        }
    }

    // Safely convert a string to a double value. Returns 0 if parsing fails.
    public static double parseDouble(String numStr) {
        try {
            return Double.parseDouble(numStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
