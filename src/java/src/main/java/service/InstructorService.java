package service;

import domain.Answer;
import domain.Exam;
import domain.Question;
import domain.Submission;
import exceptions.ExamGotSubmissionException;
import exceptions.StudentTakingExamException;
import org.json.JSONArray;

import java.util.List;

public interface InstructorService extends UserService{
    public boolean checkAnySubmission(int examId, int subjectId) throws ExamGotSubmissionException;
    public boolean checkStudentTakingExam(int examId, int subjectId) throws StudentTakingExamException;
    public boolean checkUpdatePermission(int examId, int subjectId);
    /**
     * Delete the exam given the subject Id and exam Id.
     * @param subjectId Subject Id
     * @param examId Exam Id
     */
    public void deleteExam(int subjectId, int examId);

    /**
     * Add a new exam given the subject ID. Exam id will be auto-generated
     * by the DB.
     */
    public void addExam(Exam exam);


    public void addQuestions();

    public boolean checkExamSubmitted(Exam exam);

    public boolean checkStudentInExam(Exam exam);



    public Submission getSubmission(int submissionId);

    public List<Submission> getAllSubmission(int examId);

    public Answer getAnswer(int submissionId, int questionId);

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
     */
    public void updateExam(Exam exam, String newDescription, String newShowName);

    public void updatedQuestions(List<Question> originalQuestions, JSONArray newQuestionsObj, Exam exam);

    public void updateQuestions(List<Question> questions);

    /**
     * Update the marks of the student.
     * @param studentId Student Id
     */
    public void updateMarks(int studentId);

    /**
     * Cl the exam.
     * @param examId
     */
    public void closeExam(int userId, int examId, int subjectId);

    /**
     * Publish the exam and make it available to the student.
     * @param examId
     */
    public void publishExam(int userId, int examId);

}
