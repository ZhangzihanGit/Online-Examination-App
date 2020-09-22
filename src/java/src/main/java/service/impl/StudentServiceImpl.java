package service.impl;

import domain.Exam;
import domain.Question;
import service.StudentService;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    /**
     * Update the answer given the subject ID, exam Id and questions ID.
     *
     * @param subjectId  Subject
     * @param examId     Exam that the question is in
     * @param questionId The question that needs to be updated
     * @param content    New content to be updated
     */
    @Override
    public void updateAnswer(int subjectId, int examId, int questionId, String content) {

    }

    /**
     * Submit the exam, given the subject and exam IDs.
     *
     * @param subjectId Subject
     * @param examId    Exam that needs to be submitted
     */
    @Override
    public void submitExam(int subjectId, int examId) {

    }

    /**
     * Initialisation when the exam begins, Return all questions to front end.
     *
     * @param subjectId Subject
     * @param examId    Exam
     * @return List of all questions in one exam.
     */
    @Override
    public List<Question> viewAllQuestions(int subjectId, int examId) {
        return null;
    }

    /**
     * View all exams under the subject.
     *
     * @param subjectId Subject
     * @return List of all exams under the subject
     */
    @Override
    public List<Exam> viewAllExams(int subjectId) {
        return null;
    }

    /**
     * View the exam given the exam ID.
     *
     * @param subjectId Subject
     * @param examId    Exam
     * @return One exam
     */
    @Override
    public Exam viewExamById(int subjectId, int examId) {
        return null;
    }
}
