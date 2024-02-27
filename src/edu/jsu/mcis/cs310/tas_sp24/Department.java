package edu.jsu.mcis.cs310.tas_sp24;

/**
 *
 * @author Raelee Shuler
 */
public class Department {
    private int departmentid, terminalid;
    private String description;
    
    public Department(int departmentid, int terminalid, String description) {
        this.departmentid = departmentid;
        this.terminalid = terminalid;
        this.description = description;
    }
    
    public int getdepartmentId() {
        return departmentid;
    }
    
    public int terminalId() {
        return terminalid;
    }
    
    public String Description() {
        return description;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    public void setTerminalid(int terminalid) {
        this.terminalid = terminalid;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append("#").append(departmentid).append(" ");
        s.append("(").append(description).append("),");
        s.append(" Terminal ID: ").append(terminalid);

        return s.toString();

    }
}
