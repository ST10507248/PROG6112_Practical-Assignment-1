package medicare;

/**
 * Exception thrown when no hospital beds are available for allocation.
 */
public class NoBedAvailableException extends Exception {
    public NoBedAvailableException(String message) {
        super(message);
    }
}
