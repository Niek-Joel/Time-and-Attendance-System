package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Department;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Joel Cain
 */
public class PunchDAO { //"SELECT * FROM department JOIN employee ON employee.departmentid = department.id WHERE employee.id = ?";

    private static final String QUERY_FIND_EVENT = "SELECT * FROM badge JOIN event "
                                                 + "ON event.badgeid = badge.id "
                                                 + "WHERE event.id = ?";
    private static final String QUERY_INSERT_EVENT = "INSERT INTO event (badgeid, timestamp, terminalid, eventtypeid) VALUES "
                                                   + "(?,?,?,?)";

    private final DAOFactory daoFactory;

    public PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Punch find(int id) {

        Punch punch = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND_EVENT);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();
                    while (rs.next()) {
                        // Getting parameters for Punch object contrsuctor (existing object)
                        int terminalid = rs.getInt("terminalid");
                        String badgeid = rs.getString("badgeid");
                        String description = rs.getString("description");
                        Badge badge = new Badge(badgeid, description);

                        Timestamp timestamp = rs.getTimestamp("timestamp");
                        LocalDateTime originaltimestamp = timestamp.toLocalDateTime();

                        int eventtypeid = rs.getInt("eventtypeid");
                        EventType punchtype = EventType.values()[eventtypeid];

                        // Create punch object to be returned
                        punch = new Punch(id, terminalid, badge, originaltimestamp, punchtype);
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

        return punch;
    }

    public int create(Punch punch) {
        Integer punchid = null;
        //  Getting values to check authorization   
        int punchTerminalid = punch.getTerminalid();  // Compare this to Department's terminalid for Authorization
        Badge badge = punch.getBadge();
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        Employee employee = employeeDAO.find(badge);
        Department department = employee.getDepartment();
        int departmentTerminalid = department.getTerminalId();

        //  If matching then Authorize
        if (punchTerminalid == departmentTerminalid || punch.getTerminalid() == 0) {  //if terminalid == 0 then admin access
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {

                Connection conn = daoFactory.getConnection();

                if (conn.isValid(0)) {

                    ps = conn.prepareStatement(QUERY_INSERT_EVENT, PreparedStatement.RETURN_GENERATED_KEYS);              
                    String badgeid = badge.getId();
                    ps.setString(1, badgeid);
                    ps.setString(2, (punch.getOriginaltimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                    ps.setInt(3, punch.getTerminalid());                  
                    ps.setInt(4, punch.getPunchtype().ordinal());
                                  
                    int result = ps.executeUpdate();

                    if (result == 1) {
                        rs = ps.getGeneratedKeys();
                        if (rs.next()) {
                            punchid = rs.getInt(1);
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
        } else { // failed authorization check
            punchid = 0;
        }

        return punchid;
    }
}
