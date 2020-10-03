package servlet;

import domain.Answer;
import domain.Submission;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import service.InstructorService;
import service.StudentService;
import service.UserService;
import service.impl.InstructorServiceImpl;
import service.impl.StudentServiceImpl;
import util.UnitOfWork;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/submission")
public class AddSubmissionServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(AddSubmissionServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Answer> answers = new ArrayList<>();
        String requestData = request.getReader().lines().
                collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);

        int examId = jsonObject.getInt("examId");
        int userId = jsonObject.getInt("userId");

        JSONArray jsonArray = jsonObject.getJSONArray("questions");
        StudentService service = new StudentServiceImpl();
        service.addSubmission(examId, userId);
        // Create the new submission.
        Submission submission = new Submission(userId, examId);
        UnitOfWork.getInstance().commit();
        int submissionId = submission.getId();

        for ( int i =0; i<jsonArray.length(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            int questionId = o.getInt("questionId");
            String content = o.getString("answer");

            Answer answer = new Answer(questionId,content,submissionId);
            answers.add(answer);
        }
        // Call UnitOfWork to commit the added answers.
        service.addAnswer();
        // After the answers are written into DB, inject the dependency into
        // the submission.
        submission.setAnswers(answers);

        jsonObject = new JSONObject();
        jsonObject.put("message","success");
        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().write(jsonObject.toString());
    }

}
