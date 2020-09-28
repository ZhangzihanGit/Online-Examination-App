package service;

import domain.Exam;
import domain.Question;
import domain.Submission;

import java.util.List;

public interface StudentService extends UserService{
    /**
     * Update the answer given the subject ID, exam Id and questions ID.
     * @param subjectId Subject
     * @param examId Exam that the question is in
     * @param questionId The question that needs to be updated
     * @param content New content to be updated
     */
    public void updateAnswer(int subjectId, int examId, int questionId, String content);

    /**
     * Submit the exam, given the subject and exam IDs.
     * @param subjectId Subject
     * @param examId Exam that needs to be submitted
     */
    public void submitExam(int subjectId, int examId);

    /**
     * Initialisation when the exam begins, Return all questions to front end.
     * @param subjectId Subject
     * @param examId Exam
     * @return List of all questions in one exam.
     */
    public List<Question> viewAllQuestions(int subjectId, int examId);

    /**
     *  View all exams under the subject.
     * @param subjectId Subject
     * @return List of all exams under the subject
     */
    public List<Exam> viewAllExams(int subjectId);

    /**
     * View the exam given the exam ID.
     * @param subjectId Subject
     * @param examId Exam
     * @return One exam
     */
    public Exam viewExamById(int subjectId, int examId);

    public void addSubmission(Submission submission);
}
