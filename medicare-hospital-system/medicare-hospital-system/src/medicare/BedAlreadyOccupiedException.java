package medicare;

/**
 * Exception thrown when attempting to allocate an already occupied bed.
 */
public class BedAlreadyOccupiedException extends Exception {
    public BedAlreadyOccupiedException(String message) {
        super(message);
    }
}
