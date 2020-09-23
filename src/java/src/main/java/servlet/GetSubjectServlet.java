package servlet;

import domain.Exam;
import domain.Instructor;
import domain.Student;
import domain.UserType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import service.InstructorService;
import service.StudentService;
import service.UserService;
import service.impl.InstructorServiceImpl;
import service.impl.StudentServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetSubjectServlet", urlPatterns = "/subject")
public class GetSubjectServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(GetSubjectServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String userType = request.getParameter("userType");
        String subjectId = request.getParameter("subjectId");
        System.out.println(userId);
        System.out.println(userType);
        System.out.println(subjectId);

        // get exam list
        UserService userService = new UserServiceImpl();
        List<Exam> exams = userService.viewAllExams(Integer.parseInt(subjectId),
                Integer.parseInt(userId), UserType.valueOf(userType.toUpperCase()));

        JSONObject data = new JSONObject ();
        JSONArray examArr = new JSONArray(exams);
        data.put("message", "success");
        data.put("examList", examArr);

        List<Student> students = new ArrayList<>();
        List<Instructor> instructors = new ArrayList<>();
        // get student and instructor list
        if (userType.equalsIgnoreCase(UserType.ADMIN.toString())){
            logger.info("is admin");
            InstructorService instructorService = new InstructorServiceImpl();
            StudentService studentService = new StudentServiceImpl();
            students = studentService.viewAllStudents(Integer.parseInt(subjectId));
            instructors = instructorService.viewAllInstructors(Integer.parseInt(subjectId));
            JSONArray instructorArr = new JSONArray(instructors);
            JSONArray studentArr = new JSONArray(students);
            data.put("instructorList", instructorArr);
            data.put("studentList", studentArr);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().write(data.toString());
    }
}
