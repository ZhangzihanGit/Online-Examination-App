package servlet;

import domain.Subject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.InstructorService;
import service.impl.InstructorServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AllSubjectsServlet", urlPatterns = "/all-subjects")
public class GetAllSubjectsServlet extends HttpServlet {
    private Logger logger = LogManager.getLogger(GetAllSubjectsServlet.class);
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        InstructorService instructorService = new InstructorServiceImpl();
        List<Subject> subjects = new ArrayList<Subject>();
        subjects = instructorService.viewAllSubjects();
    }
}
