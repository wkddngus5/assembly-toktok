package error;

public class PasswordTokenNotFoundException extends RuntimeException{
    public PasswordTokenNotFoundException() {
        super();
    }

    public PasswordTokenNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PasswordTokenNotFoundException(final String message) {
        super(message);
    }

    public PasswordTokenNotFoundException(final Throwable cause) {
        super(cause);
    }
}
