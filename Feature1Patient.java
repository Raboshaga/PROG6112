//Feature 1
import java.util.Scanner;
public class Patient {
    enum Gender{
        MALE,
        FEMALE,
    }
    enum PatientCategory{
        GENERAL,
        EMERGENCY,
        OUPATIENT,
    }
    public static void main(String[] args){
        
        Scanner input = new Scanner(System.in);
      //Display Patient Management System
        System.out.println("Patient Management System");
        
        System.out.print("Enter patient firstName:");
        String firstName = input.nextLine();
        
        System.out.print("Enter patient lastName:");
        String lastName = input.nextLine();
        
        System.out.print("Enter patient age:");
        int age = input.nextInt();
        
        System.out.print("Enter gender(MALE/FEMALE):");
        String genderInput = input.nextLine().trim().toUpperCase();
        Gender gender = Gender.valueOf(input.nextLine().toUpperCase());
        
        System.out.print("Enter patient category (GENERAL/EMERGENCY/OUTPATIENT):");
        PatientCategory category = PatientCategory.valueOf(input.nextLine().toUpperCase());
        //Display Patient Details 
        System.out.println("Patient Details");
        System.out.println("firstName:"+ firstName);
        System.out.println("Age:"+ age);
        System.out.println("Gender:"+ gender);
        System.out.println("Category:"+ category);
        
    }
    private String patientId;
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;
    private String medicalCondition;
    private PatientCategory category;

    public Patient(String patientId, String firstName, String lastName, int age,
                   Gender gender, String medicalCondition, PatientCategory category) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }

    public String getPatientId() { return patientId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public Gender getGender() { return gender; }
    public String getMedicalCondition() { return medicalCondition; }
    public PatientCategory getCategory() { return category; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAge(int age) { this.age = age; }
    public void setGender(Gender gender) { this.gender = gender; }
    public void setMedicalCondition(String medicalCondition) { this.medicalCondition = medicalCondition; }
    public void setCategory(PatientCategory category) { this.category = category; }

    public void displayDetails() {
        System.out.print("Enter Patient ID: " + patientId);
        System.out.print("Enter Names: " + firstName + " " + lastName);
        System.out.print("Enter Age: " + age);
        System.out.print("Enter Gender: " + gender);
        System.out.print("Enter Medical Condition: " + medicalCondition);
        System.out.print("Enter Category: " + category);
    }

    @Override
    public String toString() {
        return patientId + " | " + firstName + " " + lastName +
               " | Age: " + age + " | Gender: " + gender +
               " | Condition: " + medicalCondition + " | Category: " + category;
   
    } 
    
}
