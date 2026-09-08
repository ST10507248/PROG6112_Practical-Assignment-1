package medicare;

/**
 * Exception thrown for invalid bed operations (e.g., allocating to non-inpatient).
 */
public class InvalidBedOperationException extends Exception {
    public InvalidBedOperationException(String message) {
        super(message);
    }
}
