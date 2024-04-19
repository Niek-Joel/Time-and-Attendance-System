package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Department;
import java.sql.*;

/**
 *
 * The {@code DepartmentDAO} class provides methods to interact with the database and perform
 * operations related to department records. It encapsulates database queries and operations
 * to find and retrieve department information from the database.
 *
 * <p>Instances of {@code DepartmentDAO} are typically created and used by other classes to
 * access and manipulate department data stored in the TAS database. The {@code DepartmentDAO}
 * class follows the DAO design pattern to ensure separation of concerns and maintainability of
 * the database access code.</p>

 * @author Raelee Shuler
 */
public class DepartmentDAO {

    /** SQL query to find a department by ID in the database. */
    private static final String QUERY_FIND = "SELECT * FROM department WHERE id = ?";

    /** The DAOFactory used to create instances of DepartmentDAO. */
    private final DAOFactory daoFactory;
    
     /**
     * Constructs a new {@code DepartmentDAO} instance with the specified {@link DAOFactory}.
     *
     * @param daoFactory the DAOFactory used to create this DepartmentDAO
     */
    public DepartmentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
     /**
     * Retrieves a {@link Department} object from the database based on the provided department ID.
     *
     * @param id the ID of the department to find
     * @return the Department object representing the department with the specified ID,
     *         or {@code null} if no such department exists in the database
     * @throws DAOException if an error occurs while accessing the database
     */
    public Department find(int id) {

        Department department = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setString(1, String.valueOf(id));

                boolean hasresults = ps.execute();

                if (hasresults) {
                    rs = ps.getResultSet();

                    while (rs.next()) {

                        int departmentid = rs.getInt("id");
                        int terminalid = rs.getInt("terminalid");
                        String description = rs.getString("description");

                        department = new Department(departmentid, terminalid, description);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            
         // Close resources 
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
        return department;
        
    }
    
}
