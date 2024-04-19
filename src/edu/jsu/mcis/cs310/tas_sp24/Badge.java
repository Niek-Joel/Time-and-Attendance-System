package edu.jsu.mcis.cs310.tas_sp24;

/**
 * Represents a badge used by employees for identification.
 * 
 * <p>A badge object contains an ID and a description. The ID is a unique identifier
 * associated with the badge, while the description provides additional information
 * about the badge.</p>
 */
public class Badge {

    private final String id, description;

    public Badge(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');

        return s.toString();

    }

}
