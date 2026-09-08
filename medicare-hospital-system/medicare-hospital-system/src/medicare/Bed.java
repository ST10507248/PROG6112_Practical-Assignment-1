package medicare;

/**
 * Bed class representing a single hospital bed.
 * Stores bed number, occupancy status, and assigned patient ID.
 */
public class Bed {
    private String bedNumber;
    private boolean isOccupied;
    private String assignedPatientID;

    public Bed(String bedNumber) {
        this.bedNumber = bedNumber;
        this.isOccupied = false;
        this.assignedPatientID = null;
    }

    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }

    public boolean isOccupied() { return isOccupied; }

    public String getAssignedPatientID() { return assignedPatientID; }

    /**
     * Marks the bed as occupied by a patient.
     */
    public void occupy(String patientID) {
        this.isOccupied = true;
        this.assignedPatientID = patientID;
    }

    /**
     * Releases the bed, marking it as available.
     */
    public void release() {
        this.isOccupied = false;
        this.assignedPatientID = null;
    }

    @Override
    public String toString() {
        return bedNumber + (isOccupied ? " [OCCUPIED]" : " [AVAILABLE]");
    }
}
