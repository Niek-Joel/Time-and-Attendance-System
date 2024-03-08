package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Department;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 *
 * @author Joel Cain
 */
public class PunchDAO {
    private static final String QUERY_FIND_EVENT = "SELECT * FROM event WHERE id = ?";
    private static final String QUERY_INSERT_EVENT = "INSERT INTO event (id, terminalid, badgeid, timestamp, eventtypeid) VALUES "
	    					   + "(?,?,?,?,?)";

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
                        
                        // Create Badge object
                        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
                        Badge badge = badgeDAO.find(badgeid);
                        
                        // convert Timestamp object from sql to LocalDateTime object
                        Timestamp timestamp = rs.getTimestamp("timestamp");
                        LocalDateTime originaltimestamp = timestamp.toLocalDateTime();
                        
                        // get punchtype (eventtype) from eventtypeid
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
		Integer punchid = 0;
		//  Getting values to check authorization   
		int punchTerminalid = punch.getTerminalid();  // Compare this to Department's terminalid for Authorization
		Badge badge = punch.getBadge();
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
		Employee employee = employeeDAO.find(badge);
		Department department = employee.getDepartment();
		int departmentTerminalid = department.getdepartmentId();
	
		//  If matching then Authorize
		if (punchTerminalid == departmentTerminalid) {
               
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {

                Connection conn = daoFactory.getConnection();

                if (conn.isValid(0)) {

                    ps = conn.prepareStatement(QUERY_INSERT_EVENT, PreparedStatement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, punch.getId());
                    ps.setInt(2, punch.getTerminalid());
                    String badgeid = badge.getId();
                    ps.setString(3, badgeid);
                    ps.setString(4, (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(punch.getOriginaltimestamp()).toString());
                    ps.setInt(5, punch.getPunchtype().ordinal());
                    
                    boolean hasresults = ps.execute();

                    if (hasresults) {

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
		}
	            
	

		return punchid;
    }
}
