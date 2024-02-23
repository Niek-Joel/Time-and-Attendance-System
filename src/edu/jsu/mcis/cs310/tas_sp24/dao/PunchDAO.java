package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
//import edu.jsu.mcis.cs310.tas_sp24.dao.BadgeDAO;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import java.sql.*;
import java.time.LocalDateTime;

/**
 *
 * @author Joel Cain
 */
public class PunchDAO {
    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
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

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        
                        int terminalid = rs.getInt("terminalid");
                        String badgeid = rs.getString("badgeid");
                        //Badge badge = BadgeDAO.find(badgeid);
                        
                        //  TO-DO: badgeid, eventtypeid

                        // convert Timestamp object from sql to LocalDateTime object
                        Timestamp timestamp = rs.getTimestamp("timestamp");
                        LocalDateTime originaltimestamp = timestamp.toLocalDateTime();

                        System.out.println("id = " + id);
                        System.out.println("terminal id = " + terminalid);
                        System.out.println("badgeid = " + badgeid);
                        System.out.println("originaltimestamp = " + originaltimestamp);
    
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
    
}
