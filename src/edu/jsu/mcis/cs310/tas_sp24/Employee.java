package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * An employee has various attributes such as ID, name, badge, department, shift, and type.
 * This class provides methods to access and manipulate employee information.
 * 
 * <p>
 * This class overrides the {@code toString()} method to provide a formatted string
 * representation of the employee, including their ID, name, badge ID, type, department,
 * and active date.
 * </p>
 * 
 * @author aseel
 */
public class Employee {

    private int id;
    private String firstname;
    private String middlename;
    private String lastname;
    private LocalDateTime active;
    private Badge badge;
    private Department department;
    private Shift shift;
    private EmployeeType employeetype;

     /**
     * Constructs a new Employee with the specified details.
     * 
     * @param id the employee's ID
     * @param firstname the employee's first name
     * @param middlename the employee's middle name
     * @param lastname the employee's last name
     * @param active the date and time when the employee became active
     * @param badge the badge associated with the employee
     * @param department the department in which the employee belongs
     * @param shift the shift assigned to the employee
     * @param employeetype the type of employee
     */
    public Employee(int id, String firstname, String middlename, String lastname, LocalDateTime active, Badge badge,
            Department department, Shift shift, EmployeeType employeetype) {
    //replace argument with parameter map
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.active = active;
        this.badge = badge;
        this.department = department;
        this.shift = shift;
        this.employeetype = employeetype;

    }
    
    // Getter methods...
    /**
     * Returns a string representation of this employee.
     * contains the employee's ID, name, badgeID, type, department, and active date.
     * 
     * @return a string 
     */
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getMiddleName() {
        return middlename;
    }

    public String getLastName() {
        return lastname;
    }

    public LocalDateTime getActive() {
        return active;
    }

    public Badge getbadge() {
        return badge;
    }

    public Department getDepartment() {
        return department;
    }

    public Shift getShift() {
        return shift;
    }

    public EmployeeType getEmployeeType() {
        return employeetype;
    }

    //override toString() method
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append("ID #")
                .append(id)
                .append(": ")
                .append(lastname)
                .append(", ")
                .append(firstname)
                .append(" ")
                .append(middlename)
                .append(" (#")
                .append(badge.getId())
                .append("), Type: ")
                .append(employeetype)
                .append(", Department: ")
                .append(department.getDescription())
                .append(", Active: ")
                .append(active.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        return s.toString();
    }
}
