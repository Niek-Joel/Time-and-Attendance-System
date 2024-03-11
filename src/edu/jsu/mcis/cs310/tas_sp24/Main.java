package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        
        // test database connectivity; get DAOs

        DAOFactory daoFactory = new DAOFactory("tas.jdbc");
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        
        // find badge

        Badge b = badgeDAO.find("C4F37EFF");
        
        // output should be "Test Badge: #C4F37EFF (Welch, Travis C)"
        
        System.err.println("Test Badge: " + b.toString());
        
        // START: Punch/PunchDAO Create testing
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        
        Punch p = new Punch(9999, b, EventType.CLOCK_OUT);
        
        int punchid = punchDAO.create(p);
        System.out.println("");
        System.out.println("Create returns punchid which is " + punchid + ". (0 means its incorrect)");
        // END: Punch/PunchDAO create testing
    }

}
