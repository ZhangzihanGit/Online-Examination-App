package servlet;

import auth.AuthorisationCenter;
import domain.Instructor;
import domain.Subject;
import domain.UserType;
import exceptions.NoAuthorisationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import service.AdminService;
import service.InstructorService;
import service.StudentService;
import service.UserService;
import service.impl.AdminServiceImpl;
import service.impl.InstructorServiceImpl;
import service.impl.StudentServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "GetAllInstructorsServlet", urlPatterns = "/all-instructors")
public class GetAllInstructorsServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(GetAllInstructorsServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);
        int userId = jsonObject.getInt("userId");
        String sessionId = jsonObject.getString("sessionId");
        String userType = jsonObject.getString("userType");
        System.out.println(userId);
        System.out.println(userType);

        List<Instructor> instructors;
        JSONObject data = new JSONObject ();

        AuthorisationCenter authorisationCenter = AuthorisationCenter.getInstance();
        try {
            authorisationCenter.checkPermission(sessionId, "admin");

            AdminService userService = new AdminServiceImpl();
            instructors = userService.viewAllInstructors();
            JSONArray instructorArr = new JSONArray(instructors);
            data.put("message", "Fetch instructors successfully");
            data.put("instructorList", instructorArr);
            response.setStatus(200);
        } catch (NoAuthorisationException e) {
            data.put("message", e.getMessage());
            response.setStatus(403);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(data.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
