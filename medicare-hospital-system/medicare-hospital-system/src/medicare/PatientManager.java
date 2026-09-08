package medicare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * PatientManager handles all patient CRUD operations using an ArrayList.
 * Demonstrates ArrayList usage and array sorting (learning objectives).
 */
public class PatientManager {
    private ArrayList<Patient> patients;

    public PatientManager() {
        patients = new ArrayList<>();
    }

    /**
     * Registers a new patient. Prevents duplicate Patient IDs.
     * @throws DuplicatePatientIDException if ID already exists
     */
    public void registerPatient(Patient patient) throws DuplicatePatientIDException {
        if (searchPatient(patient.getPatientID()) != null) {
            throw new DuplicatePatientIDException(
                "Patient ID \"" + patient.getPatientID() + "\" already exists. Registration failed.");
        }
        patients.add(patient);
    }

    /**
     * Searches for a patient by their unique Patient ID.
     * @return Patient object if found, null otherwise
     */
    public Patient searchPatient(String patientID) {
        for (Patient p : patients) {
            if (p.getPatientID().equalsIgnoreCase(patientID)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Updates an existing patient's details.
     * @return true if update was successful, false if patient not found
     */
    public boolean updatePatient(String patientID, String firstName, String lastName,
                                  int age, String gender, String medicalCondition) {
        Patient p = searchPatient(patientID);
        if (p == null) {
            return false;
        }
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setAge(age);
        p.setGender(gender);
        p.setMedicalCondition(medicalCondition);
        return true;
    }

    /**
     * Deletes a patient from the system.
     * @return true if deletion was successful, false if patient not found
     */
    public boolean deletePatient(String patientID) {
        Patient p = searchPatient(patientID);
        if (p != null) {
            patients.remove(p);
            return true;
        }
        return false;
    }

    /**
     * Displays all registered patients.
     */
    public void displayAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients are currently registered.");
            return;
        }
        System.out.println("\n=== ALL REGISTERED PATIENTS ===");
        for (Patient p : patients) {
            p.displayDetails();
        }
    }

    public int getTotalPatients() {
        return patients.size();
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    /**
     * Sorts patients by Patient ID and returns them as an array.
     * Demonstrates: Sorting Arrays (learning objective).
     */
    public Patient[] getPatientsSortedByID() {
        Patient[] arr = patients.toArray(new Patient[0]);
        Arrays.sort(arr, Comparator.comparing(Patient::getPatientID));
        return arr;
    }

    /**
     * Displays patients sorted by ID.
     */
    public void displayPatientsSorted() {
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        Patient[] sorted = getPatientsSortedByID();
        System.out.println("\n=== PATIENTS SORTED BY ID ===");
        for (Patient p : sorted) {
            p.displayDetails();
        }
    }
}
