package in.xlendz.exception;

import in.xlendz.constants.ExceptionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(XlendzDataAccessException.class)
    public ResponseEntity<String> handleXlendzDataAccessException(XlendzDataAccessException ex) {
        LOGGER.error("Data access exception: {} ", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionConstants.SOME_INTERNAL_ERROR_IS_OCCURRED +
                ExceptionConstants.PLEASE_TRY_AGAIN_LATER);
    }

    @ExceptionHandler(DataUpdationException.class)
    public ResponseEntity<String> handleDataUpdationException(DataUpdationException ex) {
        LOGGER.error("Data Updation Exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionConstants.SOME_INTERNAL_ERROR_IS_OCCURRED +
                ExceptionConstants.PLEASE_TRY_AGAIN_LATER);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<String> handleInvalidFileException(InvalidFileException ex) {
        LOGGER.error("Invalid file exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(KYCNotVerifiedException.class)
    public ResponseEntity<String> handleKYCNotVerifiedException(KYCNotVerifiedException ex) {
        LOGGER.error("KYC not verified exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        LOGGER.error("Exception occurred: {} ", ex.getMessage(),ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionConstants.SOME_INTERNAL_ERROR_IS_OCCURRED +
                ExceptionConstants.PLEASE_TRY_AGAIN_LATER);
    }
}
