package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.*;

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
        
        Badge z = badgeDAO.find("021890C0");
        Punch p = new Punch(103, z, EventType.CLOCK_IN);

        //int punchid = punchDAO.create(p);  // WARNING: If you run this, it will create an entry in your database
        System.out.println("");
        //System.out.println("Create returns punchid = " + punchid + ". (checking: if 0 then incorrect)");
        // END: Punch/PunchDAO create testing
    }

} 