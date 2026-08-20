//Hospital Ward
import java.util.Scanner;

public class Feature4 {
    private final Patient[][] beds;
    private static final int ROWS = 4;
    private static final int COLUMNS = 5;
    private static final Scanner input = new Scanner(System.in);

    public Feature4() {
        beds = new Patient[ROWS][COLUMNS];
    }
    public static void main(String[] args) {
        Feature4 ward = new Feature4();
        boolean running = true;

        while (running) {
            System.out.println("1. Display Bed Layout");
            System.out.println("2. Display Available Beds");
            System.out.println("3. Display Occupied Beds");
            System.out.println("4. Allocate Bed to Patient (Demo)");
            System.out.println("5. Release Bed");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            try {
                int choice = Integer.parseInt(input.nextLine());
                switch (choice) {
                    case 1: ward.displayLayout(); break;
                    case 2: ward.displayAvailableBeds(); break;
                    case 3: ward.displayOccupiedBeds(); break;
                    case 4:
                        System.out.print("Enter Patient ID: ");
                        String id = input.nextLine();
                        System.out.print("Enter Bed Number (B01-B20): ");
                        String bedNo = input.nextLine();
                        // For demonstration, we create a new Inpatient object
                        Inpatient p = new Inpatient(id, 30); 
                        p.setFirstName("Demo");
                        p.setLastName("Patient");
                        ward.allocateBed(p, bedNo);
                        System.out.println("Bed allocated successfully.");
                        break;
                    case 5:
                        System.out.print("Enter Bed Number to release: ");
                        ward.releaseBed(input.nextLine());
                        System.out.println("Bed released.");
                        break;
                    case 6: running = false; break;
                    default: System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
    public Patient[][] getBeds() {
        return beds;
    }

    public String getBedNumber(int row, int column) {
        int number = row * COLUMNS + column + 1;
        return String.format("B%02d", number);
    }

    public boolean isValidBed(String bedNumber) {
        if (bedNumber == null || !bedNumber.matches("B(0[1-9]|1[0-9]|20)")) {
            return false;
        }
        int number = Integer.parseInt(bedNumber.substring(1));
        return number >= 1 && number <= 20;
    }

    private int[] getPosition(String bedNumber) throws HospitalException {
        if (!isValidBed(bedNumber)) {
            throw new HospitalException("Invalid bed number. Use B01 to B20.");
        }
        int number = Integer.parseInt(bedNumber.substring(1));
        return new int[]{(number - 1) / COLUMNS, (number - 1) % COLUMNS};
    }

    public boolean isAvailable(String bedNumber) throws HospitalException {
        int[] pos = getPosition(bedNumber);
        return beds[pos[0]][pos[1]] == null;
    }

    public void allocateBed(Inpatient patient, String bedNumber) throws HospitalException {
        int[] pos = getPosition(bedNumber);
        if (beds[pos[0]][pos[1]] != null) {
            throw new HospitalException("Bed " + bedNumber + " is already occupied.");
        }
        beds[pos[0]][pos[1]] = patient;
        patient.setWardNumber("Ward 1");
        patient.setBedNumber(bedNumber);
    }

    public void releaseBed(String bedNumber) throws HospitalException {
        int[] pos = getPosition(bedNumber);
        if (beds[pos[0]][pos[1]] == null) {
            throw new HospitalException("Bed " + bedNumber + " is already available.");
        }
        Patient patient = beds[pos[0]][pos[1]];
        if (patient instanceof Inpatient) {
            Inpatient inpatient = (Inpatient) patient;
            inpatient.setWardNumber("Not assigned");
            inpatient.setBedNumber("Not assigned");
        }
        beds[pos[0]][pos[1]] = null;
    }

    public Patient findPatientByBed(String bedNumber) throws HospitalException {
        int[] pos = getPosition(bedNumber);
        return beds[pos[0]][pos[1]];
    }

    public int countAvailableBeds() {
        int count = 0;
        for (int row = 0; row < beds.length; row++) {
            for (int col = 0; col < beds[row].length; col++) {
                if (beds[row][col] == null) count++;
            }
        }
        return count;
    }

    public int countOccupiedBeds() {
        return (ROWS * COLUMNS) - countAvailableBeds();
    }

    public void displayLayout() {
        System.out.println("WARD 1 BED LAYOUT");
        for (int row = 0; row < beds.length; row++) {
            for (int col = 0; col < beds[row].length; col++) {
                String bedNumber = getBedNumber(row, col);
                String status = beds[row][col] == null ? "Available" : "Occupied";
                System.out.printf("%-15s", bedNumber + " (" + status + ")");
            }
            System.out.println();
        }
    }

    public void displayAvailableBeds() {
        System.out.println("\nAvailable Beds:");
        boolean found = false;
        for (int row = 0; row < beds.length; row++) {
            for (int col = 0; col < beds[row].length; col++) {
                if (beds[row][col] == null) {
                    System.out.print(getBedNumber(row, col) + " ");
                    found = true;
                }
            }
        }
        if (!found) System.out.print("No beds available.");
        System.out.println();
    }

    public void displayOccupiedBeds() {
        System.out.println("\nOccupied Beds:");
        boolean found = false;
        for (int row = 0; row < beds.length; row++) {
            for (int col = 0; col < beds[row].length; col++) {
                if (beds[row][col] != null) {
                    System.out.println(getBedNumber(row, col) + " -> ID: " + 
                        beds[row][col].getPatientId() + " (" + 
                        beds[row][col].getFirstName() + " " + 
                        beds[row][col].getLastName() + ")");
                    found = true;
                }
            }
        }
        if (!found) System.out.println("No occupied beds.");
    }
}

class HospitalException extends Exception {
    public HospitalException(String msg) { super(msg); }
}

class Patient {
    private String patientId, firstName = "", lastName = "";
    private int age;

    public Patient(String id, int age) { this.patientId = id; this.age = age; }
    public String getPatientId() { return patientId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public void setFirstName(String f) { this.firstName = f; }
    public void setLastName(String l) { this.lastName = l; }
}

class Inpatient extends Patient {
    private String wardNumber = "Not assigned";
    private String bedNumber = "Not assigned";

    public Inpatient(String id, int age) { super(id, age); }
    public void setWardNumber(String w) { this.wardNumber = w; }
    public void setBedNumber(String b) { this.bedNumber = b; }
}
