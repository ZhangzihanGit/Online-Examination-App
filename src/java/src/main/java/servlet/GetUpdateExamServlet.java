package servlet;

import auth.AuthorisationCenter;
import db.LockManager;
import domain.Exam;
import domain.Question;
import exceptions.AcquireLockException;
import exceptions.NoAuthorisationException;
import org.json.JSONArray;
import org.json.JSONObject;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetUpdateExamServlet", urlPatterns = "/get-update-exam")
public class GetUpdateExamServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int examId = Integer.parseInt(request.getParameter("examId"));
        String sessionId = request.getParameter("sessionId");

        JSONObject data = new JSONObject();
        try {
            AuthorisationCenter authorisationCenter = AuthorisationCenter.getInstance();
            // check if the subject session has authorization to add exam
            authorisationCenter.checkPermission(sessionId, "instructor");

            // acquire lock only if it is
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
        } catch (AcquireLockException | NoAuthorisationException e) {
            data.put("message", e.getMessage());
            response.setStatus(403);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(data.toString());
    }
}
