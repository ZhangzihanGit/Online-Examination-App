package service;

import domain.Exam;
import domain.Subject;

import java.util.List;

// 接口可以是在controller层面解析parameter，把id发过来。
public interface InstructorService {
    /**
     * Delete the exam given the subject Id and exam Id.
     * @param subjectId Subject Id
     * @param examId Exam Id
     */
    public void deleteExam(int subjectId, int examId);

    /**
     * Add a new exam given the subject ID. Exam id will be auto-generated
     * by the DB.
     * @param subjectId Subject Id
     */
    public void addExam(int subjectId);

    /**
     * View all exams under the subject.
     * @param subjectId Subject Id
     * @return List of exams under the subject
     */
    public List<Exam> viewAllExams(int subjectId);

    /**
     * Retrieve the exam details given the subject Id and exam Id.
     * @param subjectId Subject Id
     * @param examId Exam Id
     * @return Exam
     */
    public Exam getExamById(int subjectId, int examId);

    /**
     * Update the exam, given the subject Id and exam Id.
     * @param subjectId Subject Id
     * @param examId Exam Id
     */
    public void updateExam(int subjectId, int examId);

    /**
     * Update the marks of the student.
     * @param studentId Student Id
     */
    public void updateMarks(int studentId);

    /**
     * Cl the exam.
     * @param subjectId
     * @param examId
     */
    public void closeExam(int subjectId, int examId);

    /**
     * Publish the exam and make it available to the student.
     * @param subjectId
     * @param examId
     */
    public void publishExam(int subjectId, int examId);

    public List<Subject> viewAllSubjects(int userId);
}
