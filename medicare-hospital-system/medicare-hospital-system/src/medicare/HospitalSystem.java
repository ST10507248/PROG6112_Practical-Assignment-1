package medicare;

import java.util.Scanner;

/**
 * HospitalSystem - Main entry point with a console-driven menu.
 * Provides a user-friendly interface for all hospital operations.
 */
public class HospitalSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static HospitalController controller = new HospitalController();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   MEDI-CARE HOSPITAL ADMISSION SYSTEM");
        System.out.println("========================================");
        System.out.println("   20-Bed Ward Management Console v1.0");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            printMainMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1": registerPatient(); break;
                    case "2": searchPatient(); break;
                    case "3": updatePatient(); break;
                    case "4": deletePatient(); break;
                    case "5": displayAllPatients(); break;
                    case "6": allocateBed(); break;
                    case "7": releaseBed(); break;
                    case "8": displayWardLayout(); break;
                    case "9": displayAvailableBeds(); break;
                    case "10": displayOccupiedBeds(); break;
                    case "11": generateReports(); break;
                    case "12": displayPatientsSorted(); break;
                    case "13": showArrayDimensions(); break;
                    case "0":
                        System.out.println("Thank you for using MediCare System. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n------------- MAIN MENU -------------");
        System.out.println(" 1. Register New Patient");
        System.out.println(" 2. Search Patient by ID");
        System.out.println(" 3. Update Patient Details");
        System.out.println(" 4. Delete Patient");
        System.out.println(" 5. Display All Patients");
        System.out.println(" 6. Allocate Bed to Inpatient");
        System.out.println(" 7. Release Bed (Discharge)");
        System.out.println(" 8. Display Ward Layout (4x5 Grid)");
        System.out.println(" 9. Display Available Beds");
        System.out.println("10. Display Occupied Beds");
        System.out.println("11. Generate Reports");
        System.out.println("12. Display Patients Sorted by ID");
        System.out.println("13. Show Array Dimensions (Demo)");
        System.out.println(" 0. Exit");
        System.out.println("-------------------------------------");
    }

    // ============================================
    // PATIENT MANAGEMENT
    // ============================================

    private static void registerPatient() {
        System.out.println("\n--- REGISTER NEW PATIENT ---");
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Enter Age: ");
        int age = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine().trim();

        System.out.print("Enter Medical Condition: ");
        String condition = scanner.nextLine().trim();

        System.out.println("Select Patient Category:");
        PatientCategory.displayAllCategories();
        System.out.print("Enter choice (1-3): ");
        int catChoice = Integer.parseInt(scanner.nextLine().trim());
        PatientCategory category;
        switch (catChoice) {
            case 1: category = PatientCategory.INPATIENT; break;
            case 2: category = PatientCategory.OUTPATIENT; break;
            case 3: category = PatientCategory.EMERGENCY; break;
            default:
                System.out.println("Invalid choice. Defaulting to OUTPATIENT.");
                category = PatientCategory.OUTPATIENT;
        }

        try {
            if (category == PatientCategory.INPATIENT) {
                System.out.print("Enter Ward Number: ");
                int ward = Integer.parseInt(scanner.nextLine().trim());
                Inpatient inpatient = new Inpatient(id, firstName, lastName, age, gender, condition, category, ward);
                controller.registerPatient(inpatient);
            } else {
                Patient patient = new Patient(id, firstName, lastName, age, gender, condition, category);
                controller.registerPatient(patient);
            }
            System.out.println("Patient registered successfully!");
        } catch (DuplicatePatientIDException e) {
            System.out.println("Registration Failed: " + e.getMessage());
        }
    }

    private static void searchPatient() {
        System.out.print("\nEnter Patient ID to search: ");
        String id = scanner.nextLine().trim();
        Patient p = controller.getPatientManager().searchPatient(id);
        if (p != null) {
            System.out.println("\nPatient Found:");
            p.displayDetails();
        } else {
            System.out.println("Patient not found.");
        }
    }

    private static void updatePatient() {
        System.out.print("\nEnter Patient ID to update: ");
        String id = scanner.nextLine().trim();
        Patient p = controller.getPatientManager().searchPatient(id);
        if (p == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("Enter new details (press Enter to keep current value):");
        System.out.print("First Name [" + p.getFirstName() + "]: ");
        String firstName = scanner.nextLine().trim();
        if (firstName.isEmpty()) firstName = p.getFirstName();

        System.out.print("Last Name [" + p.getLastName() + "]: ");
        String lastName = scanner.nextLine().trim();
        if (lastName.isEmpty()) lastName = p.getLastName();

        System.out.print("Age [" + p.getAge() + "]: ");
        String ageStr = scanner.nextLine().trim();
        int age = ageStr.isEmpty() ? p.getAge() : Integer.parseInt(ageStr);

        System.out.print("Gender [" + p.getGender() + "]: ");
        String gender = scanner.nextLine().trim();
        if (gender.isEmpty()) gender = p.getGender();

        System.out.print("Medical Condition [" + p.getMedicalCondition() + "]: ");
        String condition = scanner.nextLine().trim();
        if (condition.isEmpty()) condition = p.getMedicalCondition();

        boolean updated = controller.getPatientManager().updatePatient(id, firstName, lastName, age, gender, condition);
        if (updated) {
            System.out.println("Patient details updated successfully!");
        } else {
            System.out.println("Update failed.");
        }
    }

    private static void deletePatient() {
        System.out.print("\nEnter Patient ID to delete: ");
        String id = scanner.nextLine().trim();
        boolean deleted = controller.deletePatient(id);
        if (deleted) {
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    private static void displayAllPatients() {
        controller.getPatientManager().displayAllPatients();
    }

    private static void displayPatientsSorted() {
        controller.getPatientManager().displayPatientsSorted();
    }

    // ============================================
    // BED MANAGEMENT
    // ============================================

    private static void allocateBed() {
        System.out.println("\n--- ALLOCATE BED ---");
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();

        System.out.println("Choose allocation method:");
        System.out.println("1. Auto-allocate first available bed");
        System.out.println("2. Choose specific bed number");
        System.out.print("Enter choice: ");
        String method = scanner.nextLine().trim();

        try {
            if (method.equals("1")) {
                String bedNum = controller.allocateFirstAvailableBed(id);
                System.out.println("Bed " + bedNum + " allocated successfully to Patient " + id);
            } else if (method.equals("2")) {
                controller.getBedManager().displayAvailableBeds();
                System.out.print("Enter Bed Number (e.g., B05): ");
                String bedNum = scanner.nextLine().trim();
                controller.allocateBed(id, bedNum);
                System.out.println("Bed " + bedNum + " allocated successfully to Patient " + id);
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Allocation Failed: " + e.getMessage());
        }
    }

    private static void releaseBed() {
        System.out.println("\n--- RELEASE BED ---");
        controller.getBedManager().displayOccupiedBeds();
        System.out.print("Enter Bed Number to release (e.g., B05): ");
        String bedNum = scanner.nextLine().trim();
        controller.releaseBed(bedNum);
        System.out.println("Bed " + bedNum + " has been released.");
    }

    private static void displayWardLayout() {
        controller.getBedManager().displayWardLayout();
    }

    private static void displayAvailableBeds() {
        controller.getBedManager().displayAvailableBeds();
    }

    private static void displayOccupiedBeds() {
        controller.getBedManager().displayOccupiedBeds();
    }

    // ============================================
    // REPORTS & DEMO
    // ============================================

    private static void generateReports() {
        controller.generateReports();
    }

    private static void showArrayDimensions() {
        controller.getBedManager().showArrayDimensions();
    }
}
