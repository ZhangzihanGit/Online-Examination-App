package servlet;

import db.LockManager;
import domain.Exam;
import domain.Question;
import exceptions.AcquireLockException;
import org.json.JSONArray;
import org.json.JSONObject;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/get-exam")
public class GetExamServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int examId = Integer.parseInt(request.getParameter("examId"));
        String sessionId = request.getParameter("sessionId");

        JSONObject data = new JSONObject();
        try {
            // acquire lock
            LockManager.getInstance().acquireLock(examId, sessionId);

            UserService service = new UserServiceImpl();
            Exam exam = service.getExam(examId);
            List<Question> questionLists = exam.getQuestions();
            JSONArray questions = new JSONArray(questionLists);
            JSONObject examObj = new JSONObject();
            examObj.put("questions",questions);
            examObj.put("showName", exam.getShowName());
            examObj.put("description", exam.getDescription());
            examObj.put("published", exam.isPublished());
            examObj.put("closed", exam.isClosed());
            examObj.put("subjectId", exam.getSubjectId());
            examObj.put("examId", examId);

            data.put("message", "success");
            data.put("exam", examObj);
            response.setStatus(200);
        } catch (AcquireLockException e) {
            data.put("message", e.getMessage());
            response.setStatus(403);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(data.toString());
    }
}
