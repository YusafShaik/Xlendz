package in.xlendz.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private EmailValidator() {
        throw new IllegalStateException("Utility class");
    }

    private static final String EMAIL_PATTERN ="^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
            + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
