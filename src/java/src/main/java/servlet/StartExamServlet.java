package servlet;

import domain.Exam;
import domain.Student;
import domain.User;
import org.json.JSONObject;
import service.StudentService;
import service.UserService;
import service.impl.StudentServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/start-exam")
public class StartExamServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int studentId = Integer.parseInt(request.getParameter("id"));
        int examId = Integer.parseInt(request.getParameter("examId"));

        StudentService service = new StudentServiceImpl();
        Exam exam = service.getExam(examId);
        User student = service.getUser(studentId);

        boolean isExamStart = service.startExam((Student)student);
        JSONObject jsonObject = new JSONObject();
        if (isExamStart )jsonObject.put("message", "success");
        else jsonObject.put("message", "failed");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().write(jsonObject.toString());
    }
}
