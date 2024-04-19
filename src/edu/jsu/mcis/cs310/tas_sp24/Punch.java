package edu.jsu.mcis.cs310.tas_sp24;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

/**
 *
 * The {@code Punch} class represents a single punch event, containing information such as
 * terminal ID, badge, original timestamp, adjusted timestamp, punch type, and adjustment type.
 *
 * <p>Objects of the {@code Punch} class can represent existing punches retrieved from the
 * database or newly-created punches that have not yet been added to the database. For new
 * punches, the original timestamp is initialized to the current time as reported by the
 * system clock.</p>
 * 
 * <p>The {@code Punch} class provides methods to adjust the punch timestamp based on
 * shift rules and calculate the adjusted timestamp and adjustment type accordingly.</p>
 *
 * <p>It also includes methods to print the punch information with the original or adjusted
 * timestamp in a formatted string representation.</p>
 * 
 * <p>When using the {@code Punch} class, it is to utilize appropriate data types
 * for each instance field,{@link LocalDateTime} for timestamps and {@link EventType}
 * for punch types.</p>
 * 
 * <p>Instances of {@code Punch} should be created using one of the provided constructors,
 * depending on whether the punch is new or existing.</p>
 * 
 * 
 * @author Joel Cain
 * @author Corey Roberts
 */
public class Punch {
    private int id;
    private int terminalid;
    private Badge badge;
    private LocalDateTime originaltimestamp;
    private LocalDateTime adjustedtimestamp; 
    private EventType punchtype;
    private PunchAdjustmentType adjustmenttype;
    
    // Constructor 
     /**
     * Constructs a new {@code Punch} object for a newly-created punch with the specified
     * terminal ID, badge, and punch type. The original timestamp is initialized to the
     * current time as reported by the system clock.
     *
     * @param terminalid the ID of the terminal where the punch occurred
     * @param badge the badge associated with the punch
     * @param punchtype the type of punch (e.g., CLOCK IN, CLOCK OUT, TIME OUT)
     */
    public Punch (int terminalid, Badge badge, EventType punchtype) {
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchtype = punchtype;
        
     // Initializing time stamp to the current time
        this.originaltimestamp = LocalDateTime.now();
    }
    
    // Constructor for existing punches
     /**
     * Constructs a new {@code Punch} object for an existing punch retrieved from the database.
     *
     * @param id the unique ID of the punch
     * @param terminalid the ID of the terminal where the punch occurred
     * @param badge the badge associated with the punch
     * @param originaltimestamp the original timestamp of the punch
     * @param punchtype the type of punch (e.g., CLOCK IN, CLOCK OUT, TIME OUT)
     */
    public Punch (int id, int terminalid, Badge badge, LocalDateTime originaltimestamp, EventType punchtype) {
        this.id = id;
        this.terminalid = terminalid;
        this.badge = badge;
        this.originaltimestamp = originaltimestamp;
        this.punchtype = punchtype;
    }
    
    //Getters
    public int getId(){
        return id;
    }
    
    public int getTerminalid(){
        return terminalid;
    }
    
    public Badge getBadge(){
        return badge;
    }
    
    public LocalDateTime getOriginaltimestamp(){
        return originaltimestamp;
    }
    
    public EventType getPunchtype(){
        return punchtype;
    }
    
    public LocalDateTime getAdjustedtimestamp(){
        return adjustedtimestamp;
    }
    
    public PunchAdjustmentType getAdjustmentType(){
        return adjustmenttype;
    }
    
    //Adjust Method
    public void adjust(Shift s){
        //Extract time from punches original timestamp
        LocalTime punchTime = this.originaltimestamp.toLocalTime();
        
        //Determine punch clock in or clock out
        boolean isClockIn = this.punchtype == EventType.CLOCK_IN;
        boolean isClockOut = this.punchtype == EventType.CLOCK_OUT;

        
        //---Adjustment logic rules start---
        // Early clock in for shift start
        if (isClockIn && isIntervalBeforeShiftStart(s) ){
            //Adjustment type: Early clock in 
            // punchTime.plusMinutes(adds15min) Calculates new time if new time passes (isAfter) s.getShiftstart
            //time snaps back to Shift_Start
            if (punchTime.plusMinutes(s.getRoundinterval()).isAfter(s.getShiftstart())){
                this.adjustedtimestamp = LocalDateTime.of(this.originaltimestamp.toLocalDate(), s.getShiftstart());
                this.adjustmenttype = PunchAdjustmentType.SHIFT_START;
            }
        }
        
        // Late clock in adjustment for Grace Period and Dock Penalty
        if (isClockIn && isWithinDockStart(s)){
            // Late clock in for shift start, within grace period
            if (isWithinGraceStart(s)){ 
                //Within grace period 5mins late shift back to Shift Start
                this.adjustedtimestamp = LocalDateTime.of(this.originaltimestamp.toLocalDate(), s.getShiftstart());
                this.adjustmenttype = PunchAdjustmentType.SHIFT_START;
            } 
            // Late clock in for shift start, within dock period
            else {
                //past grace period but before dock penalty
                this.adjustedtimestamp = LocalDateTime.of(this.originaltimestamp.toLocalDate(), s.getShiftstart().plusMinutes(s.getDockpenalty()));
                this.adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
            }
        }
        
        if (isWithinLunch(s)) {
            // Late Clock in for lunch
            if (isClockIn) {
                this.adjustedtimestamp = LocalDateTime.of(this.originaltimestamp.toLocalDate(), s.getLunchstop());
                this.adjustmenttype = PunchAdjustmentType.LUNCH_STOP;
            }
            if (isClockOut) {
                this.adjustedtimestamp = LocalDateTime.of(this.originaltimestamp.toLocalDate(), s.getLunchstart());
                this.adjustmenttype = PunchAdjustmentType.LUNCH_START;
            }
        }
        
        // Early clock out adjustment for grace period and dock penalty
        if (isClockOut && isWithinDockStop(s)){
            // Early clock out for shift stop, within grace period
            if (isWithinGraceEnd(s)) {
                //considered within grace period shift time back to Shiftstop
                this.adjustedtimestamp = LocalDateTime.of(this.originaltimestamp.toLocalDate(), s.getShiftstop());
                this.adjustmenttype = PunchAdjustmentType.SHIFT_STOP;
            } 
            // Early clock out for shift stop, within dock period
            else{
                //adjust time stamp to dock penalty
                this.adjustedtimestamp = LocalDateTime.of(this.originaltimestamp.toLocalDate(), s.getShiftstop().minusMinutes(s.getDockpenalty()));
                this.adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
            }       
        }
        
        // Late Clock out for shift stop
        if (isClockOut && isIntervalAfterShiftStop(s)) {
            // If within Interval for clock out after shift stop 
            if (punchTime.minusMinutes(s.getRoundinterval()).isBefore(s.getShiftstop())) {
                this.adjustedtimestamp = LocalDateTime.of(this.originaltimestamp.toLocalDate(), s.getShiftstop());
                this.adjustmenttype = PunchAdjustmentType.SHIFT_STOP;
            }
        }

        // Calculate hours and minutes on timestamp. Seconds and nano left as zero
        int totalMinutes = punchTime.getMinute() + punchTime.getHour()*60;
        boolean isOnIcrement = ((double)totalMinutes % s.getRoundinterval() == 0.0);  // Disregard seconds as per instructions
        int seconds = punchTime.getSecond();
        if (seconds > 30) {
            totalMinutes++;
        }   
        double totalIncrements = (double)totalMinutes/s.getRoundinterval(); // Casting to double so the int/int equation doesn't truncate
        totalIncrements = Math.round(totalIncrements);
        totalMinutes = (int)totalIncrements*s.getRoundinterval(); // Casting back to int, double was rounded on above line
           
        // For 24 hour time format
        int hour = totalMinutes/60; 
        int minute = totalMinutes - hour*60;

        // The second part of the conditional is needed because all prior adjusts move the timestamp to a perfect interval
        if (isOnIcrement && this.adjustedtimestamp == null) {
            this.adjustmenttype = PunchAdjustmentType.NONE;
        }
        
        // If timestamp not on any prior zones, then move timestamp to nearest round interval 
        if (this.adjustedtimestamp == null) {
            this.adjustedtimestamp = LocalDateTime.of(this.originaltimestamp.toLocalDate(), LocalTime.of(hour, minute, 0));
            if (this.adjustmenttype == null) {
                this.adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;    
            }
        }
        
        // If on weekend 
        if (this.getOriginaltimestamp().getDayOfWeek() == DayOfWeek.SATURDAY || this.originaltimestamp.getDayOfWeek() == DayOfWeek.SUNDAY) {
            this.adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
        }
        

    }
    
    
    // Helper funciton for Adjust. Checks if punchTime is within or on the edge of Interval before shift start
    public boolean isIntervalBeforeShiftStart(Shift s) {
        LocalTime punchTime = this.originaltimestamp.toLocalTime();
        LocalTime intervalStart = s.getShiftstart().minusMinutes(s.getDockpenalty());
        
        if (( punchTime.isAfter(intervalStart) || punchTime.equals(intervalStart)) &&
             (punchTime.isBefore(s.getShiftstart()) || punchTime.equals(s.getShiftstart()))){
            return true;
        }
        else {
            return false;
        }
    }
    
    // Helper funciton for Adjust. Checks if punchTime is within or on the edge of dock period after shift start
    public boolean isWithinDockStart(Shift s) {
        LocalTime punchTime = this.originaltimestamp.toLocalTime();
        LocalTime dockEnd = s.getShiftstart().plusMinutes(s.getDockpenalty());
        
        if ( punchTime.isAfter(s.getShiftstart()) && 
            (punchTime.isBefore(dockEnd) || punchTime.equals(dockEnd))){
            return true;
        }
        else {
            return false;
        }
    }
    
    // Helper funciton for Adjust. Checks if punchTime is within or on the edge of lunch period
    public boolean isWithinLunch(Shift s) {
        LocalTime punchTime = this.originaltimestamp.toLocalTime();
        
        if ((punchTime.isAfter(s.getLunchstart()) || punchTime.equals(s.getLunchstart()) )&& 
            (punchTime.isBefore(s.getLunchstop()) || punchTime.equals(s.getLunchstop()))){
            return true;
        }
        else {
            return false;
        }
    }
    
    // Helper funciton for Adjust. Checks if punchTime is within or on the edge of dock period before shift stop
    public boolean isWithinDockStop(Shift s) {
        LocalTime punchTime = this.originaltimestamp.toLocalTime();
        LocalTime dockStart = s.getShiftstop().minusMinutes(s.getDockpenalty());
        
        if (( punchTime.isAfter(dockStart) || punchTime.equals(dockStart)) &&
             (punchTime.isBefore(s.getShiftstop()) || punchTime.equals(s.getShiftstop()))){
            return true;
        }
        else {
            return false;
        }
    }
    
    // Helper funciton for Adjust. Checks if punchTime is within or on the edge of Interval after shift stop
    public boolean isIntervalAfterShiftStop(Shift s) {
        LocalTime punchTime = this.originaltimestamp.toLocalTime();
        LocalTime intervalStop = s.getShiftstop().plusMinutes(s.getDockpenalty());
        
        if (( punchTime.isAfter(s.getShiftstop()) || punchTime.equals(s.getShiftstop())) &&
             (punchTime.isBefore(intervalStop) || punchTime.equals(intervalStop))){
            return true;
        }
        else {
            return false;
        }
    }
    
    // Helper funciton for Adjust. Checks if punchTime is within or on the edge of grace period after shift start
    public boolean isWithinGraceStart(Shift s) {
        LocalTime punchTime = this.originaltimestamp.toLocalTime();
        LocalTime graceEnd = s.getShiftstart().plusMinutes(s.getGraceperiod());
        
        if (( punchTime.isBefore(graceEnd) || punchTime.equals(graceEnd)) &&
             (punchTime.isAfter(s.getShiftstart()) || punchTime.equals(s.getShiftstart()))){
            return true;
        }
        else {
            return false;
        }
    }
    
    // Helper funciton for Adjust. Checks if punchTime is within or on the edge of grace period before shift end
    public boolean isWithinGraceEnd(Shift s) {
        LocalTime punchTime = this.originaltimestamp.toLocalTime();
        LocalTime graceStart = s.getShiftstop().minusMinutes(s.getGraceperiod());
        
        if (( punchTime.isAfter(graceStart) || punchTime.equals(graceStart)) &&
             (punchTime.isBefore(s.getShiftstop()) || punchTime.equals(s.getShiftstop()))){
            return true;
        }
        else {
            return false;
        }
    }
    
    
    // Prints original timestamp 
    public String printOriginal(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        String formattedDate = this.originaltimestamp.format(formatter).toUpperCase();
        String punchTypeString = this.punchtype.toString();
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("#")
                .append(this.badge.getId())
                .append(" ")
                .append(punchTypeString)
                .append(": ")
                .append(formattedDate);
    
        return sb.toString();
    }

    // Prints adjusted timestamp
     /**
     * Returns a formatted string representation of the punch with its adjusted timestamp.
     *
     * @return a string representation of the punch with its adjusted timestamp
     */
    public String printAdjusted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        String formattedDate = this.adjustedtimestamp.format(formatter).toUpperCase();
        String punchTypeString = this.punchtype.toString();
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("#")
                .append(this.badge.getId())
                .append(" ")
                .append(punchTypeString)
                .append(": ")
                .append(formattedDate)
                .append(" (").append(adjustmenttype).append(")");
    
        return sb.toString();
    }
    
     /**
     * Returns a string representation of the punch with its original timestamp.
     *
     * @return a string representation of the punch with its original timestamp
     */
    @Override
    public String toString() {
        return printOriginal();
    }

}
