package domain;

import db.SubjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import servlet.GetAllSubjectsServlet;
import util.UnitOfWork;

import java.util.List;

public class Subject {
    private final static Logger logger = LogManager.getLogger(Subject.class);

    private Integer id;
    private String subjectCode;
    private List<Exam> exams;
    private List<Student> students;
    private String description;
    // TODO: replace adminId to InstructorId
    private List<Instructor> instructors;
    private int adminId;

    public Subject() {
    }

    public Subject(String description ,String subjectCode, int adminId, List<Student> students,
                   List<Instructor> instructors) {
        this.description = description;
        this.subjectCode = subjectCode;
        this.students = students;
        this.adminId = adminId;
        this.instructors = instructors;

        UnitOfWork.getInstance().registerNewObject(this);
    }

    public Subject(int id, String description, String subjectCode, List<Exam> exams, List<Student> students,
                   int adminId) {
        this.subjectCode = subjectCode;
        this.exams = exams;
        this.students = students;
        this.id = id;
        this.description = description;
        this.adminId = adminId;
    }

    public List<Instructor> getInstructors() {
        if (this.instructors == null) {
            load();
        }
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setDescription(String description) {
        this.description = description;
//        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public String getDescription() {
        if (this.description == null) {
            load();
        }
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubjectCode() {
        if (this.subjectCode == null) {
            load();
        }
        return subjectCode;
    }

    public List<Exam> getExams() {
        if (this.exams == null) {
            load();
        }
        return exams;
    }

    public Integer getId() {
        return id;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setStudents(List<Student> students) {
        this.students = students;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    private void load() {
        logger.info("here: " + this.id);
        Subject subject = SubjectMapper.loadWithId(this.id);
        if (this.exams == null) {
            this.exams = subject.exams;
        }
        if (this.students == null) {
            this.students = subject.students;
        }
        if (this.subjectCode == null) {
            this.subjectCode = subject.subjectCode;
        }
        if (this.description == null) {
            this.description = subject.description;
        }
        if (this.instructors == null) {
            this.instructors = subject.instructors;
        }
    }
}
