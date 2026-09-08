package medicare;

/**
 * Patient base class demonstrating information hiding (private fields + getters/setters),
 * and providing a displayDetails() method to be overridden by subclasses.
 */
public class Patient {
    // Information hiding: all fields are private
    private String patientID;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String medicalCondition;
    private PatientCategory category;

    /**
     * Constructor to initialise all patient attributes.
     */
    public Patient(String patientID, String firstName, String lastName,
                     int age, String gender, String medicalCondition,
                     PatientCategory category) {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }

    // Getters and Setters (Information Hiding)
    public String getPatientID() { return patientID; }
    public void setPatientID(String patientID) { this.patientID = patientID; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getMedicalCondition() { return medicalCondition; }
    public void setMedicalCondition(String medicalCondition) { this.medicalCondition = medicalCondition; }

    public PatientCategory getCategory() { return category; }
    public void setCategory(PatientCategory category) { this.category = category; }

    /**
     * Displays patient details. This method is intended to be overridden
     * by subclasses (e.g., Inpatient) to include additional information.
     */
    public void displayDetails() {
        System.out.println("========================================");
        System.out.println("Patient ID:        " + patientID);
        System.out.println("Name:              " + firstName + " " + lastName);
        System.out.println("Age:               " + age);
        System.out.println("Gender:            " + gender);
        System.out.println("Medical Condition: " + medicalCondition);
        System.out.println("Category:          " + category);
        System.out.println("========================================");
    }
}
