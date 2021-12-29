package nl.inholland.javafx.Model;

public class Employee {


    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private AccessLevel typeOfEmployee;

    public Employee(String firstName, String lastName, String userName, String password, AccessLevel typeOfEmployee) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.typeOfEmployee = typeOfEmployee;
    }

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setUserName(String userName) {this.userName = userName;}
    public void setPassword(String password) {this.password = password;}
    public void setTypeOfEmployee(AccessLevel typeOfEmployee) {this.typeOfEmployee = typeOfEmployee;}

    public String getUserName() {return userName;}
    public String getPassword() {return password;}
    public AccessLevel getTypeOfEmployee() {return typeOfEmployee;}
}
