package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import db.DBConnection;
import domain.Subject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.InstructorService;
import service.UserService;
import service.impl.InstructorServiceImpl;
import service.impl.StudentServiceImpl;

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

        UserService userService = new InstructorServiceImpl();
        List<Subject> subjects = userService.viewAllSubjects(Integer.parseInt(userId));


        UserService userService1 = new StudentServiceImpl();
        List<Subject> subjects1 = userService1.viewAllSubjects(Integer.parseInt(userId));

        try{
            GsonBuilder builder = new GsonBuilder();
            builder.excludeFieldsWithoutExposeAnnotation();
            Gson gson = builder.create();
//            Subject subject = subjects.get(0);
            String json = gson.toJson(subjects);
//            logger.info(json);
            logger.info(gson.toJson(subjects1));
//            [{"id":7,"subjectCode":"SWEN9007","description":"Software Design and Architecture"},{"id":8,"subjectCode":"SWEN90010","description":"High Integrity Systems Engineering"}]
            // TO add more field:

        } catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        JSONObject resJson = new JSONObject ();
        JSONObject  subjectListJson = new JSONObject ();
        JSONArray subjectArr = new JSONArray(subjects);
        subjectListJson.put("subjectList", subjectArr);
        resJson.put("message", "success");
        resJson.put("data", subjectListJson);
        System.out.println(resJson);

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(resJson.toString());
        }catch (JSONException | IOException err) {
            System.out.println("Error to convert from String to JSON!");
        }
    }
}
