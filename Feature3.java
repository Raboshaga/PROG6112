//Hospital System 
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Feature3 {
    private final ArrayList<Patient> patients;
    private final HospitalWard ward;
    private static final Scanner input = new Scanner(System.in);

    public Feature3() {
        patients = new ArrayList<>();
        ward = new HospitalWard();
    }

    public static void main(String[] args) {
        Feature3 app = new Feature3();
        boolean running = true;

        while (running) {
            System.out.println("1. Register New Patient");
            System.out.println("2. Display All Patients");
            System.out.println("3. Search Patient by ID");
            System.out.println("4. Update Patient Info");
            System.out.println("5. Allocate Bed (Inpatients only)");
            System.out.println("6. Release Bed");
            System.out.println("7. Delete Patient");
            System.out.println("8. View Hospital Report");
            System.out.println("9. Exit");
            System.out.print("Select an option: ");

            try {
                int choice = Integer.parseInt(input.nextLine());
                switch (choice) {
                    case 1: app.handleRegistration(); break;
                    case 2: app.displayAllPatients(); break;
                    case 3: 
                        System.out.print("Enter Patient ID: ");
                        app.searchPatient(input.nextLine());
                        break;
                    case 4: app.handleUpdate(); break;
                    case 5:
                        System.out.print("Enter Patient ID: ");
                        String pId = input.nextLine();
                        System.out.print("Enter Bed Number: ");
                        app.allocateBed(pId, input.nextLine());
                        break;
                    case 6:
                        System.out.print("Enter Patient ID: ");
                        app.releaseBed(input.nextLine());
                        break;
                    case 7:
                        System.out.print("Enter Patient ID to delete: ");
                        app.deletePatient(input.nextLine());
                        break;
                    case 8: app.displayReport(); break;
                    case 9: running = false; break;
                    default: System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        System.out.println("Program exited.");
    }

    private void handleRegistration() throws HospitalException {
        System.out.print("Enter Patient ID: ");
        String id = input.nextLine();
        System.out.print("Enter Age: ");
        int age = Integer.parseInt(input.nextLine());
        System.out.print("Is this an Inpatient? (y/n): ");
        boolean isInpatient = input.nextLine().equalsIgnoreCase("y");

        Patient p;
        if (isInpatient) {
            p = new Inpatient(id, age);
        } else {
            p = new Patient(id, age);
            p.setCategory(PatientCategory.OUTPATIENT);
        }
        registerPatient(p);
        System.out.println("Patient registered successfully.");
    }

    private void handleUpdate() throws HospitalException {
        System.out.print("Enter Patient ID to update: ");
        String id = input.nextLine();
        System.out.print("Enter New First Name: ");
        String fName = input.nextLine();
        System.out.print("Enter New Last Name: ");
        String lName = input.nextLine();
        System.out.print("Enter New Age: ");
        int age = Integer.parseInt(input.nextLine());
        System.out.print("Enter Gender (MALE/FEMALE/OTHER): ");
        Gender gender = Gender.valueOf(input.nextLine().toUpperCase());
        System.out.print("Enter Condition: ");
        String cond = input.nextLine();
        System.out.print("Enter Category (INPATIENT/OUTPATIENT): ");
        PatientCategory cat = PatientCategory.valueOf(input.nextLine().toUpperCase());

        updatePatient(id, fName, lName, age, gender, cond, cat);
        System.out.println("Patient updated successfully.");
    }

    public void registerPatient(Patient patient) throws HospitalException {
        if (patient == null) throw new HospitalException("Patient cannot be null.");
        if (patient.getPatientId() == null || patient.getPatientId().trim().isEmpty()) {
            throw new HospitalException("Patient ID is required.");
        }
        if (findPatient(patient.getPatientId()) != null) {
            throw new HospitalException("Patient ID already exists.");
        }
        if (patient.getAge() < 0) throw new HospitalException("Age cannot be negative.");
        patients.add(patient);
    }

    public Patient findPatient(String patientId) {
        for (Patient p : patients) {
            if (p.getPatientId().equalsIgnoreCase(patientId)) return p;
        }
        return null;
    }

    public void searchPatient(String patientId) throws HospitalException {
        Patient p = findPatient(patientId);
        if (p == null) throw new HospitalException("Patient not found.");
        p.displayDetails();
    }

    public void updatePatient(String pId, String fName, String lName, int age, Gender g, String cond, PatientCategory cat) throws HospitalException {
        Patient p = findPatient(pId);
        if (p == null) throw new HospitalException("Patient not found.");
        if (p instanceof Inpatient && cat != PatientCategory.INPATIENT) {
            throw new HospitalException("Cannot change category of an allocated Inpatient.");
        }
        p.setFirstName(fName);
        p.setLastName(lName);
        p.setAge(age);
        p.setGender(g);
        p.setMedicalCondition(cond);
        p.setCategory(cat);
    }

    public void deletePatient(String patientId) throws HospitalException {
        Patient p = findPatient(patientId);
        if (p == null) throw new HospitalException("Patient not found.");
        if (p instanceof Inpatient) {
            if (!((Inpatient) p).getBedNumber().equals("Not assigned")) {
                throw new HospitalException("Release bed before deleting.");
            }
        }
        patients.remove(p);
    }

    public void allocateBed(String pId, String bedNo) throws HospitalException {
        Patient p = findPatient(pId);
        if (!(p instanceof Inpatient)) throw new HospitalException("Only Inpatients get beds.");
        ward.allocateBed((Inpatient) p, bedNo);
    }

    public void releaseBed(String pId) throws HospitalException {
        Patient p = findPatient(pId);
        if (!(p instanceof Inpatient)) throw new HospitalException("Patient is not an Inpatient.");
        ward.releaseBed(((Inpatient) p).getBedNumber());
        ((Inpatient) p).setBedNumber("Not assigned");
    }

    public void displayAllPatients() {
        if (patients.isEmpty()) System.out.println("No patients found.");
        for (Patient p : patients) p.displayDetails();
    }

    public void displayReport() {
        System.out.println("\n--- HOSPITAL REPORT ---");
        System.out.println("Total Patients: " + patients.size());
        System.out.println("Occupied Beds: " + ward.getOccupiedCount());
        System.out.println("Available Beds: " + (20 - ward.getOccupiedCount()));
    }
}
enum Gender { MALE, FEMALE}
enum PatientCategory { INPATIENT, OUTPATIENT,EMERGENCY }

class HospitalException extends Exception {
    public HospitalException(String msg) { super(msg); }
}

class Patient {
    private String patientId, firstName = "New", lastName = "Patient", condition = "None";
    private int age;
    private Gender gender = Gender.MALE;
    private PatientCategory category = PatientCategory.INPATIENT;

    public Patient(String id, int age) { this.patientId = id; this.age = age; }
    public String getPatientId() { return patientId; }
    public int getAge() { return age; }
    public void setFirstName(String f) { firstName = f; }
    public void setLastName(String l) { lastName = l; }
    public void setAge(int a) { age = a; }
    public void setGender(Gender g) { gender = g; }
    public void setMedicalCondition(String c) { condition = c; }
    public void setCategory(PatientCategory cat) { category = cat; }
    public void displayDetails() {
        System.out.println("[" + category + "] ID: " + patientId + " | Name: " + firstName + " " + lastName + " | Age: " + age);
    }
}

class Inpatient extends Patient {
    private String bedNumber = "Not assigned";
    public Inpatient(String id, int age) { super(id, age); setCategory(PatientCategory.INPATIENT); }
    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String b) { bedNumber = b; }
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("   Bed: " + bedNumber);
    }
}

class HospitalWard {
    private int occupied = 0;
    public void allocateBed(Inpatient p, String b) { p.setBedNumber(b); occupied++; }
    public void releaseBed(String b) { if (occupied > 0) occupied--; }
    public int getOccupiedCount() { return occupied; }
}
