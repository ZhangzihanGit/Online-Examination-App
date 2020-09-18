package domain;

import java.util.List;
import java.util.Map;

public class Student {
    private int studentID;
    private List<Subject> subjects;
    private boolean status;
    private String name;
    private List<Map<Exam,Integer>> marks;
    private UserType userType;

    public Student(int studentID, List<Subject> subjects, boolean status, String name, List<Map<Exam, Integer>> marks,
                   UserType userType) {
        this.studentID = studentID;
        this.subjects = subjects;
        this.status = status;
        this.name = name;
        this.marks = marks;
        this.userType = userType;
    }

    public int getStudentID() {
        return studentID;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public boolean isStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public List<Map<Exam, Integer>> getMarks() {
        return marks;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarks(List<Map<Exam, Integer>> marks) {
        this.marks = marks;
    }
}
