package servlet;

import db.ExamMapper;
import domain.*;
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
@WebServlet("/add-exam")
public class AddExamServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(AddExamServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String requestData = request.getReader().lines().
                collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);
        int subjectId = Integer.parseInt(jsonObject.get("subjectId").toString());
        JSONArray jsonArray= jsonObject.getJSONArray("questions");


        List<Question> questions = new ArrayList<>();
        Exam exam = new Exam(subjectId,"Not too sure if needed");
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
        service.addQuestions();
        // After questions are loaded into the exam, initialise it in the exam object.
        exam.setQuestions(questions);

        jsonObject = new JSONObject();
        jsonObject.put("message","success");
        jsonObject.put("examId", exam.getId().toString());
        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().write(jsonObject.toString());
    }
}
