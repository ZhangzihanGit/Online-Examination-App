package servlet;

import domain.Exam;
import domain.Student;
import domain.User;
import exceptions.StudentTakingExamException;
import org.json.JSONObject;
import service.InstructorService;
import service.StudentService;
import service.UserService;
import service.impl.InstructorServiceImpl;
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
        int studentId = Integer.parseInt(request.getParameter("userId"));
        int examId = Integer.parseInt(request.getParameter("examId"));

        StudentServiceImpl service = new StudentServiceImpl();
        Exam exam = service.getExam(examId);
        User student = service.getUser(studentId);
        System.out.println("Student ID: " + student.getName() + "=====: " + exam.getShowName());

        InstructorServiceImpl instructorService = new InstructorServiceImpl();

        try {
            // the student has not attempted the exam before, so allow to start exam
            if (instructorService.checkStudentHasTakenExam(examId, studentId)) {
                boolean isExamStart = service.startExam((Student)student, exam);
                JSONObject jsonObject = new JSONObject();
                if (isExamStart) jsonObject.put("message", "success");
                else jsonObject.put("message", "failed");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                response.getWriter().write(jsonObject.toString());
            }
        } catch (StudentTakingExamException e) {
            JSONObject object = new JSONObject();
            object.put("message", e.getMessage());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(403);
            response.getWriter().write(object.toString());
        }
    }
}
