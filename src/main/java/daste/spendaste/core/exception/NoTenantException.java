package daste.spendaste.core.exception;

public class NoTenantException extends RuntimeException {
    public NoTenantException() {
        super("Tenant ID is required but not provided");
    }

    public NoTenantException(String message) {
        super(message);
    }

    public NoTenantException(String message, Throwable cause) {
        super(message, cause);
    }
}
