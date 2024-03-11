package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import edu.jsu.mcis.cs310.tas_sp24.dao.BadgeDAO;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 *
 * @author Joel Cain
 */
public class PunchDAO {
    private static final String QUERY_FIND_EVENT = "SELECT * FROM event WHERE id = ?";
    private static final String QUERY_FIND_PUNCHLIST= "SELECT *, DATE(timestamp) AS originaldate FROM event WHERE badgeid = ? HAVING originaldate = ? ORDER BY `timestamp`";
//SELECT *, DATE(`timestamp`) AS originaldate FROM event WHERE badgeid = '28DC3FB8' HAVING originaldate > '2018-08-01' ORDER BY `timestamp` LIMIT 1; 
//EXAMPLE OF USING SQL SYNTAX (creating tmp table to compare & checking it against the original then getting the first timestamp of the next day)
                            
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
    public ArrayList list(Badge Badge,LocalDate Date){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        String ts=null;
        String ts1=null;
        
        ArrayList<Punch> punchlist = new ArrayList();
      
        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) 
                
              ts =Date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "%";
            
             
                
                ps = conn.prepareStatement(QUERY_FIND_PUNCHLIST);
                ps.setString(1,Badge.getId());
                ps.setString(2,ts);
              
                
                
                
                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        
                        
                        if((Date.getDayOfMonth() != rs.getTimestamp("timestamp").toLocalDateTime().getDayOfMonth()) && (rs.getInt("eventtypeid") != 0)){
                            break;
                        }
                        int id=rs.getInt("id");
                        Punch p =(find(id));
                        punchlist.add(p);
                        
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

        return punchlist;
        
    }
        
    }
    

