package servlet;

import db.ExamMapper;
import domain.Exam;
import domain.Question;
import domain.QuestionType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import service.InstructorService;
import service.impl.InstructorServiceImpl;
import util.UnitOfWork;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@WebServlet(urlPatterns = "/update-exam")
public class UpdateExamServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(UpdateExamServlet.class);

    /**
     *
     * @param request request body
     * @param response response body. For more details go and check the API documentation.
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = request.getReader().lines().
                collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);
        int subjectId = jsonObject.getInt("subjectId");
        int examId = jsonObject.getInt("examId");
        JSONArray jsonArray = jsonObject.getJSONArray("questions");

        // TODO: Identity Map 很有用，需要修复。删改不需要数据库操作了，现在还需要load回来
        Exam exam = ExamMapper.loadWithId(examId);
        List<Question> questions = new ArrayList<>();
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String description = object.getString("description");
            String options = object.get("options").toString();
            int mark = object.getInt("mark");
            int questionId = object.getInt("questionId");
            QuestionType questionType = QuestionType.valueOf(object
                    .getString("questionType").toUpperCase());
            Question question = new Question(questionId,description,options,questionType,examId,mark);
            questions.add(question);
        }
        InstructorService instructorService = new InstructorServiceImpl();
        // Commit list of questions, including unitofwork.commit()
        instructorService.updateQuestions(questions);

        // exam becomes dirty, unitofwork.commit()
        exam.setQuestions(questions);
        instructorService.updateExam(exam);

        JSONObject object = new JSONObject();
        object.put("message","success");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().write(object.toString());
    }
}
