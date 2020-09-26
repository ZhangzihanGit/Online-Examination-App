package servlet;

import db.ExamMapper;
import db.SubjectMapper;
import domain.Exam;
import domain.Subject;
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

@WebServlet(urlPatterns = "/delete-exam")
public class DeleteExamServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(DeleteExamServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = request.getReader().lines().
                collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);
        int examId = jsonObject.getInt("examId");
        int subjectId = jsonObject.getInt("subjectId");
        int userId = jsonObject.getInt("userId");

        InstructorService service = new InstructorServiceImpl();
        service.deleteExam(subjectId,examId);

        JSONObject data = new JSONObject();
        data.put("message", "success");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().write(data.toString());
    }
}