package servlet;

import domain.Answer;
import domain.Submission;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import service.InstructorService;
import service.impl.InstructorServiceImpl;
import util.UnitOfWork;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/mark-submit")
public class MarkSubmissionServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MarkSubmissionServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));
        JSONObject obj = new JSONObject(requestData);

        InstructorService service = new InstructorServiceImpl();

        int examId = obj.getInt("examId");
        JSONArray array = obj.getJSONArray("marks");
        for (int i=0; i<array.length(); i++) {
            JSONObject markObject = array.getJSONObject(i);
            int submissionId = markObject.getInt("submissionId");
            int userId = markObject.getInt("userId");
            int totalMark =markObject.getInt("totalMark");
            // Mark distribution for each question.
            JSONArray questionsData = markObject.getJSONArray("questions");
            Submission submission = service.getSubmission(submissionId);
            submission.setMark(totalMark);
            for (int j=0; j<questionsData.length(); j++) {
                JSONObject questionData = questionsData.getJSONObject(j);
                int questionId = questionData.getInt("questionId");
                int mark = questionData.getInt("mark");
                Answer answer = service.getAnswer(submissionId, questionId);
                logger.info("Answer id is: "+ answer.getId());
                answer.setMark(mark);
            }
            UnitOfWork.getInstance().commit();

            obj = new JSONObject();
            obj.put("message","success");
            response.setContentType("application/json");
            request.setCharacterEncoding("UTF-8");
            response.setStatus(200);
            response.getWriter().write(obj.toString());

        }
    }
}
