package medicare;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * HospitalSystemTest - JUnit 5 test class covering all required test scenarios.
 * Feature 5: Unit Testing (15 Marks)
 */
public class HospitalSystemTest {

    private PatientManager patientManager;
    private BedManager bedManager;
    private HospitalController controller;

    @BeforeEach
    void setUp() {
        patientManager = new PatientManager();
        bedManager = new BedManager();
        controller = new HospitalController();
    }

    // ============================================
    // TEST: Register a patient
    // ============================================
    @Test
    void testRegisterPatient() throws Exception {
        Patient p = new Patient("P001", "John", "Doe", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
        patientManager.registerPatient(p);
        assertEquals(1, patientManager.getTotalPatients(), "Patient should be registered.");
        assertNotNull(patientManager.searchPatient("P001"), "Registered patient should be searchable.");
    }

    // ============================================
    // TEST: Search for a patient
    // ============================================
    @Test
    void testSearchPatient() throws Exception {
        Patient p = new Patient("P002", "Jane", "Smith", 25, "Female", "Cold", PatientCategory.OUTPATIENT);
        patientManager.registerPatient(p);
        Patient found = patientManager.searchPatient("P002");
        assertNotNull(found, "Patient should be found.");
        assertEquals("Jane", found.getFirstName(), "First name should match.");
        assertEquals("Smith", found.getLastName(), "Last name should match.");
    }

    @Test
    void testSearchPatientNotFound() {
        Patient found = patientManager.searchPatient("NONEXISTENT");
        assertNull(found, "Non-existent patient should return null.");
    }

    // ============================================
    // TEST: Update patient details
    // ============================================
    @Test
    void testUpdatePatientDetails() throws Exception {
        Patient p = new Patient("P003", "Alice", "Brown", 40, "Female", "Asthma", PatientCategory.OUTPATIENT);
        patientManager.registerPatient(p);
        boolean updated = patientManager.updatePatient("P003", "Alicia", "Brown", 41, "Female", "Recovered");
        assertTrue(updated, "Update should return true.");
        Patient updatedPatient = patientManager.searchPatient("P003");
        assertEquals("Alicia", updatedPatient.getFirstName(), "First name should be updated.");
        assertEquals(41, updatedPatient.getAge(), "Age should be updated.");
        assertEquals("Recovered", updatedPatient.getMedicalCondition(), "Condition should be updated.");
    }

    @Test
    void testUpdatePatientNotFound() {
        boolean updated = patientManager.updatePatient("NONEXISTENT", "X", "Y", 0, "M", "None");
        assertFalse(updated, "Updating non-existent patient should return false.");
    }

    // ============================================
    // TEST: Delete a patient
    // ============================================
    @Test
    void testDeletePatient() throws Exception {
        Patient p = new Patient("P004", "Bob", "White", 50, "Male", "Diabetes", PatientCategory.OUTPATIENT);
        patientManager.registerPatient(p);
        assertEquals(1, patientManager.getTotalPatients());
        boolean deleted = patientManager.deletePatient("P004");
        assertTrue(deleted, "Delete should return true.");
        assertEquals(0, patientManager.getTotalPatients(), "Patient list should be empty after deletion.");
        assertNull(patientManager.searchPatient("P004"), "Deleted patient should not be found.");
    }

    @Test
    void testDeletePatientNotFound() {
        boolean deleted = patientManager.deletePatient("NONEXISTENT");
        assertFalse(deleted, "Deleting non-existent patient should return false.");
    }

    // ============================================
    // TEST: Allocate a bed
    // ============================================
    @Test
    void testAllocateBed() throws Exception {
        Inpatient ip = new Inpatient("P005", "Charlie", "Green", 60, "Male", "Surgery", PatientCategory.INPATIENT, 1);
        controller.registerPatient(ip);
        String bedNum = controller.allocateFirstAvailableBed("P005");
        assertNotNull(bedNum, "Bed number should be returned.");
        assertEquals(bedNum, ip.getBedNumber(), "Inpatient should have bed number set.");
        assertTrue(controller.getBedManager().findBed(bedNum).isOccupied(), "Bed should be marked occupied.");
    }

    @Test
    void testAllocateBedOnlyForInpatient() throws Exception {
        Patient outpatient = new Patient("P006", "Diana", "Blue", 35, "Female", "Checkup", PatientCategory.OUTPATIENT);
        controller.registerPatient(outpatient);
        Exception exception = assertThrows(InvalidBedOperationException.class, () -> {
            controller.allocateFirstAvailableBed("P006");
        });
        assertTrue(exception.getMessage().contains("Only Inpatients"));
    }

    // ============================================
    // TEST: Release a bed
    // ============================================
    @Test
    void testReleaseBed() throws Exception {
        Inpatient ip = new Inpatient("P007", "Eve", "Black", 45, "Female", "Recovery", PatientCategory.INPATIENT, 1);
        controller.registerPatient(ip);
        String bedNum = controller.allocateFirstAvailableBed("P007");
        assertNotNull(ip.getBedNumber());
        controller.releaseBed(bedNum);
        assertNull(ip.getBedNumber(), "Inpatient bed number should be cleared.");
        assertFalse(controller.getBedManager().findBed(bedNum).isOccupied(), "Bed should be available.");
    }

    // ============================================
    // TEST: Prevent duplicate Patient IDs
    // ============================================
    @Test
    void testPreventDuplicatePatientID() {
        Patient p1 = new Patient("P008", "Frank", "Red", 55, "Male", "Hypertension", PatientCategory.OUTPATIENT);
        Patient p2 = new Patient("P008", "Grace", "Yellow", 28, "Female", "Allergy", PatientCategory.OUTPATIENT);
        assertDoesNotThrow(() -> patientManager.registerPatient(p1), "First registration should succeed.");
        assertThrows(DuplicatePatientIDException.class, () -> patientManager.registerPatient(p2),
            "Duplicate ID should throw DuplicatePatientIDException.");
        assertEquals(1, patientManager.getTotalPatients(), "Only one patient should be registered.");
    }

    // ============================================
    // TEST: Prevent allocating an occupied bed
    // ============================================
    @Test
    void testPreventAllocatingOccupiedBed() throws Exception {
        Inpatient ip1 = new Inpatient("P009", "Henry", "Purple", 70, "Male", "Fracture", PatientCategory.INPATIENT, 1);
        Inpatient ip2 = new Inpatient("P010", "Ivy", "Orange", 33, "Female", "Infection", PatientCategory.INPATIENT, 1);
        controller.registerPatient(ip1);
        controller.registerPatient(ip2);
        String bedNum = controller.allocateFirstAvailableBed("P009");
        assertThrows(BedAlreadyOccupiedException.class, () -> {
            controller.allocateBed("P010", bedNum);
        }, "Allocating an occupied bed should throw BedAlreadyOccupiedException.");
    }

    // ============================================
    // TEST: No bed available exception
    // ============================================
    @Test
    void testNoBedAvailable() throws Exception {
        // Fill all 20 beds
        for (int i = 1; i <= 20; i++) {
            Inpatient ip = new Inpatient("P" + String.format("%03d", i), "Patient", "Name" + i, 30, "Male", "Condition", PatientCategory.INPATIENT, 1);
            controller.registerPatient(ip);
            controller.allocateFirstAvailableBed("P" + String.format("%03d", i));
        }
        Inpatient extra = new Inpatient("P999", "Extra", "Patient", 25, "Female", "Flu", PatientCategory.INPATIENT, 1);
        controller.registerPatient(extra);
        assertThrows(NoBedAvailableException.class, () -> {
            controller.allocateFirstAvailableBed("P999");
        }, "Should throw exception when no beds available.");
    }

    // ============================================
    // TEST: Ward occupancy calculation
    // ============================================
    @Test
    void testOccupancyPercentage() throws Exception {
        assertEquals(0.0, controller.getBedManager().getOccupancyPercentage(), 0.01, "Initially 0% occupancy.");
        Inpatient ip = new Inpatient("P011", "Jack", "Gray", 60, "Male", "Observation", PatientCategory.INPATIENT, 1);
        controller.registerPatient(ip);
        controller.allocateFirstAvailableBed("P011");
        assertEquals(5.0, controller.getBedManager().getOccupancyPercentage(), 0.01, "1/20 = 5% occupancy.");
    }

    // ============================================
    // TEST: Sorting patients by ID
    // ============================================
    @Test
    void testSortPatientsByID() throws Exception {
        patientManager.registerPatient(new Patient("P003", "C", "C", 20, "M", "A", PatientCategory.OUTPATIENT));
        patientManager.registerPatient(new Patient("P001", "A", "A", 20, "M", "A", PatientCategory.OUTPATIENT));
        patientManager.registerPatient(new Patient("P002", "B", "B", 20, "M", "A", PatientCategory.OUTPATIENT));
        Patient[] sorted = patientManager.getPatientsSortedByID();
        assertEquals("P001", sorted[0].getPatientID());
        assertEquals("P002", sorted[1].getPatientID());
        assertEquals("P003", sorted[2].getPatientID());
    }

    // ============================================
    // TEST: Inpatient inheritance and override
    // ============================================
    @Test
    void testInpatientInheritance() {
        Inpatient ip = new Inpatient("P012", "Kate", "Pink", 29, "Female", "Maternity", PatientCategory.INPATIENT, 2);
        assertTrue(ip instanceof Patient, "Inpatient should be an instance of Patient.");
        assertEquals(PatientCategory.INPATIENT, ip.getCategory(), "Category should be INPATIENT.");
        assertEquals(2, ip.getWardNumber(), "Ward number should be set.");
        assertNull(ip.getBedNumber(), "Bed number should initially be null.");
    }
}
