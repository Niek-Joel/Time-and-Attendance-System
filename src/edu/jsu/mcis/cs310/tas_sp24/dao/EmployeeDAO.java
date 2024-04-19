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
 * 
 * 
 * Data Access Object for retrieving Employee information from a database.
 * This class provides methods to find an employee by their ID or badge ID.
 * 
 * <p>
 * This class interacts with the database using a DAOFactory to obtain connections
 * and execute SQL queries. It provides methods to find an employee by their ID or
 * their associated badge ID. Each method returns an Employee object if the employee
 * is found, or null if no matching record is found in the database.
 * </p>
 * 
 * <p>
 * Instances of this class should be created using a DAOFactory, which ensures proper
 * management of database connections and resources.
 * </p>
 * @author Raelee Shuler
 * 
 */
public class EmployeeDAO {

    private static final String QUERY_FIND1 = "SELECT * FROM department JOIN employee ON employee.departmentid = department.id WHERE                                                employee.id = ?";
    private static final String QUERY_FIND2 = "SELECT * FROM employee WHERE badgeid = ?";

    private final DAOFactory daoFactory;

    EmployeeDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;
    }
    
    /**
     * Finds and returns an employee with the specified ID from the database.
     * 
     * @param id the ID of the employee to find
     * @return the Employee object representing the employee, or null if not found
     * @throws DAOException if an SQL error occurs during the operation
     */
    
    public Employee find(int id) {

        Employee employee = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND1);
                ps.setString(1, String.valueOf(id));

                boolean hasresults = ps.execute();

                if (hasresults) {
                    rs = ps.getResultSet();

                    while (rs.next()) {

                        int employeeid = rs.getInt("id");
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
        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }

        return employee;
    }
    
    
     /**
     * Finds and returns an employee associated with the specified badge from the database.
     * 
     * @param badge the Badge object associated with the employee
     * @return the Employee object representing the employee, or null if not found
     * @throws DAOException if an SQL error occurs during the operation
     */
    
    public Employee find(Badge badge) {

        Employee employee = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND2);
                ps.setString(1, badge.getId());

                boolean hasresults = ps.execute();

                if (hasresults) {
                    rs = ps.getResultSet();

                    while (rs.next()) {
                      // Extract employee data from the result set and call find() to get the Employee object
                       
                        int employeeid = rs.getInt("id");                   
                        employee = find(employeeid);

                    }
                }
            }
        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }

        return employee;
        
    }
    
}
