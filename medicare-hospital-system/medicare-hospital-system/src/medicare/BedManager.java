package medicare;

/**
 * BedManager manages the 4x5 ward layout (20 beds: B01-B20) using a 2D array.
 * Demonstrates: 2D arrays, nested loops, passing arrays to methods, using .length field.
 */
public class BedManager {
    private Bed[][] beds;
    private static final int ROWS = 4;
    private static final int COLS = 5;

    public BedManager() {
        beds = new Bed[ROWS][COLS];
        int bedNum = 1;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                beds[i][j] = new Bed(String.format("B%02d", bedNum++));
            }
        }
    }

    /**
     * Allocates a specific bed to a patient.
     * @throws BedAlreadyOccupiedException if bed is already taken
     */
    public void allocateBed(String patientID, String bedNumber) throws BedAlreadyOccupiedException {
        Bed bed = findBed(bedNumber);
        if (bed == null) {
            throw new IllegalArgumentException("Invalid bed number: " + bedNumber);
        }
        if (bed.isOccupied()) {
            throw new BedAlreadyOccupiedException("Bed " + bedNumber + " is already occupied.");
        }
        bed.occupy(patientID);
    }

    /**
     * Finds and allocates the first available bed.
     * @return the bed number allocated
     * @throws NoBedAvailableException if all beds are full
     */
    public String allocateFirstAvailable(String patientID) throws NoBedAvailableException {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (!beds[i][j].isOccupied()) {
                    beds[i][j].occupy(patientID);
                    return beds[i][j].getBedNumber();
                }
            }
        }
        throw new NoBedAvailableException("No beds are currently available.");
    }

    /**
     * Releases a bed by its bed number.
     */
    public void releaseBed(String bedNumber) {
        Bed bed = findBed(bedNumber);
        if (bed != null) {
            bed.release();
        }
    }

    /**
     * Finds a bed by its number (e.g., "B05").
     */
    public Bed findBed(String bedNumber) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (beds[i][j].getBedNumber().equalsIgnoreCase(bedNumber)) {
                    return beds[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Finds which bed is occupied by a specific patient.
     */
    public Bed findBedByPatientID(String patientID) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (beds[i][j].isOccupied() && beds[i][j].getAssignedPatientID().equalsIgnoreCase(patientID)) {
                    return beds[i][j];
                }
            }
        }
        return null;
    }

    // ============================================
    // DISPLAY METHODS (demonstrating nested loops)
    // ============================================

    /**
     * Displays the complete ward layout in a 4x5 grid.
     * [X] = occupied, [ ] = available
     */
    public void displayWardLayout() {
        System.out.println("\n========== WARD LAYOUT (4 x 5) ==========");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Bed bed = beds[i][j];
                String status = bed.isOccupied() ? "[X]" : "[ ]";
                System.out.printf("%s %-4s", status, bed.getBedNumber());
            }
            System.out.println();
        }
        System.out.println("========================================");
    }

    /**
     * Displays only available beds.
     */
    public void displayAvailableBeds() {
        System.out.println("\n========== AVAILABLE BEDS ==========");
        boolean found = false;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (!beds[i][j].isOccupied()) {
                    System.out.print(beds[i][j].getBedNumber() + "  ");
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No available beds.");
        }
        System.out.println("\n========================================");
    }

    /**
     * Displays only occupied beds with patient IDs.
     */
    public void displayOccupiedBeds() {
        System.out.println("\n========== OCCUPIED BEDS ==========");
        boolean found = false;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (beds[i][j].isOccupied()) {
                    System.out.printf("%s -> Patient: %s%n",
                        beds[i][j].getBedNumber(), beds[i][j].getAssignedPatientID());
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No occupied beds.");
        }
        System.out.println("========================================");
    }

    // ============================================
    // REPORT METHODS
    // ============================================

    public int getOccupiedCount() {
        int count = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (beds[i][j].isOccupied()) count++;
            }
        }
        return count;
    }

    public int getTotalBeds() {
        return ROWS * COLS;
    }

    public int getAvailableCount() {
        return getTotalBeds() - getOccupiedCount();
    }

    public double getOccupancyPercentage() {
        return (getOccupiedCount() / (double) getTotalBeds()) * 100.0;
    }

    // ============================================
    // LEARNING OBJECTIVE: Passing array to method + using .length
    // ============================================

    /**
     * Demonstrates passing a 2D array to a method and using the .length field.
     * Prints the dimensions of the bed grid.
     */
    public static void printArrayDimensions(Bed[][] bedArray) {
        System.out.println("Bed Grid Dimensions:");
        System.out.println("  Number of rows: " + bedArray.length);
        if (bedArray.length > 0) {
            System.out.println("  Number of columns: " + bedArray[0].length);
        }
    }

    /**
     * Public accessor to demonstrate the array-passing method above.
     */
    public void showArrayDimensions() {
        printArrayDimensions(beds);
    }

    public Bed[][] getBeds() {
        return beds;
    }
}
