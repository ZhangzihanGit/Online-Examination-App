package servlet;

import auth.AuthorisationCenter;
import domain.Instructor;
import domain.Student;
import domain.Subject;
import domain.UserType;
import exceptions.NoAuthorisationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import service.AdminService;
import service.UserService;
import service.impl.AdminServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/add-subject")
public class AddSubjectServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(AddSubjectServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = request.getReader().lines().
                collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);
        // TODO: enable multiple students and instructors enroll in this newly added subject
        int adminId = jsonObject.getInt("userId");
        String sessionId = jsonObject.getString("sessionId");
        String showName = jsonObject.getString("showName");
        String description = jsonObject.getString("description");

        JSONObject responseObject = new JSONObject();
        AuthorisationCenter authorisationCenter = AuthorisationCenter.getInstance();
        try {
            authorisationCenter.checkPermission(sessionId, "admin");

            UserService userService = new UserServiceImpl();
            AdminService service = new AdminServiceImpl();
            logger.info("Start");
            List<Instructor> instructors = new ArrayList<>();
            List<Student> students = new ArrayList<>();

            for (int i=0; i< jsonObject.getJSONArray("instructors").length(); i++) {
                int instructorId = jsonObject.getJSONArray("instructors").getInt(i);
                Instructor instructor = (Instructor)userService.getUser(instructorId);
                logger.info("Instructor id: " + instructor.getUserId());
                instructors.add(instructor);
            }
            for (int i=0; i< jsonObject.getJSONArray("students").length(); i++) {
                int studentId= jsonObject.getJSONArray("students").getInt(i);
                Student student = (Student) userService.getUser(studentId);
                logger.info("Student id is: " + student.getUserId());
                students.add(student);
            }

            //Subject will be added to new Object list and commit by calling addSubject()
            Subject subject = new Subject(description,showName,adminId,students,instructors);
            service.addSubject();

            JSONObject data = new JSONObject();
            data.put("id", subject.getId());
            data.put("description", subject.getDescription());
            data.put("subjectCode", subject.getSubjectCode());
            responseObject.put("message","success");
            responseObject.put("subject", data);
            response.setStatus(200);
        } catch (NoAuthorisationException e) {
            responseObject.put("message", e.getMessage());
            response.setStatus(403);
        }

        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseObject.toString());
    }
}
