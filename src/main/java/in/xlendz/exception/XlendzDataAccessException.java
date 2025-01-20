package in.xlendz.exception;

public class XlendzDataAccessException extends RuntimeException{
    public XlendzDataAccessException(String message, Throwable e) {
        super(message,e);
    }
    public XlendzDataAccessException(String message) {
        super(message);
    }
}
