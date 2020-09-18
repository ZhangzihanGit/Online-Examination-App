package domain;

import java.util.List;

public class Instructor {
    private int instructorID;
    private String name;
    private List<Subject> subjcts;
    private UserType userType;

    public Instructor(int instructorID, String name, List<Subject> subjcts, UserType userType) {
        this.instructorID = instructorID;
        this.name = name;
        this.subjcts = subjcts;
        this.userType = userType;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public String getName() {
        return name;
    }

    public List<Subject> getSubjcts() {
        return subjcts;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubjcts(List<Subject> subjcts) {
        this.subjcts = subjcts;
    }
}
