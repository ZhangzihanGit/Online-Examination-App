package servlet;

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

@WebServlet(urlPatterns = "/add-exam")
public class AddExamServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(AddExamServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String requestData = request.getReader().lines().
                collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);
        int subjectId = Integer.parseInt(jsonObject.get("subjectId").toString());
        JSONArray jsonArray= jsonObject.getJSONArray("questions");
        // TODO: 解析URL交给Servlet, 实例创建也是servlet？ 还是说在InstructorServiceImpl里面创建？
        List<Question> questions = new ArrayList<>();
        for (int i=0; i< jsonArray.length(); i++) {
            JSONObject objet = jsonArray.getJSONObject(i);
            String description = objet.get("description").toString();
            String options = objet.get("options").toString();
            QuestionType questionType = QuestionType.valueOf(objet.get("questionType").toString().toUpperCase());
            questions.add(new Question(description,options,questionType));
        }
        Exam exam = new Exam(subjectId,questions, "Not too sure if needed");
        InstructorService service = new InstructorServiceImpl();
        service.addExam(exam);
    }
}
