package medicare;

/**
 * PatientCategory enum representing the three types of patients.
 * Demonstrates enum methods as per learning objectives.
 */
public enum PatientCategory {
    INPATIENT,
    OUTPATIENT,
    EMERGENCY;

    /**
     * Displays all available categories using enum methods.
     * Demonstrates: values(), name(), ordinal()
     */
    public static void displayAllCategories() {
        System.out.println("Available Patient Categories:");
        for (PatientCategory category : PatientCategory.values()) {
            System.out.println((category.ordinal() + 1) + ". " + category.name());
        }
    }
}
