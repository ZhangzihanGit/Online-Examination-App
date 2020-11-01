package servlet;

import auth.AuthorisationCenter;
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
        String sessionId = request.getParameter("sessionId");
        String subjectId = request.getParameter("subjectId");
        System.out.println(userId);
        System.out.println(userType);
        System.out.println(subjectId);

        // get exam list
        UserService userService = new UserServiceImpl();
        List<Exam> exams = userService.viewAllExams(Integer.parseInt(subjectId),
                Integer.parseInt(userId), UserType.valueOf(userType.toUpperCase()));

        JSONObject data = new JSONObject ();
        JSONArray examArr = new JSONArray();

        for (Exam e : exams) {
            JSONObject d = new JSONObject ();
            d.put("showName", e.getShowName());
            d.put("description", e.getDescription());
            d.put("published", e.isPublished());
            d.put("subjectId", e.getSubjectId());
            d.put("closed", e.isClosed());
            d.put("examId", e.getId());
            examArr.put(d);
        }
        data.put("message", "success");
        data.put("examList", examArr);

        // get student and instructor list
        AuthorisationCenter authorisationCenter = AuthorisationCenter.getInstance();
        if (authorisationCenter.hasPermission(sessionId, "admin")) {
            logger.info("is admin");
            InstructorService instructorService = new InstructorServiceImpl();
            StudentService studentService = new StudentServiceImpl();
            List<Student> students = studentService.viewAllStudents(Integer.parseInt(subjectId));
            List<Instructor> instructors = instructorService.viewAllInstructors(Integer.parseInt(subjectId));
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
