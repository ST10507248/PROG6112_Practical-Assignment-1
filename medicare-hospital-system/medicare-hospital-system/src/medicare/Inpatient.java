package medicare;

/**
 * Inpatient class extends Patient.
 * Demonstrates inheritance, super() constructor calls, and method overriding.
 * Only Inpatients may be allocated hospital beds.
 */
public class Inpatient extends Patient {
    private int wardNumber;
    private String bedNumber; // null if no bed allocated

    /**
     * Constructor using super() to initialise inherited attributes.
     * Forces category to INPATIENT regardless of parameter passed.
     */
    public Inpatient(String patientID, String firstName, String lastName,
                     int age, String gender, String medicalCondition,
                     PatientCategory category, int wardNumber) {
        super(patientID, firstName, lastName, age, gender, medicalCondition, PatientCategory.INPATIENT);
        this.wardNumber = wardNumber;
        this.bedNumber = null;
    }

    public int getWardNumber() { return wardNumber; }
    public void setWardNumber(int wardNumber) { this.wardNumber = wardNumber; }

    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }

    /**
     * Overrides displayDetails() to include ward and bed information.
     * Calls super.displayDetails() first to show inherited attributes.
     */
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Ward Number:       " + wardNumber);
        System.out.println("Bed Number:        " + (bedNumber != null ? bedNumber : "Not Assigned"));
        System.out.println("========================================");
    }
}
