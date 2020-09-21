package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import db.DBConnection;
import domain.Subject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.InstructorService;
import service.impl.InstructorServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AllSubjectsServlet", urlPatterns = "/all-subjects")
public class GetAllSubjectsServlet extends HttpServlet {
    private Logger logger = LogManager.getLogger(GetAllSubjectsServlet.class);


    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        String userId = request.getParameter("userid");
        System.out.println(userId);

        InstructorService instructorService = new InstructorServiceImpl();
        List<Subject> subjects = instructorService.viewAllSubjects(Integer.parseInt(userId));

        for (int i = 0; i < subjects.size(); i++) {
            System.out.println(subjects.get(i).getSubjectCode());
            System.out.println(subjects.get(i).getDescription());
            System.out.println((subjects.get(i).getId()));
        }

        JSONObject resJson = new JSONObject ();
        JSONObject  subjectListJson = new JSONObject ();
        JSONArray subjectArr = new JSONArray(subjects);
        subjectListJson.put("subjectList", subjectArr);
        resJson.put("message", "success");
        resJson.put("data", subjectListJson);
        System.out.println(resJson);

//        for (Subject subject : subjects) {
//            String subjectJSONstr = new Gson().toJson(subject);
//            System.out.println(subjectJSONstr);
//        }

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(resJson.toString());
        }catch (JSONException | IOException err) {
            System.out.println("Error to convert from String to JSON!");
        }
    }
}
