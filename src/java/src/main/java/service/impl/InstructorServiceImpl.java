package service.impl;

import db.ExamMapper;
import db.InstructorMapper;
import db.SubjectMapper;
import domain.Exam;
import domain.Subject;
import domain.User;
import org.json.JSONArray;
import org.json.JSONObject;
import domain.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.InstructorService;
import util.UnitOfWork;

import java.util.ArrayList;
import java.util.List;

public class InstructorServiceImpl implements InstructorService {
    private static final Logger logger = LogManager.getLogger(InstructorServiceImpl.class);
    /**
     * Delete the exam given the subject Id and exam Id.
     *
     * @param subjectId Subject Id
     * @param examId    Exam Id
     */
    @Override
    public void deleteExam(int subjectId, int examId) {

    }

    /**
     * Add a new exam given the subject ID. Exam id will be auto-generated
     * by the DB.
     *
//     * @param subjectId Subject Id
     */
    @Override
    public void addExam(Exam exam) {
        UnitOfWork.getInstance().commit();
    }
    @Override
    public void addQuestions(){
        UnitOfWork.getInstance().commit();
    }

    /**
     * View all exams under the subject.
     *
     * @param subjectId Subject Id
     * @return List of exams under the subject
     */
    @Override
    public List<Exam> viewAllExams(int subjectId) {
        return null;
    }

    /**
     * Retrieve the exam details given the subject Id and exam Id.
     *
     * @param subjectId Subject Id
     * @param examId    Exam Id
     * @return Exam
     */
    @Override
    public Exam getExamById(int subjectId, int examId) {
        return null;
    }

    /**
     * Update the exam, given the subject Id and exam Id.
     *
     * @param subjectId Subject Id
     * @param examId    Exam Id
     */
    @Override
    public void updateExam(Exam exam) {
//        ExamMapper.updateExam(exam);
        UnitOfWork.getInstance().commit();
    }

    @Override
    public void updateQuestions(List<Question> questions) {
        for(Question q: questions) {
            UnitOfWork.getInstance().registerDirtyObject(q);
        }
        UnitOfWork.getInstance().commit();
    }

    /**
     * Update the marks of the student.
     *
     * @param studentId Student Id
     */
    @Override
    public void updateMarks(int studentId) {

    }

    /**
     * There are three scenarios in the list of new questions. Questions can be added(when no question id is provided
     * in the request body), or questions can be updated(some attributes such as options/mark is modified), or
     * question can be deleted(question id that exists in list of original questions, now doesn't exist in the
     * new questions list.
     * @param originalQuestions Original list of questions of the given exam id retrieved from the DB
     * @param newQuestionsArr Request body that contains list of questions.
     */
    @Override
    public void updatedQuestions(List<Question> originalQuestions, JSONArray newQuestionsArr, Exam exam) {
        // Case 1.
        // When there are questions deleted.
        if (newQuestionsArr.length() < originalQuestions.size()) {
            List<Integer> newQuestionIds = new ArrayList<>();
            for(int i=0; i<newQuestionsArr.length(); i++) {
                JSONObject object = newQuestionsArr.getJSONObject(i);
                if (object.has("questionId")) {
                    newQuestionIds.add(object.getInt("questionId"));
                }
            }
            for (Question q: originalQuestions) {
                // the question id existed in the original questions lists but not in the current question list.
                if (!newQuestionIds.contains(q.getQuestionID())) {
                    q.delete();
                }
            }
        }
        // The check if contents of the questions are changed.
        for (int i=0; i<newQuestionsArr.length(); i++) {
            JSONObject object = newQuestionsArr.getJSONObject(i);

            String description = object.getString("description");
            String options = object.get("options").toString();
            int mark = object.getInt("mark");
            QuestionType questionType;
            questionType = QuestionType.valueOf(object
                    .getString("questionType").toUpperCase());

            int questionId;
            // Case 2 Question content is updated
            // If the question sent in the request body has a question id associated with it,
            // The question is a question that already exist in the DB.
            if (object.has("questionId")) {
                questionId = object.getInt("questionId");
                for (Question q: originalQuestions) {
                    // Check if this is the same question, if true, go and check the attributes inside the question
                    // Exam id is not examined, since the request body was under the exam id.
                    if (q.getQuestionID()==questionId) {
                        logger.info("question with id: "+ q.getQuestionID() + " is being checked");
                        // The content of the question is updated
                        if (q.getQuestionType() != questionType) {
                            q.setQuestionType(questionType);
                        }
                        if (!q.getOptions().equals(options)) {
                            q.setOptions(options);
                        }
                        if (q.getMark()!= mark) {
                            logger.info("mark is changed. Add it to dirty obj list. ");
                            q.setMark(mark);
                        }
                        if (!q.getDescription().equals(description)) {
                            q.setDescription(description);
                        }
                    }
                }
            }
            // TODO: Question Id will not be given every time. If the question is newly created, it will not contain
            // the question id. Any ideas on that?

            // Case 3 Question is new
            else{
                Question question = new Question(description,options,questionType,exam.getId(),mark);
//                exam.getQuestions().add(question);
                originalQuestions.add(question);
            }
        }
        UnitOfWork.getInstance().commit();
    }

    /**
     * Close the exam.
     *
     * @param subjectId
     * @param examId
     */
    @Override
    public void closeExam(int userId, int examId) {
        try {
            Exam exam = ExamMapper.loadWithId(examId);
            ExamMapper.closeExam(exam);
        } catch (Exception e) {
            logger.error(e.getMessage());
            // TODO: error handling
        }
    }

    /**
     * Publish the exam and make it available to the student.
     *
     * @param subjectId
     * @param examId
     */
    @Override
    public void publishExam(int userId, int examId) {
        try {
            Exam exam = ExamMapper.loadWithId(examId);
            ExamMapper.publishExam(exam);
        }catch (Exception e) {
            logger.error(e.getMessage());
            // TODO: error handling needed.
        }

    }

    @Override
    public List<Subject> viewAllSubjects(int userId) {
        List<Subject> subjects = new ArrayList<Subject>();
        subjects = SubjectMapper.loadInstructorSubjects(userId);
        return subjects;
    }

    @Override
    public List<Exam> viewAllExams(int subjectId, int userId, UserType userType) {
//        List<Exam> exams = ExamMapper.loadInstructorExam(subjectId, userId);
//        return exams;
        return null;
    }

    @Override
    public User getUser(String userName) {
        return null;
    }

    @Override
    public List<Student> viewAllStudents(int subjectId) {
        return null;
    }

    @Override
    public List<Instructor> viewAllInstructors(int subjectId) {
        List<Instructor> instructors = InstructorMapper.loadInstructorsBySubject(subjectId);
        return instructors;
    }

    @Override
    public Exam getExam(int examId) {
        return null;
    }
}
