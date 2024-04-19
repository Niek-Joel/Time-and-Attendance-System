package edu.jsu.mcis.cs310.tas_sp24;
import edu.jsu.mcis.cs310.tas_sp24.dao.BadgeDAO;
import edu.jsu.mcis.cs310.tas_sp24.dao.DAOFactory;
import edu.jsu.mcis.cs310.tas_sp24.dao.DAOUtility;
import edu.jsu.mcis.cs310.tas_sp24.dao.PunchDAO;
import edu.jsu.mcis.cs310.tas_sp24.dao.ShiftDAO;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;


/**
 *
 * @author fcftr
 */
public class TestsForOtherTeam {
    
    private DAOFactory daoFactory;

    
    @Before
    public void setup() {
        daoFactory = new DAOFactory("tas.jdbc");
    }
    
    // This tests TimeAccruedTotal. Makes sure that they aren't counting the time between a clock in and a time out.
    @Test
    public void test1() {
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();


        Punch p = punchDAO.find(634);
        Badge b = p.getBadge();
        Shift s = shiftDAO.find(b);
        
        
        /* Get/Adjust Punch List */

        ArrayList<Punch> dailypunchlist = punchDAO.list(b, p.getOriginaltimestamp().toLocalDate());

        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
            System.out.println(punch);
        }

        /* Compute Pay Period Total */
        
        int m = DAOUtility.calculateTotalMinutes(dailypunchlist, s);

        /* Compare to Expected Value */
        
        assertEquals(0, m);
    }

    @Test
    public void test2() {
        // Creating a few different punches for an employee in a single day

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        Badge b1 = badgeDAO.find("12565C60");        

        Shift s = shiftDAO.find(b1);  // Shift 1

                                                     // YEAR MM DD    HH  MM  SS 
        LocalDateTime timeStampOne =   LocalDateTime.of(2024, 8, 7,   07, 00, 00); // Clock in for shift One
        LocalDateTime timeStampTwo =   LocalDateTime.of(2024, 8, 7,   11, 59, 13); // Clock out for lunch
        LocalDateTime timeStampThree = LocalDateTime.of(2024, 8, 7,   12, 25, 10); // Clock in for lunch stop
        LocalDateTime timeStampFour =  LocalDateTime.of(2024, 8, 7,   12, 29, 00); // Another Clock in for some reason
        LocalDateTime timeStampFive =  LocalDateTime.of(2024, 8, 7,   15, 00, 00); // Clock out
        LocalDateTime timeStampSix =   LocalDateTime.of(2024, 8, 7,   15, 20, 00); // Clock out
        LocalDateTime timeStampSeven = LocalDateTime.of(2024, 8, 7,   16, 00, 00); // Clock in
        LocalDateTime timeStampEight = LocalDateTime.of(2024, 8, 7,   18, 00, 00); // Clock out

        
        Punch p1 = new Punch(9000, 101, b1, timeStampOne, EventType.CLOCK_IN);
        int punchid = punchDAO.create(p1);
        Punch p2 = new Punch(9001, 101, b1, timeStampTwo, EventType.CLOCK_OUT);
        int punchid2 = punchDAO.create(p2);
        Punch p3 = new Punch(9002, 101, b1, timeStampThree, EventType.CLOCK_IN);
        int punchid3 = punchDAO.create(p3);
        Punch p4 = new Punch(9003, 101, b1, timeStampFour, EventType.CLOCK_IN);
        int punchid4 = punchDAO.create(p4);
        Punch p5 = new Punch(9004, 101, b1, timeStampFive, EventType.CLOCK_OUT);
        int punchid5 = punchDAO.create(p5);
        Punch p6 = new Punch(9005, 101, b1, timeStampSix, EventType.CLOCK_OUT);
        int punchid6 = punchDAO.create(p6);
        Punch p7 = new Punch(9006, 101, b1, timeStampSeven, EventType.CLOCK_IN);
        int punchid7 = punchDAO.create(p7);
        Punch p8 = new Punch(9007, 101, b1, timeStampEight, EventType.CLOCK_OUT);
        int punchid8 = punchDAO.create(p8);


        ArrayList<Punch> dailypunchlist = punchDAO.list(b1, p1.getOriginaltimestamp().toLocalDate());
        System.out.println(p1.getOriginaltimestamp().toLocalDate());

        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
            System.out.println(punch);
            System.out.println("");
            System.out.println("");
        }
        


        int m = DAOUtility.calculateTotalMinutes(dailypunchlist, s);
        
        System.out.println("m = " + m);

//        assertEquals(DUMMYINTEGER, m);
    }


}
