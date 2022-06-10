package dev.david.exceptions;

public class EmailNotAvailableException extends RuntimeException{
    public EmailNotAvailableException() {
    }

    public EmailNotAvailableException(String message) {
        super(message);
    }

    public EmailNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailNotAvailableException(Throwable cause) {
        super(cause);
    }

    public EmailNotAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
