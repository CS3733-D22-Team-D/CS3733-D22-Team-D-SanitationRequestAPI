package edu.wpi.cs3733.D22.teamD.entities;

import edu.wpi.cs3733.D22.teamD.table.TableHandler;
import edu.wpi.cs3733.D22.teamD.table.TableObject;

public class Employee extends TableObject {

    // CLASS ENUMS
    public enum EmployeeType {
        ADMINISTRATOR,
        DOCTOR,
        NURSE,
        JANITOR,
        KITCHEN
    }

    // TABLEOBJECT METHODS
    @Override
    public String getTableInit() {

        return "CREATE TABLE EMPLOYEES(nodeid varchar(48) PRIMARY KEY,"
                + "firstname varchar(60),"
                + "lastname varchar(60),"
                + "dateofbirth varchar(20),"
                + "employeetype varchar(30),"
                + "securityclearance varchar(2))";
    }

    @Override
    public String getTableName() {
        return "EMPLOYEES";
    }

    @Override
    public String getAttribute(int columnNumber) {

        switch (columnNumber) {
            case 1:
                return nodeID;
            case 2:
                return firstName;
            case 3:
                return lastName;
            case 4:
                return dateOfBirth;
            case 5:
                return employeeType.toString();
            case 6:
                return Integer.toString(securityClearance);
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void setAttribute(int columnNumber, String newAttribute) {
        switch (columnNumber) {
            case 1:
                nodeID = newAttribute;
                break;
            case 2:
                firstName = newAttribute;
                break;
            case 3:
                lastName = newAttribute;
                break;
            case 4:
                dateOfBirth = newAttribute;
                break;
            case 5:
                employeeType = EmployeeType.valueOf(newAttribute);
                break;
            case 6:
                securityClearance = Integer.parseInt(newAttribute);
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public Object get() {
        return new Employee();
    }

    // ATTRIBUTES
    @TableHandler(table = 0, col = 0)
    private String nodeID;

    @TableHandler(table = 0, col = 1)
    private String firstName;

    @TableHandler(table = 0, col = 2)
    private String lastName;

    @TableHandler(table = 0, col = 3)
    private String dateOfBirth;

    @TableHandler(table = 0, col = 4)
    private EmployeeType employeeType;

    @TableHandler(table = 0, col = 5)
    private int securityClearance;

    // CONSTRUCTORS

    public Employee() {}

    public Employee(
            String firstName,
            String lastName,
            String dateOfBirth,
            EmployeeType employeeType,
            int securityClearance) {
        this.nodeID = firstName + lastName + dateOfBirth;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.employeeType = employeeType;
        this.securityClearance = securityClearance;
    }

    // SETTERS AND GETTERS

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public int getSecurityClearance() {
        return securityClearance;
    }

    public void setSecurityClearance(int securityClearance) {
        this.securityClearance = securityClearance;
    }
}
