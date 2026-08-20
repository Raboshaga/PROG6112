//Hospital System Test
import java.util.*;

public class Feature5 {

    public static void main(String[] args) {
        System.out.println("--- RUNNING HOSPITAL SYSTEM TESTS ---");
        HospitalSystemTest tests = new HospitalSystemTest();
        
        try {
            tests.setUp();
            tests.registerPatientShouldAddPatient();
            System.out.println("[PASS] registerPatientShouldAddPatient");

            tests.setUp();
            tests.duplicatePatientIdsShouldBeRejected();
            System.out.println("[PASS] duplicatePatientIdsShouldBeRejected");

            tests.setUp();
            tests.searchShouldFindPatient();
            System.out.println("[PASS] searchShouldFindPatient");

            tests.setUp();
            tests.updateShouldChangePatientDetails();
            System.out.println("[PASS] updateShouldChangePatientDetails");

            tests.setUp();
            tests.deleteShouldRemovePatient();
            System.out.println("[PASS] deleteShouldRemovePatient");

            tests.setUp();
            tests.allocateBedShouldOccupyBed();
            System.out.println("[PASS] allocateBedShouldOccupyBed");

            System.out.println("\nALL TESTS PASSED SUCCESSFULLY!");
        } catch (Exception e) {
            System.out.println("\n[FAIL] A test failed with error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
class HospitalSystemTest {
    private HospitalSystem hospital;

    void setUp() {
        hospital = new HospitalSystem();
    }

    private Inpatient inpatient(String id, String surname) {
        Inpatient p = new Inpatient(id, 30);
        p.setFirstName("Test");
        p.setLastName(surname);
        p.setGender(Gender.MALE);
        p.setMedicalCondition("General");
        return p;
    }

    void registerPatientShouldAddPatient() throws HospitalException {
        hospital.registerPatient(inpatient("P001", "Smith"));
        if (hospital.findPatient("P001") == null || hospital.getPatients().size() != 1) {
            throw new RuntimeException("Patient was not added correctly");
        }
    }

    void duplicatePatientIdsShouldBeRejected() {
        try {
            hospital.registerPatient(inpatient("P001", "Smith"));
            hospital.registerPatient(inpatient("P001", "Jones"));
            throw new RuntimeException("Should have thrown HospitalException for duplicate ID");
        } catch (HospitalException e) {
            // Success - exception was thrown
        }
    }

    void searchShouldFindPatient() throws HospitalException {
        hospital.registerPatient(inpatient("P001", "Smith"));
        Patient p = hospital.findPatient("P001");
        if (!"P001".equals(p.getPatientId())) throw new RuntimeException("Search failed");
    }

    void updateShouldChangePatientDetails() throws HospitalException {
        hospital.registerPatient(inpatient("P001", "Smith"));
        hospital.updatePatient("P001", "John", "Brown", 40, Gender.MALE, "Flu", PatientCategory.INPATIENT);
        
        Patient p = hospital.findPatient("P001");
        if (!"Brown".equals(p.getLastName()) || p.getAge() != 40) throw new RuntimeException("Update failed");
    }

    void deleteShouldRemovePatient() throws HospitalException {
        hospital.registerPatient(inpatient("P001", "Smith"));
        hospital.deletePatient("P001");
        if (hospital.findPatient("P001") != null) throw new RuntimeException("Delete failed");
    }

    void allocateBedShouldOccupyBed() throws HospitalException {
        hospital.registerPatient(inpatient("P001", "Smith"));
        hospital.allocateBed("P001", "B01");
        
        if (hospital.getWard().countAvailableBeds() != 19) throw new RuntimeException("Bed allocation count failed");
        Inpatient p = (Inpatient) hospital.findPatient("P001");
        if (!"B01".equals(p.getBedNumber())) throw new RuntimeException("Bed number not assigned to patient");
    }
}
enum Gender { MALE, FEMALE, OTHER }
enum PatientCategory { INPATIENT, OUTPATIENT }

class HospitalException extends Exception {
    public HospitalException(String msg) { super(msg); }
}

class Patient {
    private String id, fName, lName, condition;
    private int age;
    private Gender gender;
    private PatientCategory category;

    public Patient(String id, int age) { this.id = id; this.age = age; }
    public String getPatientId() { return id; }
    public String getLastName() { return lName; }
    public int getAge() { return age; }
    public String getMedicalCondition() { return condition; }
    public void setFirstName(String f) { fName = f; }
    public void setLastName(String l) { lName = l; }
    public void setAge(int a) { age = a; }
    public void setGender(Gender g) { gender = g; }
    public void setMedicalCondition(String c) { condition = c; }
    public void setCategory(PatientCategory cat) { category = cat; }
}

class Inpatient extends Patient {
    private String bedNumber = "Not assigned";
    public Inpatient(String id, int age) { super(id, age); setCategory(PatientCategory.INPATIENT); }
    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String b) { bedNumber = b; }
}

class HospitalWard {
    private int occupied = 0;
    public int countAvailableBeds() { return 20 - occupied; }
    public void allocateBed(Inpatient p, String b) { p.setBedNumber(b); occupied++; }
    public void releaseBed() { if(occupied > 0) occupied--; }
}

class HospitalSystem {
    private List<Patient> patients = new ArrayList<>();
    private HospitalWard ward = new HospitalWard();

    public void registerPatient(Patient p) throws HospitalException {
        if (findPatient(p.getPatientId()) != null) throw new HospitalException("Duplicate ID");
        patients.add(p);
    }
    public Patient findPatient(String id) {
        for (Patient p : patients) if (p.getPatientId().equals(id)) return p;
        return null;
    }
    public List<Patient> getPatients() { return patients; }
    public HospitalWard getWard() { return ward; }
    public void deletePatient(String id) throws HospitalException { patients.remove(findPatient(id)); }
    public void allocateBed(String id, String bed) throws HospitalException {
        Inpatient p = (Inpatient) findPatient(id);
        ward.allocateBed(p, bed);
    }
    public void updatePatient(String id, String fn, String ln, int age, Gender g, String c, PatientCategory cat) {
        Patient p = findPatient(id);
        p.setFirstName(fn); p.setLastName(ln); p.setAge(age); p.setGender(g); p.setMedicalCondition(c); p.setCategory(cat);
    }
}
