
package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalTime;
import java.util.HashMap;

/**
 *
 * @author aseel
 */
public class Shift {
    private final String description ;
    private final  int shiftid;
    private final LocalTime shiftstart;
    private final LocalTime shiftstop;
    private final int roundinterval;
    private final LocalTime graceperiod;
    private final int dockpenally;
    private final LocalTime lunchstart;
    private final LocalTime lunchstop;
    private final int lunchthreshold;
    private final int lunchduration;
    private final int  shiftduration;

    /*constructor 
    * parameter values can be stored in the map as strings and passed as a single argument to the Shift constructor;
    * the constructor can then retrieve these values from the map and convert them to their native types.
    */
     public Shift(HashMap<String,String> shiftmap){
        this.shiftid = Integer.parseInt(shiftmap.get("shiftid"));
        this.description = (String)shiftmap.get("description");
        this.shiftstart = LocalTime.parse(shiftmap.get("shiftstart"));
        this.shiftstop = LocalTime.parse(shiftmap.get("shiftstop"));
        this.roundinterval = Integer.parseInt(shiftmap.get("roundinterval"));
        this.graceperiod = LocalTime.parse(shiftmap.get("graceperiod"));
        this.dockpenally = Integer.parseInt(shiftmap.get("dockpenally"));
        this.lunchstart = LocalTime.parse(shiftmap.get("lunchstart"));
        this.lunchstop = LocalTime.parse(shiftmap.get("lunchstop"));
        this.lunchthreshold = Integer.parseInt(shiftmap.get("lunchthreshold"));
        this.lunchduration = Integer.parseInt(shiftmap.get("lunchduration"));
        this.shiftduration = Integer.parseInt(shiftmap.get("shiftduration"));
        
     }
     
     
     //get() methods
     public String getDescription() {
        return description;
    }
     public int getShiftid() {
        return shiftid;
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
     public LocalTime getGraceperiod(){
        return graceperiod;
     }
     public int getDockpenally(){
        return dockpenally;
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
     public Integer getLunchduration() {
        return lunchduration;
    }

      public Integer getShiftduration() {
        return shiftduration;
    }


    @Override
    public String toString() {

      StringBuilder s = new StringBuilder();
     s.append('"')
     .append(description)
     .append(':')
     .append(shiftstart)
     .append("-")
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
