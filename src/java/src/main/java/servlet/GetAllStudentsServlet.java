package servlet;

import domain.Instructor;
import domain.Student;
import domain.UserType;
import org.json.JSONArray;
import org.json.JSONObject;
import service.AdminService;
import service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "GetAllStudentsServlet", urlPatterns = "/all-students")
public class GetAllStudentsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestData = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);
        int userId = jsonObject.getInt("userId");
        String userType = jsonObject.getString("userType");
        System.out.println(userId);
        System.out.println(userType);

        List<Student> students;
        JSONObject data = new JSONObject ();

        // TODO: do more strict authentication in Part 3
        // TODO: e.g. not only check string, but check userId in DB to see the user's type
        if (userType.equalsIgnoreCase(UserType.ADMIN.toString())){
            AdminService userService = new AdminServiceImpl();
            students = userService.viewAllStudents();
            JSONArray studentArr = new JSONArray(students);
            data.put("message", "Fetch students successfully");
            data.put("studentList", studentArr);
            response.setStatus(200);
        } else {
            data.put("message", "No authority to fetch students");
            response.setStatus(403);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(data.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
