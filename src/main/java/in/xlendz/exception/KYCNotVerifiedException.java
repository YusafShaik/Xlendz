package in.xlendz.exception;

public class KYCNotVerifiedException extends RuntimeException {

    public KYCNotVerifiedException(String message) {
        super(message);
    }
    public KYCNotVerifiedException(String message,Throwable e) {
    }
}
