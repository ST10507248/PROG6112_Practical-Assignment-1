package medicare;

/**
 * Exception thrown when a patient search returns no results.
 */
public class PatientNotFoundException extends Exception {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
