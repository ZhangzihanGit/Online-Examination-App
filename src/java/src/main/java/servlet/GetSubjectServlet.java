package servlet;

import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetSubjectServlet", urlPatterns = "/subject")
public class GetSubjectServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String userType = request.getParameter("userType");
        String subjectId = request.getParameter("subjectId");
        System.out.println(userId);
        System.out.println(userType);
        System.out.println(subjectId);

        UserService userService = new UserServiceImpl();
        userService.viewAllExams(Integer.parseInt(subjectId), Integer.parseInt(userId));
    }
}
