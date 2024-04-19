package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Shift;
import java.sql.*;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aseel
 */
public class ShiftDAO {

    private static final String QUERY_FIND1 = "SELECT * FROM shift WHERE id = ?"; //find and return numeric ID
    private static final String QUERY_FIND2 = "SELECT * FROM shift WHERE id IN (SELECT shiftid FROM employee WHERE badgeid = ?)";
    private static final String QUERY_FIND3 = "SELECT shiftid FROM employee WHERE badgeid = ?";

    private final DAOFactory daoFactory;
    
    
    /**
     * Constructs a ShiftDAO object with the specified DAOFactory.
     *
     * @param daoFactory the DAOFactory used to obtain connections to the database
     */
    ShiftDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;

    }
    
    /**
     * Finds and returns a Shift object with the specified ID from the database.
     *
     * @param id the ID of the shift to find
     * @return the Shift object with the specified ID, or null if not found
     * @throws DAOException if an SQL exception occurs
     */
    public Shift find(int id) {

        Shift shift = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND1);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {

                        Map<String, String> shiftmap = new HashMap<>();

                        shiftmap.put("Description", rs.getString("description"));

                        shiftmap.put("Shift Start", rs.getString("shiftstart"));

                        shiftmap.put("Shift Stop", rs.getString("shiftstop"));

                        shiftmap.put("Round Interval", rs.getString("roundinterval"));

                        shiftmap.put("Grace Period", rs.getString("graceperiod"));

                        shiftmap.put("Dock Penalty", rs.getString("dockpenalty"));

                        shiftmap.put("Lunch Start", rs.getString("lunchstart"));

                        shiftmap.put("Lunch Stop", rs.getString("lunchstop"));

                        shiftmap.put("Lunch Threshold", rs.getString("lunchthreshold"));
                        
                        shiftmap.put("id", rs.getString("id"));

                        shift = new Shift((HashMap<String, String>) shiftmap);

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

        return shift;

    }
    
    /**
     * Finds and returns the Shift object associated with the specified badge from the database.
     *
     * @param badge the Badge object to find the associated Shift for
     * @return the Shift object associated with the specified badge, or null if not found
     * @throws DAOException if an SQL exception occurs
     */
    public Shift find(Badge badge) {

        Shift shift = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                //ps = conn.prepareStatement(QUERY_FIND2);
                ps = conn.prepareStatement(QUERY_FIND3);
                
                ps.setString(1, badge.getId());

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        
                        int shiftid = rs.getInt("shiftid");
                        
                        shift = find(shiftid);
                        
                        /*

                        Map<String, String> shiftmap = new HashMap<>();

                        shiftmap.put("Description", rs.getString("description"));

                        shiftmap.put("Shift Start", rs.getString("shiftstart"));

                        shiftmap.put("Shift Stop", rs.getString("shiftstop"));

                        shiftmap.put("Round Interval", rs.getString("roundinterval"));

                        shiftmap.put("Grace Period", rs.getString("graceperiod"));

                        shiftmap.put("Dock Penalty", rs.getString("dockpenalty"));

                        shiftmap.put("Lunch Start", rs.getString("lunchstart"));

                        shiftmap.put("Lunch Stop", rs.getString("lunchstop"));

                        shiftmap.put("Lunch Threshold", rs.getString("lunchthreshold"));

                        shift = new Shift((HashMap<String, String>) shiftmap);
                        */

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
        return shift;

    }

}
