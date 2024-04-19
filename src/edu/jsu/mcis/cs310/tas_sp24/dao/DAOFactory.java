package edu.jsu.mcis.cs310.tas_sp24.dao;

import java.sql.*;

/**
 * Factory class for creating Data Access Objects (DAOs) to interact with the database.
 * 
 */
public final class DAOFactory {

    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";

    private final String url, username, password;
    
    private Connection conn = null;
    
    /**
     * Constructs a new DAOFactory with the specified property prefix.
     * @param prefix is used to load the connection properties from a properties file.
     */
    public DAOFactory(String prefix) {

        DAOProperties properties = new DAOProperties(prefix);

        this.url = properties.getProperty(PROPERTY_URL);
        this.username = properties.getProperty(PROPERTY_USERNAME);
        this.password = properties.getProperty(PROPERTY_PASSWORD);

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

    }
    
    /**
     * Returns the database connection managed by this DAOFactory.
     * 
     * @return the database connection
     */
    Connection getConnection() {
        return conn;
    }
    
    /**
     * Returns a new instance of BadgeDAO associated with this DAOFactory.
     * 
     * @return a new BadgeDAO instance
     */
    public BadgeDAO getBadgeDAO() {
        return new BadgeDAO(this);
    }
    
     /**
     * Returns a new instance of PunchDAO associated with this DAOFactory.
     * 
     * @return a new PunchDAO instance
     */
    public PunchDAO getPunchDAO() {
        return new PunchDAO(this);
    }
    
    /**
     * Returns a new instance of DepartmentDAO associated with this DAOFactory.
     * 
     * @return a new DepartmentDAO instance
     */
    public DepartmentDAO getDepartmentDAO() {
        return new DepartmentDAO(this);
    }
    
    /**
     * Returns a new instance of ShiftDAO associated with this DAOFactory.
     * 
     * @return a new ShiftDAO instance
     */
    public ShiftDAO getShiftDAO() {
        return new ShiftDAO(this);
    }
    
     /**
     * Returns a new instance of EmployeeDAO associated with this DAOFactory.
     * 
     * @return a new EmployeeDAO instance
     */
    public EmployeeDAO getEmployeeDAO() {
        return new EmployeeDAO(this);
    }
    
}
