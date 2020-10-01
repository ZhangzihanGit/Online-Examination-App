package servlet;

import db.*;
import domain.*;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int examId = Integer.parseInt(request.getParameter("examId"));
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        int totalMark = 0;

        Exam exam = ExamMapper.loadWithId(examId);
        Subject subject = SubjectMapper.loadSubject(subjectId);
        InstructorService service = new InstructorServiceImpl();

        List<Question> questions = exam.getQuestions();
        List<Student> students = StudentMapper.loadStudentsBySubject(subjectId);
        List<Submission> submissions = service.getAllSubmission(examId);

        JSONObject object = new JSONObject();
        JSONArray tempArrayQuestions = new JSONArray();
        JSONArray tempArraySubmissions = new JSONArray();
        for (Submission submission: submissions) {
            for (Student student: students) {
                JSONObject tempSubmission = new JSONObject();
                if (student.getUserId() == submission.getStudentId()) {
                    tempSubmission.put("userId", student.getUserId());
                    tempSubmission.put("submissionId", submission.getId());
                    // Find the submission under the name of the student, get back to the questions.
                    List<Answer> answers = AnswerMapper.loadAnswers(submission.getId());
                    JSONObject tempQuestion = null;
                    for (Question question: questions) {
                        for (Answer answer: answers) {
                            tempQuestion = new JSONObject();
                            if (question.getQuestionId() == answer.getQuestionId()) {
                                String questionDescription = question.getDescription();
                                int mark =answer.getMark();
                                totalMark +=mark;
                                String content = answer.getContent();
                                int questionId = question.getQuestionId();
                                tempQuestion.put("questionId", questionId);
                                tempQuestion.put("description", questionDescription);
                                tempQuestion.put("answer", content);
                                tempQuestion.put("mark", mark);
                            }
                        }
                        tempArrayQuestions.put(tempQuestion);
                    }
                    tempSubmission.put("questions", tempArrayQuestions);
                }
                tempArraySubmissions.put(tempSubmission);
            }
        }
        object.put("examId", examId);
        object.put("totalMark", totalMark);
        object.put("submissions", tempArraySubmissions);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().write(object.toString());
    }
}
