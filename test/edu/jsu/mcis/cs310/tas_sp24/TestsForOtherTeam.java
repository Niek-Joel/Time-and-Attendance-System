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
import static org.junit.Assert.assertNull;


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
    // Author: Joel Cain
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

    // A bunch of random punches throughout the day. Tests accuracy by using calculate total minutes.
    // Author: Joel Cain
    @Test
    public void test2() {
        // Creating a few different punches for an employee in a single day

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        Badge b1 = badgeDAO.find("12565C60");        

        Shift s = shiftDAO.find(b1);  // Shift 1

                                                     // YEAR MM  DD    HH  MM  SS 
        LocalDateTime timeStampOne =   LocalDateTime.of(2024, 4, 15,   07, 02, 01); // Clock in for shift One
        LocalDateTime timeStampTwo =   LocalDateTime.of(2024, 4, 15,   11, 59, 13); // Clock out for lunch
        LocalDateTime timeStampThree = LocalDateTime.of(2024, 4, 15,   12, 25, 10); // Clock in for lunch stop
        LocalDateTime timeStampFour =  LocalDateTime.of(2024, 4, 15,   12, 29, 42); // Another Clock in for some reason
        LocalDateTime timeStampFive =  LocalDateTime.of(2024, 4, 15,   15, 17, 22); // Clock out
        LocalDateTime timeStampSix =   LocalDateTime.of(2024, 4, 15,   15, 42, 15); // Clock out
        LocalDateTime timeStampSeven = LocalDateTime.of(2024, 4, 15,   16, 22, 32); // Clock in
        LocalDateTime timeStampEight = LocalDateTime.of(2024, 4, 15,   18, 43, 31); // Clock out

        
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

        // Adding to an array. Tried to just use the list method from punchDAO but there is an issue. 
        // I believe it is because Junit and mySQL aren't meshing properly. But that's above my paygrade.
        ArrayList<Punch> punchList = new ArrayList<>();
        punchList.add(p1);
        punchList.add(p2);
        punchList.add(p3);
        punchList.add(p4);
        punchList.add(p5);
        punchList.add(p6);
        punchList.add(p7);
        punchList.add(p8);

        for (Punch punch: punchList) {
            punch.adjust(s);
        }

        int m = DAOUtility.calculateTotalMinutes(punchList, s);
        
        assertEquals(570, m);
    }

    // Almost a carbon copy of test2. Just changes one line to check an odd edge case. 
    // Author: Joel Cain
    @Test
    public void test3() {
        // Creating a few different punches for an employee in a single day

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        Badge b1 = badgeDAO.find("12565C60");        

        Shift s = shiftDAO.find(b1);  // Shift 1

                                                     // YEAR MM  DD    HH  MM  SS 
        LocalDateTime timeStampOne =   LocalDateTime.of(2024, 4, 15,   07, 02, 01); // Clock in for shift One
        LocalDateTime timeStampTwo =   LocalDateTime.of(2024, 4, 15,   11, 59, 13); // Clock out for lunch
        LocalDateTime timeStampThree = LocalDateTime.of(2024, 4, 15,   12, 01, 10); // Clock in for lunch stop
        LocalDateTime timeStampFour =  LocalDateTime.of(2024, 4, 15,   12, 29, 42); // Another Clock in for some reason
        LocalDateTime timeStampFive =  LocalDateTime.of(2024, 4, 15,   15, 17, 22); // Clock out
        LocalDateTime timeStampSix =   LocalDateTime.of(2024, 4, 15,   15, 42, 15); // Clock out
        LocalDateTime timeStampSeven = LocalDateTime.of(2024, 4, 15,   16, 22, 32); // Clock in
        LocalDateTime timeStampEight = LocalDateTime.of(2024, 4, 15,   18, 43, 31); // Clock out

        
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

        // Adding to an array. Tried to just use the list method from punchDAO but there is an issue. 
        // I believe it is because Junit and mySQL aren't meshing properly. But that's above my paygrade.
        ArrayList<Punch> punchList = new ArrayList<>();
        punchList.add(p1);
        punchList.add(p2);
        punchList.add(p3);
        punchList.add(p4);
        punchList.add(p5);
        punchList.add(p6);
        punchList.add(p7);
        punchList.add(p8);

        for (Punch punch: punchList) {
            punch.adjust(s);
        }

        int m = DAOUtility.calculateTotalMinutes(punchList, s);
        
        assertEquals(570, m);
    }
    
    @Test
    // Author: Corey Roberts
    //tests punch at shift change
    public void test4() {
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        
        Badge testBadge = badgeDAO.find("76B87761"); // Assume valid badge ID
        Shift testShift = shiftDAO.find(testBadge); // Assume this retrieves a valid shift

        int hypotheticalPunchId = 9999; 

         LocalDateTime shiftStartTime = LocalDateTime.of(2018, 10, 11, 
                                                    testShift.getShiftstart().getHour(), 
                                                    testShift.getShiftstart().getMinute(), 0);

        
        Punch shiftChangePunch = new Punch(hypotheticalPunchId, testShift.getID(), testBadge, 
                                       shiftStartTime, EventType.CLOCK_IN);
       

        punchDAO.create(shiftChangePunch);
        shiftChangePunch.adjust(testShift);

        // Use assertEquals to compare LocalDateTime objects directly
        assertEquals("Punch time should be adjusted to shift start time", shiftStartTime, shiftChangePunch.getAdjustedtimestamp());
    }

    // test to account for leap year
    // Author: Corey Roberts
    @Test
    public void test5(){
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        
        //badge and shift
        Badge testBadge = badgeDAO.find("ValidBadgeId");
        
        
        if (testBadge != null) {
        Shift testShift = shiftDAO.find(testBadge);
        
        //leap year date
        LocalDateTime leapYearDate = LocalDateTime.of(2024, 2, 29, 8, 0);
        
        Punch leapDayPunch = new Punch(51, testShift.getID(), testBadge, 
                                   leapYearDate, EventType.CLOCK_IN);

        
        punchDAO.create(leapDayPunch);
        leapDayPunch.adjust(testShift);
        
        assertEquals("Leap day punch should maintain the original month and day", 29 , leapDayPunch.getAdjustedtimestamp().getDayOfMonth());
        
        }    
    }

    // Author: Corey Roberts
    @Test
    public void test6() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        
        // Test with a badge ID that does not exist
        Badge b = badgeDAO.find("nonexistent");
        assertNull("Badge should not be found", b);
    } 

}
