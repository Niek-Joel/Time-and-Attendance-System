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
        
        // For Punch and PunchDAO testing
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        Punch p = punchDAO.find(147);
        //System.out.println("Test Punch: " + p.toString());
    }

}
