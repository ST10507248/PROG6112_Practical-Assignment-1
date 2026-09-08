package medicare;

/**
 * Exception thrown when attempting to register a patient with an ID that already exists.
 */
public class DuplicatePatientIDException extends Exception {
    public DuplicatePatientIDException(String message) {
        super(message);
    }
}
