package servlet;

import domain.*;
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

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AllSubjectsServlet", urlPatterns = "/all-subjects")
public class GetAllSubjectsServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(GetAllSubjectsServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String userId = request.getParameter("userId");
        String userType = request.getParameter("userType");
        System.out.println(userId);
        System.out.println(userType);

        List<Subject> subjects = null;
        if (userType.equalsIgnoreCase(UserType.STUDENT.toString())) {
            StudentService studentService = new StudentServiceImpl();
            subjects = studentService.viewAllSubjects(Integer.parseInt(userId));
        } else if (userType.equalsIgnoreCase(UserType.INSTRUCTOR.toString())) {
            InstructorService instructorService = new InstructorServiceImpl();
            subjects = instructorService.viewAllSubjects(Integer.parseInt(userId));
        } else if (userType.equalsIgnoreCase(UserType.ADMIN.toString())){
            UserService userService = new UserServiceImpl();
            subjects = userService.viewAllSubjects(Integer.parseInt(userId));
        }

        JSONObject data = new JSONObject ();
        JSONArray subjectArr = new JSONArray(subjects);
        data.put("message", "success");
        data.put("subjectList", subjectArr);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().write(data.toString());
    }
}
