package in.xlendz.util;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private int minLength;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
       this.minLength = constraintAnnotation.minLength();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.length() < minLength) {
            return false;
        }
        // Example: Password must contain at least one digit, one uppercase letter, and be at least 6 characters
        return password.matches("^(?=.*[0-9])(?=.*[A-Z]).{6,}$");
    }
}

