package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Joel Cain
 * @author Corey Roberts
 * @author(List)Spencer Carden
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
    public ArrayList list(Badge Badge,LocalDate LocalDate){
        
        return null;
        
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
    
    
    public void adjust(Shift s){
        
        
        
    }
    
    
    // PrintOriginal 
    public String printOriginal(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        String formattedDate = this.originaltimestamp.format(formatter).toUpperCase();
    
        //using toString method of punchType (EventType enum)
        String punchTypeString = this.punchtype.toString();
    
        return String.format("#%s %s: %s", this.badge.getId(), punchTypeString, formattedDate);
    }
    
    @Override
    public String toString() {
        return printOriginal();
    }

    
}
