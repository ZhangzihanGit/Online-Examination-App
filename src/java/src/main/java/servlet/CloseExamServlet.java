package servlet;

import auth.AuthorisationCenter;
import exceptions.NoAuthorisationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import service.InstructorService;
import service.impl.InstructorServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/close-exam")
public class CloseExamServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(CloseExamServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = request.getReader().lines().
                collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);
        int userId = jsonObject.getInt("userId");
        String sessionId = jsonObject.getString("sessionId");
        int examId = jsonObject.getInt("examId");
        int subjectId = jsonObject.getInt("subjectId");

        logger.info("User: " + userId + " Exam: " + examId + " Subject: " + subjectId );

        JSONObject data = new JSONObject();
        AuthorisationCenter authorisationCenter = AuthorisationCenter.getInstance();
        try {
            authorisationCenter.checkPermission(sessionId, "instructor");

            InstructorServiceImpl service = new InstructorServiceImpl();
            service.closeExam(userId, examId, subjectId);
            data.put("message", "success");
            response.setStatus(200);
        } catch (NoAuthorisationException e) {
            data.put("message", e.getMessage());
            response.setStatus(403);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(data.toString());
    }
}
