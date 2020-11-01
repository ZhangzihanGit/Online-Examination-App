package servlet;

import auth.AuthorisationCenter;
import db.*;
import domain.*;
import exceptions.NoAuthorisationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import service.InstructorService;
import service.impl.InstructorServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/all-submissions")
public class GetAllSubmissionServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(GetAllSubmissionServlet.class);
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int examId = Integer.parseInt(request.getParameter("examId"));
        String sessionId = request.getParameter("sessionId");
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        int totalMark = 0;

        JSONObject object = new JSONObject();
        AuthorisationCenter authorisationCenter = AuthorisationCenter.getInstance();
        try {
            authorisationCenter.checkPermission(sessionId, "instructor");

            Exam exam = ExamMapper.loadWithId(examId);
            Subject subject = SubjectMapper.loadSubject(subjectId);
            InstructorService service = new InstructorServiceImpl();

            List<Question> questions = exam.getQuestions();
            List<Student> students = StudentMapper.loadStudentsBySubject(subjectId);
            List<Submission> submissions = service.getAllSubmission(examId);

            JSONArray tempArraySubmissions = new JSONArray();
            for (Submission submission: submissions) {
                // should create a new question list for each submission
                JSONArray tempArrayQuestions = new JSONArray();
                for (Student student: students) {
                    JSONObject tempSubmission = new JSONObject();
                    if (student.getUserId() == submission.getStudentId()) {
                        tempSubmission.put("userId", student.getUserId());
                        tempSubmission.put("submissionId", submission.getId());
                        tempSubmission.put("tableViewMark", submission.getMark());
                        logger.info("User id submssion id +" + student.getUserId()+ " " + submission.getId());
                        System.out.println("submission id is: " + submission.getStudentId());
                        // Find the submission under the name of the student, get back to the questions.
                        List<Answer> answers = AnswerMapper.loadAnswers(submission.getId());

                        for (Answer a : answers) {
                            System.out.println("Answer: " + a.getContent());
                        }

                        JSONObject tempQuestion = null;
                        for (Question question: questions) {
                            for (Answer answer: answers) {
                                System.out.println("========: " + answer.getContent());
                                tempQuestion = new JSONObject();
                                if (question.getQuestionId() == answer.getQuestionId()) {
                                    String questionDescription = question.getDescription();

                                    int assignedMark = 0;
                                    try {
                                        assignedMark =answer.getMark();
                                    } catch (NullPointerException e){}

                                    int mark = question.getMark();
                                    totalMark +=mark;
                                    logger.info("-==TOTAL MARK: " + totalMark);
                                    String content = answer.getContent();
                                    int questionId = question.getQuestionId();
                                    tempQuestion.put("questionId", questionId);
                                    tempQuestion.put("description", questionDescription);
                                    tempQuestion.put("answer", content);
                                    // mark means the question's full mark
                                    tempQuestion.put("mark", mark);
                                    // assignedMark means the mark that the instructor assigned to the student
                                    tempQuestion.put("assignedMark", assignedMark);

                                    tempArrayQuestions.put(tempQuestion);
                                }
                            }
                        }

                        totalMark = 0;
                        tempSubmission.put("questions", tempArrayQuestions);

                        tempArraySubmissions.put(tempSubmission);
                    }
                }
            }
            logger.info("TOTAL MARK: " + totalMark);
            logger.info("TOTAL MARK SIZE: " + submissions.size());
            // calculate the totalMark for each exam
//            if (submissions.size() > 0) {
//                totalMark = totalMark / submissions.size();
//            }
            logger.info("TOTAL MARK2: " + totalMark);

            object.put("examId", examId);
            object.put("totalMark", totalMark);
            object.put("submissions", tempArraySubmissions);
            response.setStatus(200);
        } catch (NoAuthorisationException e) {
            object.put("message", e.getMessage());
            response.setStatus(403);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(object.toString());
    }
}
