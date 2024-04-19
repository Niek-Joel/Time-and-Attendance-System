package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

/**
 * 
 *
 * The Shift class represents a work shift with various properties such as start time, stop time, lunch duration, etc.
 * Instances of this class are used to store and manage shift information.
 * <p>
 * A Shift object is constructed using a map containing shift details, and provides methods to access and manipulate
 * these details.
 * </p>
 * @author aseel
 */
public class Shift {

    private final String description;
    private final int id;
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

     /**
     * Constructs a Shift object using the provided map of shift details.
     *
     * @param shiftmap a map containing shift details (key-value pairs)
     */
    public Shift(HashMap<String, String> shiftmap) {
        description = (String) shiftmap.get("Description");

        shiftstart = LocalTime.parse(shiftmap.get("Shift Start"));
        shiftstop = LocalTime.parse(shiftmap.get("Shift Stop"));

        roundinterval = Integer.parseInt(shiftmap.get("Round Interval"));
        graceperiod = Integer.parseInt(shiftmap.get("Grace Period"));
        dockpenalty = Integer.parseInt(shiftmap.get("Dock Penalty"));
        lunchstart = LocalTime.parse(shiftmap.get("Lunch Start"));
        lunchstop = LocalTime.parse(shiftmap.get("Lunch Stop"));
        lunchthreshold = Integer.parseInt(shiftmap.get("Lunch Threshold"));
        id = Integer.parseInt(shiftmap.get("id"));

        lunchduration = ChronoUnit.MINUTES.between(lunchstart, lunchstop);
        shiftduration = ChronoUnit.MINUTES.between(shiftstart, shiftstop);

    }
    

    // Getter methods for accessing shift details
    /**
     * Returns the description of the shift.
     *
     * @return the description of the shift
     */
    public String getDescription() {
        return description;
    }
    
     /**
     * Returns the ID of the shift.
     *
     * @return the ID of the shift
     */
    public int getID() {
        return id;
    }
    
     /**
     * Returns the start time of the shift.
     *
     * @return the start time of the shift
     */
    public LocalTime getShiftstart() {
        return shiftstart;
    }
    
    /**
     * Returns the stop time of the shift.
     *
     * @return the stop time of the shift
     */
    public LocalTime getShiftstop() {
        return shiftstop;
    }
    
    /**
     * Returns the round interval for the shift.
     *
     * @return the round interval for the shift
     */
    public int getRoundinterval() {
        return roundinterval;
    }
    
     /**
     * Returns the grace period for the shift.
     *
     * @return the grace period for the shift
     */
    public int getGraceperiod() {
        return graceperiod;
    }
    
    /**
     * Returns the dock penalty for the shift.
     *
     * @return the dock penalty for the shift
     */
    public int getDockpenalty() {
        return dockpenalty;
    }
    
    /**
     * Returns the start time of the lunch break.
     *
     * @return the start time of the lunch break
     */
    public LocalTime getLunchstart() {
        return lunchstart;
    }
    
     /**
     * Returns the stop time of the lunch break.
     *
     * @return the stop time of the lunch break
     */
    public LocalTime getLunchstop() {
        return lunchstop;
    }
    
     /**
     * Returns the threshold for the lunch break.
     *
     * @return the threshold for the lunch break
     */
    public int getLunchthreshold() {
        return lunchthreshold;
    }
    
     /**
     * Returns the duration of the lunch break in minutes.
     *
     * @return the duration of the lunch break in minutes
     */
    public long getLunchduration() {

        return lunchduration;

    }
    
    /**
     * Returns the duration of the shift in minutes.
     *
     * @return the duration of the shift in minutes
     */
    public long getShiftduration() {
        return shiftduration;
    }
    
    
    /**
     * Returns a string representation of the shift.
     *
     * @return a string representation of the shift
     */
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
