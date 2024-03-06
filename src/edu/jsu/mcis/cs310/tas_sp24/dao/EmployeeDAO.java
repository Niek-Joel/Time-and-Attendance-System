package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Department;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import edu.jsu.mcis.cs310.tas_sp24.EmployeeType;
import edu.jsu.mcis.cs310.tas_sp24.Shift;
import java.sql.*;
import java.time.LocalDateTime;

/**
 *
 * @author Raelee Shuler
 */
public class EmployeeDAO {
    
    private static final String QUERY_FIND1 = "SELECT FROM employee WHERE id = ?";
    private static final String QUERY_FIND2 = "SELECT * FROM employee WHERE badgeid = ?";
    
    private final DAOFactory daoFactory;
    
    EmployeeDAO(DAOFactory daoFactory) {
        
        this.daoFactory = daoFactory;
    }
    
    public Employee find(int id) {
        
        Employee employee = null; 
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND1);
                ps.setString(1,String.valueOf(id));
                
                boolean hasresults = ps.execute();
                
                if(hasresults) {
                    rs = ps.getResultSet();
                    
                    while (rs.next()) {
                        
                       int employeeid = id;
                       String firstname = rs.getString("firstname");
                       String middlename = rs.getString("middlename");
                       String lastname = rs.getString("lastname");
                       LocalDateTime active = rs.getTimestamp("active").toLocalDateTime(); 
                       BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
                       String badgeid = rs.getString("badgeid");
                       Badge badge = badgeDAO.find(badgeid);
                       DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();
                       int departmentid = rs.getInt("departmentid");
                       Department department = departmentDAO.find(departmentid);
                       ShiftDAO shiftDAO = daoFactory.getShiftDAO();
                       int shiftid = rs.getInt("shiftid");
                       Shift shift = shiftDAO.find(shiftid);
                       EmployeeType type = EmployeeType.values()[rs.getInt("employeetypeid")];
                       
                       employee = new Employee(id, firstname, middlename, lastname, active, badge, department, shift, type);
                        
                    }
                }
            }
        } 
        
        catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } 
        
        finally {

            if (rs != null) {
                try {
                    rs.close();
                } 
                
                catch (SQLException e) {
                    throw  new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } 
                catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        
        return employee;
    }
                        
}
                
            
        
    
    

