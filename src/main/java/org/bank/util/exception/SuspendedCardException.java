package org.bank.util.exception;

public class SuspendedCardException extends RuntimeException{
    public SuspendedCardException() {
    }

    public SuspendedCardException(String message) {
        super(message);
    }

    public SuspendedCardException(String message, Throwable cause) {
        super(message, cause);
    }

    public SuspendedCardException(Throwable cause) {
        super(cause);
    }
}
