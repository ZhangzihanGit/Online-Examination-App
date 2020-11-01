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

@WebServlet(urlPatterns = "/publish-exam")
public class PublishExamServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(PublishExamServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = request.getReader().lines().
                collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);
        String sessionId = jsonObject.getString("sessionId");
        int userId = jsonObject.getInt("userId");
        int examId = jsonObject.getInt("examId");

        JSONObject data = new JSONObject();
        AuthorisationCenter authorisationCenter = AuthorisationCenter.getInstance();
        try {
            authorisationCenter.checkPermission(sessionId, "instructor");

            InstructorService service = new InstructorServiceImpl();
            service.publishExam(userId,examId);

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
