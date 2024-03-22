package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
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
    
    // Constructor for a new punch object that hasn't been added to the database
    public Punch (int terminalid, Badge badge, EventType punchtype) {
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchtype = punchtype;
        
        // Initializing time stamp to the current time
        this.originaltimestamp = LocalDateTime.now();
    }
    
    // Constructor for existing punches
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
        
        System.out.println("Original punch time" + this.originaltimestamp);
        System.out.println("Punchtype " + (isClockIn ? "Clock In" : "Clock Out"));
        
        //---Adjustment logic rules start---
        // Identify early clock in
        if (isClockIn && punchTime.isBefore(s.getShiftstart())){
            //Adjustment type: Early clock in 
            // punchTime.plusMinutes(adds15min) Calculates new time if new time passes (isAfter) s.getShiftstart
            //time snaps back to Shift_Start
            if (punchTime.plusMinutes(s.getRoundinterval()).isAfter(s.getShiftstart())){
                this.adjustedtimestamp = LocalDateTime.of(this.originaltimestamp.toLocalDate(), s.getShiftstart());
                this.adjustmenttype = PunchAdjustmentType.SHIFT_START;
            }
        }
        
        //Late clock in adjustment (Grace Period or Dock Penalty
        if (isClockIn && punchTime.isAfter(s.getShiftstart())){
            if (punchTime.isBefore(s.getShiftstart().plusMinutes(s.getGraceperiod()))){
                //Within grace period 5mins late shift back to Shift Start
                this.adjustedtimestamp = LocalDateTime.of(this.originaltimestamp.toLocalDate(), s.getShiftstart());
                this.adjustmenttype = PunchAdjustmentType.SHIFT_START;
            } else if (punchTime.isBefore(s.getShiftstart().plusMinutes(s.getDockpenalty()))){
                //past grace period but before dock penalty
                this.adjustedtimestamp = LocalDateTime.of(this.originaltimestamp.toLocalDate(), s.getShiftstart().plusMinutes(s.getDockpenalty()));
                this.adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
            }
        }
        
        //Early clock out adjustment
        if (isClockOut && punchTime.isBefore(s.getShiftstop())){
            //Check if within grace period 5mins clockout early
            if (punchTime.plusMinutes(s.getGraceperiod()).isAfter(s.getShiftstop())){
                //considered within grace period shift time back to Shiftstop
                this.adjustedtimestamp = LocalDateTime.of(this.originaltimestamp.toLocalDate(), s.getShiftstop());
                this.adjustmenttype = PunchAdjustmentType.SHIFT_STOP;
            } else if (punchTime.plusMinutes(s.getDockpenalty()).isBefore(s.getShiftstop())){
                //adjust time stamp to dock penalty
                this.adjustedtimestamp = LocalDateTime.of(this.originaltimestamp.toLocalDate(), s.getShiftstop().minusMinutes(s.getDockpenalty()));
                this.adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
            }
            
        }
        
        //Lunch adjustments
        
        
        //interval round
        
        
        //none
        
        
        
        
    }
    
    
    // PrintOriginal 
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

}
