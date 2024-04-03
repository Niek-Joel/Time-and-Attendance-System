package edu.jsu.mcis.cs310.tas_sp24.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
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
}
