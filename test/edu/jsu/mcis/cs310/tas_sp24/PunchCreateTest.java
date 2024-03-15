package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.*;
import static org.junit.Assert.*;

public class PunchCreateTest {

    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }

    @Test
    public void testCreatePunch1() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        
        Punch p1 = new Punch(103, badgeDAO.find("021890C0"), EventType.CLOCK_IN);

        /* Create Timestamp Objects */
        
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        
        String badgeid = p1.getBadge().getId();
        ots = p1.getOriginaltimestamp();
        int terminalid = p1.getTerminalid();
        EventType punchtype = p1.getPunchtype();

        /* Insert Punch Into Database */
        
        int punchid = punchDAO.create(p1);

        /* Retrieve New Punch */
        
        Punch p2 = punchDAO.find(punchid);

        /* Compare Punches */
        
        assertEquals(badgeid, p2.getBadge().getId());

        rts = p2.getOriginaltimestamp();

        assertEquals(terminalid, p2.getTerminalid());
        assertEquals(punchtype, p2.getPunchtype());
        assertEquals(ots.format(dtf), rts.format(dtf));

    }

    @Test
    public void testCreatePunchAdmin() { // Checking terminalID = 0. (Admin entry)

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        
        Punch p1 = new Punch(0, badgeDAO.find("FF591F68"), EventType.CLOCK_OUT);

        /* Create Timestamp Objects */
        
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        
        String badgeid = p1.getBadge().getId();
        ots = p1.getOriginaltimestamp();
        int terminalid = p1.getTerminalid();
        EventType punchtype = p1.getPunchtype();

        /* Insert Punch Into Database */
        
        int punchid = punchDAO.create(p1);

        /* Retrieve New Punch */
        
        Punch p2 = punchDAO.find(punchid);

        /* Compare Punches */
        
        assertEquals(badgeid, p2.getBadge().getId());

        rts = p2.getOriginaltimestamp();

        assertEquals(terminalid, p2.getTerminalid());
        assertEquals(punchtype, p2.getPunchtype());
        assertEquals(ots.format(dtf), rts.format(dtf));

    }

    @Test
    public void testCreatePunchUnauthorized() { 
        /* Testing create() with unauthorized punch */
         
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        // This employee only has access to terminalid = 103, not 107. 

        Punch p1 = new Punch(107, badgeDAO.find("021890C0"), EventType.CLOCK_OUT); 

        /* Create Timestamp Objects */
        
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        
        String badgeid = p1.getBadge().getId();
        ots = p1.getOriginaltimestamp();
        int terminalid = p1.getTerminalid();
        EventType punchtype = p1.getPunchtype();

        /* Insert Punch Into Database */
        
        int punchid = punchDAO.create(p1);
        
        assertEquals(punchid, 0); // create() returns a punchID of zero when the punch fails the authorization

    }

    // START: Checking a couple different Department entries
    @Test
    public void testCreatePunchDepartmentNine() {
        // Checking departmentid = 9
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        
        Punch p1 = new Punch(104, badgeDAO.find("07901755"), EventType.CLOCK_IN);

        /* Create Timestamp Objects */
        
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        
        String badgeid = p1.getBadge().getId();
        ots = p1.getOriginaltimestamp();
        int terminalid = p1.getTerminalid();
        EventType punchtype = p1.getPunchtype();

        /* Insert Punch Into Database */
        
        int punchid = punchDAO.create(p1);

        /* Retrieve New Punch */
        
        Punch p2 = punchDAO.find(punchid);

        /* Compare Punches */
        
        assertEquals(badgeid, p2.getBadge().getId());

        rts = p2.getOriginaltimestamp();

        assertEquals(terminalid, p2.getTerminalid());
        assertEquals(punchtype, p2.getPunchtype());
        assertEquals(ots.format(dtf), rts.format(dtf));

    }
    
    @Test
    public void testCreatePunchDepartmentTen() {
        // Checking departmentid = 10
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        
        Punch p1 = new Punch(104, badgeDAO.find("3282F212"), EventType.CLOCK_IN);

        /* Create Timestamp Objects */
        
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        
        String badgeid = p1.getBadge().getId();
        ots = p1.getOriginaltimestamp();
        int terminalid = p1.getTerminalid();
        EventType punchtype = p1.getPunchtype();

        /* Insert Punch Into Database */
        
        int punchid = punchDAO.create(p1);

        /* Retrieve New Punch */
        
        Punch p2 = punchDAO.find(punchid);

        /* Compare Punches */
        
        assertEquals(badgeid, p2.getBadge().getId());

        rts = p2.getOriginaltimestamp();

        assertEquals(terminalid, p2.getTerminalid());
        assertEquals(punchtype, p2.getPunchtype());
        assertEquals(ots.format(dtf), rts.format(dtf));

    }
    // END
    
    // START: Checking a couple different Employee entries
    @Test
    public void testCreatePunchEmployeeThree() {
        // Checking employeeid = 3. Badge number: "0886BF12"  
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        
        Punch p1 = new Punch(103, badgeDAO.find("0886BF12"), EventType.CLOCK_IN);

        /* Create Timestamp Objects */
        
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        
        String badgeid = p1.getBadge().getId();
        ots = p1.getOriginaltimestamp();
        int terminalid = p1.getTerminalid();
        EventType punchtype = p1.getPunchtype();

        /* Insert Punch Into Database */
        
        int punchid = punchDAO.create(p1);

        /* Retrieve New Punch */
        
        Punch p2 = punchDAO.find(punchid);

        /* Compare Punches */
        
        assertEquals(badgeid, p2.getBadge().getId());

        rts = p2.getOriginaltimestamp();

        assertEquals(terminalid, p2.getTerminalid());
        assertEquals(punchtype, p2.getPunchtype());
        assertEquals(ots.format(dtf), rts.format(dtf));

    }
    
    @Test
    public void testCreatePunchEmployeeFour() {
        // Checking employeeid = 4. Badge number: "08D01475"  
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        
        Punch p1 = new Punch(104, badgeDAO.find("08D01475"), EventType.CLOCK_IN);

        /* Create Timestamp Objects */
        
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        
        String badgeid = p1.getBadge().getId();
        ots = p1.getOriginaltimestamp();
        int terminalid = p1.getTerminalid();
        EventType punchtype = p1.getPunchtype();

        /* Insert Punch Into Database */
        
        int punchid = punchDAO.create(p1);

        /* Retrieve New Punch */
        
        Punch p2 = punchDAO.find(punchid);

        /* Compare Punches */
        
        assertEquals(badgeid, p2.getBadge().getId());

        rts = p2.getOriginaltimestamp();

        assertEquals(terminalid, p2.getTerminalid());
        assertEquals(punchtype, p2.getPunchtype());
        assertEquals(ots.format(dtf), rts.format(dtf));

    }
    // END
}
