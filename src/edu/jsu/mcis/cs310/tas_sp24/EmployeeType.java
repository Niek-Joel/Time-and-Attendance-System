package edu.jsu.mcis.cs310.tas_sp24;

public enum EmployeeType {

    PART_TIME("Temporary / Part-Time"),
    FULL_TIME("Full-Time");
    private final String description;

    private EmployeeType(String d) {
        description = d;
    }

    @Override
    public String toString() {
        return description;
    }

}
