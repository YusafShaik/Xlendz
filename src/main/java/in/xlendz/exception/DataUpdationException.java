package in.xlendz.exception;

public class DataUpdationException extends RuntimeException{
    public DataUpdationException(String message) {
        super(message);
    }
    public DataUpdationException(String message, Throwable e) {
        super(message,e);
    }
}
