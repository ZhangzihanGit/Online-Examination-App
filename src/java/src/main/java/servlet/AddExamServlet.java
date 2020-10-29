package servlet;

import auth.AuthorisationCenter;
import db.ExamMapper;
import domain.*;
import exceptions.NoAuthorisationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import service.InstructorService;
import service.impl.InstructorServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Add a new exam, that consisting a list of questions. Questions are also imported
 * into the database.
 */
@WebServlet(urlPatterns = "/add-exam")
public class AddExamServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(AddExamServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String requestData = request.getReader().lines().
                collect(Collectors.joining(System.lineSeparator()));

        JSONObject jsonObject = new JSONObject(requestData);
        int subjectId = Integer.parseInt(jsonObject.get("subjectId").toString());
        String sessionId = jsonObject.getString("sessionId");
        String showName = jsonObject.get("showName").toString();
        String examDescription = jsonObject.get("description").toString();
        JSONArray jsonArray= jsonObject.getJSONArray("questions");

        logger.info(showName);

        jsonObject = new JSONObject();
        AuthorisationCenter authorisationCenter = AuthorisationCenter.getInstance();
        try {
            // check if the subject session has authorization to add exam
            authorisationCenter.checkPermission(sessionId, "instructor");

            List<Question> questions = new ArrayList<>();
            Exam exam = new Exam(subjectId,showName,examDescription);
            InstructorService service = new InstructorServiceImpl();
            // Commit the exam to DB, get examid from DB.
            service.addExam(exam);

            int examId = exam.getId();
            for (int i=0; i< jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String description = object.get("description").toString();
                String options = object.get("options").toString();
                int mark = object.getInt("mark");
                QuestionType questionType = QuestionType.valueOf(object.
                        get("questionType").toString().toUpperCase());
                questions.add(new Question(description,options,questionType,examId,mark));
            }
            // Commit the new added questions
            service.addQuestions();
            // After questions are loaded into the exam, initialise it in the exam object.
            // The setter will not initiate
            exam.setQuestions(questions);

            jsonObject.put("message","success");
            jsonObject.put("examId", exam.getId());
            jsonObject.put("showName", exam.getShowName());
            jsonObject.put("closed", exam.isClosed());
            jsonObject.put("description", exam.getDescription());
            jsonObject.put("published", exam.isPublished());
            jsonObject.put("subjectId", exam.getSubjectId());
            response.setStatus(200);
        } catch (NoAuthorisationException e) {
            jsonObject.put("message", e.getMessage());
            response.setStatus(403);
        }

        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }
}
