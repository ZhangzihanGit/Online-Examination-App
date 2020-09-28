package servlet;

import domain.Exam;
import domain.Student;
import domain.User;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/start-exam")
public class StartExamServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        int studentId = Integer.parseInt(request.getParameter("id"));
        int examId = Integer.parseInt(request.getParameter("examId"));

        UserService service = new UserServiceImpl();
        Exam exam = service.getExam(examId);
        User student = service.getUser(studentId);
        //TODO:  在Exam 还是User加一个字段？
    }
}
