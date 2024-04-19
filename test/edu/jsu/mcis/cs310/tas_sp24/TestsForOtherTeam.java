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
import java.time.format.DateTimeFormatter;


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
}
