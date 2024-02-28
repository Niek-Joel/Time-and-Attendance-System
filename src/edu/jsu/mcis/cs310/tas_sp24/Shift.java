
package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

/**
 *
 * @author aseel
 */
public class Shift {
    private final String description ;
    private final  int id = 0;
    private final LocalTime shiftstart;
    private final LocalTime shiftstop;
    private final int roundinterval;
    private final int graceperiod;
    private final int dockpenalty;
    private final LocalTime lunchstart;
    private final LocalTime lunchstop;
    private final int lunchthreshold;
    private final long lunchduration;
    private final long shiftduration;

    /*constructor 
    * parameter values can be stored in the map as strings and passed as a single argument to the Shift constructor;
    * the constructor can then retrieve these values from the map and convert them to their native types.
    */
     public Shift(HashMap<String,String> shiftmap){
        description = (String)shiftmap.get("Description");
        
        shiftstart = LocalTime.parse(shiftmap.get("Shift Start"));
        shiftstop = LocalTime.parse(shiftmap.get("Shift Stop"));
        
        roundinterval = Integer.parseInt(shiftmap.get("Round Interval"));
        graceperiod = Integer.parseInt(shiftmap.get("Grace Period"));
        dockpenalty = Integer.parseInt(shiftmap.get("Dock Penalty"));
        lunchstart = LocalTime.parse(shiftmap.get("Lunch Start"));
        lunchstop = LocalTime.parse(shiftmap.get("Lunch Stop"));
        lunchthreshold = Integer.parseInt(shiftmap.get("Lunch Threshold"));
        
        
        lunchduration = ChronoUnit.MINUTES.between(lunchstart,lunchstop);
        shiftduration = ChronoUnit.MINUTES.between(shiftstart,shiftstop);
    
     }
     
     
     //get() methods
     public String getDescription() {
        return description;
    }
     public int getID() {
        return id;
    }
     public LocalTime getShiftstart(){
         return shiftstart;
     }
     public LocalTime getShiftstop(){
        return shiftstop;
     }
     public int getRoundinterval(){
        return roundinterval;
     }
     public int getGraceperiod(){
        return graceperiod;
     }
     public int getDockpenalty(){
        return dockpenalty;
     }
     public LocalTime getLunchstart(){
        return lunchstart;
     }
     public LocalTime getLunchstop(){
        return lunchstop;
     }
     public int getLunchthreshold(){
        return lunchthreshold;
     }
     public long getLunchduration() {
    
        return  lunchduration;
        
    }

      public long getShiftduration() {
        return shiftduration;
    }


    @Override
    public String toString() {

      StringBuilder s = new StringBuilder();
     s.append(description)
     .append(": ")
     .append(shiftstart)
     .append(" - ")
     .append(shiftstop)
     .append(" (")
     .append(shiftduration)
     .append(" minutes); Lunch: ")
     .append(lunchstart)
     .append(" - ")
     .append(lunchstop)
     .append(" (")
     .append(lunchduration)
     .append(" minutes)");
    
    return s.toString();
    }
}
     
    

