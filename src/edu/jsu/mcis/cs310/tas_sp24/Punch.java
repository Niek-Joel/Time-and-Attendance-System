package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalDateTime;

/**
 *
 * @author Joel Cain
 */
public class Punch {
    private int id;
    private int terminalid;
    private Badge badge;
    private LocalDateTime originaltimestamp;
    private EventType punchtype;
    private LocalDateTime adjustedtimestamp; 
    private PunchAdjustmentType adjustmenttype;
    
    // Constructor for a new punch object that hasn't been added to the database
    Punch (int terminalid, Badge badge, EventType punchtype) {
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchtype = punchtype;
    }
    
    // Constructor for existing punches
    Punch (int id, int terminalid, Badge badge, LocalDateTime originaltimestamp, EventType punchtype) {
        this.id = id;
        this.terminalid = terminalid;
        this.badge = badge;
        this.originaltimestamp = originaltimestamp;
        this.punchtype = punchtype;
    }
    
    
    
    // Needs getters
    
    // Needs tostring
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append("id=" + id).append(' ').append("terminalid=" + terminalid).append(' ').append("badge=" + badge).append(' ');
        s.append("originaltimestamp=" + originaltimestamp).append(' ').append("punchtype=" + punchtype).append(' ');
        
        return s.toString();

    }
}
