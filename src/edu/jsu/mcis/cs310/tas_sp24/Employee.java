package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author aseel
 */
public class Employee {
    private  int id;
    private  String firstname;
    private String middlename;
    private String lastname;
    private LocalDateTime active;
    private Badge badge;
    private Department department;
    private Shift shift;
    private EmployeeType employeetype;
    
 //Constructors
   public Employee(int id, String firstname, String middlename, String lastname, LocalDateTime active, Badge badge,
           Department department, Shift shift, EmployeeType employeetype){
       
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
    
 //getters methods
    
    public int getId(){
        return id;
    }
   public String getFirstName(){
        return firstname;
    }
   public String getMiddleName(){
        return middlename;
    }
   public String getLastName(){
        return lastname;
    }
   public LocalDateTime getActive(){
        return active;
   }
   public Badge getbadge(){
        return badge;
   }
   public Department getDepartment(){
        return department;
   }
   public Shift getShift(){
        return shift;
    }
  public EmployeeType getEmployeeType(){
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