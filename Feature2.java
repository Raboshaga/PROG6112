//Feature 2
import java.util.Scanner;

public class Feature2 {
    private String wardNumber;
    private String bedNumber;
    
    enum Gender{
        MALE,
        FEMALE,
    }
    enum PatientCategory{
        GENERAL,
        EMERGENCY,
        OUPATIENT,
    }

    public Feature2(String patientId, String firstName, String lastName, int age,Gender gender, String medicalCondition, String wardNumber,String bedNumber) {
        super();
        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }
    public String getWardNumber() {
        return wardNumber; 
    }    
    public String getBedNumber() { 
        return bedNumber; 
    }
    public void setWardNumber(String wardNumber) { 
        this.wardNumber = wardNumber; 
    }    
    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber; 
    }    

    
    public void displayDetails() {
        System.out.println("Ward Number: " + wardNumber);
        System.out.println("Bed Number: " + bedNumber);
    }
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        //Display Patient Management System
        System.out.println("Patient Management System");
        
        System.out.print("Patient ID:");
        String patientId = input.nextLine();
        
        System.out.print("Enter patient firstName:");
        String firstName = input.nextLine();
        
        System.out.print("Enter patient lastName:");
        String lastName = input.nextLine();
        
        System.out.print("Enter patient age:");
        int age = input.nextInt();
        
        System.out.print("Enter gender(MALE/FEMALE):");
        String genderInput = input.nextLine().trim().toUpperCase();
        Gender gender = Gender.valueOf(input.nextLine().toUpperCase());
        
        System.out.print("Medical Condition: ");
        String medicalCondition = input.nextLine();
        
        System.out.print("Ward Number: ");
        String wardNumber = input.nextLine();
        
        System.out.print("Bed Number:");
        String bedNumber = input.nextLine();
        
        Feature2 patient =new Feature2(patientId,firstName,lastName,age,gender,medicalCondition,wardNumber,bedNumber);
    }  
}
