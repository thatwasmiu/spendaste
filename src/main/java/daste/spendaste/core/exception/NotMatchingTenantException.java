package daste.spendaste.core.exception;

public class NotMatchingTenantException extends RuntimeException {
    public NotMatchingTenantException() {
        super("Tenant is not matching with userId");
    }

    public NotMatchingTenantException(String message) {
        super(message);
    }

    public NotMatchingTenantException(String message, Throwable cause) {
        super(message, cause);
    }
}
