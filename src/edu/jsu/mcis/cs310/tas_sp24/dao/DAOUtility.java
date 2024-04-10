package edu.jsu.mcis.cs310.tas_sp24.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import edu.jsu.mcis.cs310.tas_sp24.Shift;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import edu.jsu.mcis.cs310.tas_sp24.PunchAdjustmentType;
import java.time.format.DateTimeFormatter;



/*
 *
 *
 * Utility class for DAOs. This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 *
 */
public final class DAOUtility {
public static String getPunchListAsJSON(ArrayList<Punch> punchList){
    
    ArrayList<HashMap<String,String>> jsonData = new ArrayList<>();
      /*Fancy For loop for all elemnts in array */
      for(Punch punch : punchList){
      /*Create HashMap Object*(one for each Punch*/    
      HashMap<String,String> punchData = new HashMap<>();
      
        punchData.put("originaltimestamp",String.valueOf(punch.getOriginaltimestamp().format(DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss")).toUpperCase()));
        
        punchData.put("badgeid",String.valueOf(punch.getBadge().getId()));
        
        punchData.put("adjustedtimestamp", String.valueOf(punch.getAdjustedtimestamp().format(DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss")).toUpperCase()));
        
        punchData.put("adjustmenttype", String.valueOf(punch.getAdjustmentType()));
        
        punchData.put("terminalid",String.valueOf(punch.getTerminalid()));
        
        punchData.put("id",String.valueOf(punch.getId()));
        
        punchData.put("punchtype",String.valueOf(punch.getPunchtype()));
        
   
       
        
        
        
    
        jsonData.add(punchData);
        }
        String json = Jsoner.serialize(jsonData);

        return json;
    }


public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift){
    int totalMinutes = 0;
    Punch previousPunch = null;
    boolean hadLunch;
    boolean lunchStarted = false;
    boolean lunchEnded = false;
    
    //Iterate through punchlist
    for (Punch currentPunch : dailypunchlist){
        // Checking to see if they had lunch
        if (currentPunch.getAdjustmentType() == PunchAdjustmentType.LUNCH_START) {
            lunchStarted = true;
        }
        else if (currentPunch.getAdjustmentType() == PunchAdjustmentType.LUNCH_STOP) {
            lunchEnded = true;
        }
        
        //check for previous clock in clock out pair
        if (previousPunch != null && previousPunch.getPunchtype() == EventType.CLOCK_IN && currentPunch.getPunchtype() == EventType.CLOCK_OUT){
            //Determine start time using adjusted. If unavalible fallback to original
            LocalDateTime start = (previousPunch.getAdjustedtimestamp() != null) ? previousPunch.getAdjustedtimestamp() : previousPunch.getOriginaltimestamp();
            //Determine end time using adjusted. If unavalible fallback to original
            LocalDateTime end = (currentPunch.getAdjustedtimestamp() != null) ? currentPunch.getAdjustedtimestamp() : currentPunch.getOriginaltimestamp();
            
            //Calculate minute between start and end times and add them to total minutes
            //long diff = ChronoUnit.MINUTES.between(replace - start, replace - end);
            totalMinutes += ChronoUnit.MINUTES.between(start, end);
            //null previousPunch
            previousPunch = null;
            
        } else if (currentPunch.getPunchtype() ==  EventType.CLOCK_IN){
            //if punch is CLOCK_IN set it to previous punch for pairing
            previousPunch = currentPunch;
        }
    }
    
    
    if (lunchStarted && lunchEnded) {
        hadLunch = true;
    }
    else {
        hadLunch = false;
    }
    
    /*  Deduct lunch only if an employee doesn't have lunch and if they have worked more than 
    the minimum number of minuts(called lunchthreshold in the database)  */
    if (hadLunch == false){
        if (totalMinutes > shift.getLunchthreshold() && totalMinutes - shift.getLunchduration() > 0){
            totalMinutes -= shift.getLunchduration();
        }else if (totalMinutes < shift.getLunchthreshold() && totalMinutes + shift.getLunchduration() < 0){
            totalMinutes += shift.getLunchduration();
        }
    }
    
    
    return totalMinutes;
}






}
