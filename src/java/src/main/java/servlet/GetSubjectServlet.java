package servlet;

import domain.Exam;
import domain.UserType;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String userType = request.getParameter("userType");
        String subjectId = request.getParameter("subjectId");
        System.out.println(userId);
        System.out.println(userType);
        System.out.println(subjectId);

//        List<Exam> exams = new ArrayList<>();
//        if (userType.equalsIgnoreCase(UserType.STUDENT.toString())) {
//            StudentService studentService = new StudentServiceImpl();
//            subjects = studentService.viewAllSubjects(Integer.parseInt(userId));
//        } else if (userType.equalsIgnoreCase(UserType.INSTRUCTOR.toString())) {
//            InstructorService instructorService = new InstructorServiceImpl();
//            subjects = instructorService.viewAllSubjects(Integer.parseInt(userId));
//        } else if (userType.equalsIgnoreCase(UserType.ADMIN.toString())){
//            UserService userService = new UserServiceImpl();
//            subjects = userService.viewAllSubjects(Integer.parseInt(userId));
//        }

        UserService userService = new UserServiceImpl();
        List<Exam> exams = userService.viewAllExams(Integer.parseInt(subjectId), Integer.parseInt(userId), UserType.valueOf(userType.toUpperCase()));


        JSONObject data = new JSONObject ();
        JSONArray examArr = new JSONArray(exams);
//        for (Exam exam : exams) {
//            examArr.put("id", exam.g)
//            System.out.println(exam.getId() + " " + exam.getSubjectId() + " " + exam.getDescription());
//        }

        data.put("message", "success");
        data.put("examList", examArr);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().write(data.toString());
    }
}
