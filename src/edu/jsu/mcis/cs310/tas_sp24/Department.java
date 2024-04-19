package edu.jsu.mcis.cs310.tas_sp24;

/**
 * The {@code Department} class . It encapsulates
 * information such as the department ID, description, and terminal ID associated with the
 * department.
 *
 * <p>Departments are stored in the TAS database as individual records within the "department"
 * table. Instances of this class are used to model these records and provide convenient methods
 * to access and manipulate department information.</p>
 *
 *
 * @author Raelee Shuler
 */
public class Department {

    /** The unique numeric identifier of the department.
     *The numeric identifier of the terminal associated with the department. 
     *
     */
    private int departmentid, terminalid;
   
    /** The description of the department. */
    private String description;
    
     /**
     * Constructs a new {@code Department} object with the specified department ID, terminal ID,
     * and description.
     *
     * @param departmentid the unique numeric identifier of the department
     * @param terminalid the numeric identifier of the terminal associated with the department
     * @param description the description of the department
     */
    public Department(int departmentid, int terminalid, String description) {
        this.departmentid = departmentid;
        this.terminalid = terminalid;
        this.description = description;
    }
    
     /**
     * Returns the department ID of this department.
     *
     * @return the department ID
     */
    public int getDepartmentId() {
        return departmentid;
    }
    
    /**
     * Returns the terminal ID associated with this department.
     *
     * @return the terminal ID
     */
    public int getTerminalId() {
        return terminalid;
    }
   
    
      /**
     * Returns the description of this department.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
     /**
     * Sets the department ID of this department.
     *
     * @param departmentid the new department ID
     */
    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }
    
     /**
     * Sets the terminal ID associated with this department.
     *
     * @param terminalid the new terminal ID
     */
    public void setTerminalid(int terminalid) {
        this.terminalid = terminalid;
    }
    
    /**
     * Sets the description of this department.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
     /**
     * Returns a string representation of the {@code Department} object.
     *
     * <p>The string representation consists of the department ID enclosed in '#' followed by
     * the description enclosed in parentheses, and the terminal ID prefixed with "Terminal ID:".</p>
     *
     * @return a string representation of the {@code Department} object
     */
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append("#").append(departmentid).append(" ");
        s.append("(").append(description).append("),");
        s.append(" Terminal ID: ").append(terminalid);

        return s.toString();

    }
}
