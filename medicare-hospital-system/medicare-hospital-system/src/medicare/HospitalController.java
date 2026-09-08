package medicare;

/**
 * HospitalController coordinates between PatientManager and BedManager.
 * Handles the business logic of allocating/releasing beds and ensuring
 * only Inpatients can be allocated beds.
 */
public class HospitalController {
    private PatientManager patientManager;
    private BedManager bedManager;

    public HospitalController() {
        patientManager = new PatientManager();
        bedManager = new BedManager();
    }

    public PatientManager getPatientManager() { return patientManager; }
    public BedManager getBedManager() { return bedManager; }

    /**
     * Registers a patient through the controller.
     */
    public void registerPatient(Patient patient) throws DuplicatePatientIDException {
        patientManager.registerPatient(patient);
    }

    /**
     * Allocates a specific bed to an inpatient.
     * Validates that the patient exists and is an Inpatient.
     * @throws Exception if patient not found, not an inpatient, or bed occupied
     */
    public void allocateBed(String patientID, String bedNumber)
            throws PatientNotFoundException, InvalidBedOperationException, BedAlreadyOccupiedException {
        Patient p = patientManager.searchPatient(patientID);
        if (p == null) {
            throw new PatientNotFoundException("Patient ID \"" + patientID + "\" not found.");
        }
        if (!(p instanceof Inpatient)) {
            throw new InvalidBedOperationException("Only Inpatients can be allocated beds. " +
                "Patient \"" + patientID + "\" is registered as " + p.getCategory() + ".");
        }
        bedManager.allocateBed(patientID, bedNumber);
        ((Inpatient) p).setBedNumber(bedNumber);
    }

    /**
     * Allocates the first available bed to an inpatient.
     * @return the allocated bed number
     * @throws Exception if patient not found, not an inpatient, or no beds available
     */
    public String allocateFirstAvailableBed(String patientID)
            throws PatientNotFoundException, InvalidBedOperationException, NoBedAvailableException {
        Patient p = patientManager.searchPatient(patientID);
        if (p == null) {
            throw new PatientNotFoundException("Patient ID \"" + patientID + "\" not found.");
        }
        if (!(p instanceof Inpatient)) {
            throw new InvalidBedOperationException("Only Inpatients can be allocated beds. " +
                "Patient \"" + patientID + "\" is registered as " + p.getCategory() + ".");
        }
        String bedNum = bedManager.allocateFirstAvailable(patientID);
        ((Inpatient) p).setBedNumber(bedNum);
        return bedNum;
    }

    /**
     * Releases a bed and clears the bed assignment from the associated Inpatient.
     */
    public void releaseBed(String bedNumber) {
        Bed bed = bedManager.findBed(bedNumber);
        if (bed != null && bed.isOccupied()) {
            String patientID = bed.getAssignedPatientID();
            Patient p = patientManager.searchPatient(patientID);
            if (p instanceof Inpatient) {
                ((Inpatient) p).setBedNumber(null);
            }
            bedManager.releaseBed(bedNumber);
        }
    }

    /**
     * Deletes a patient and releases any bed they occupy.
     * @return true if deleted successfully
     */
    public boolean deletePatient(String patientID) {
        Patient p = patientManager.searchPatient(patientID);
        if (p != null) {
            // Release bed if inpatient has one
            if (p instanceof Inpatient) {
                String bedNum = ((Inpatient) p).getBedNumber();
                if (bedNum != null) {
                    bedManager.releaseBed(bedNum);
                }
            }
            return patientManager.deletePatient(patientID);
        }
        return false;
    }

    // ============================================
    // REPORT GENERATION
    // ============================================

    public void generateReports() {
        System.out.println("\n========== HOSPITAL REPORTS ==========");
        System.out.println("Total Registered Patients: " + patientManager.getTotalPatients());
        System.out.println("Total Occupied Beds:       " + bedManager.getOccupiedCount());
        System.out.println("Total Available Beds:      " + bedManager.getAvailableCount());
        System.out.printf("Ward Occupancy Percentage: %.2f%%%n", bedManager.getOccupancyPercentage());
        System.out.println("========================================");
    }
}
