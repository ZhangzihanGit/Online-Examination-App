package servlet;

import db.ExamMapper;
import domain.Exam;
import domain.Question;
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

        InstructorService instructorService = new InstructorServiceImpl();

        // TODO: Identity Map 很有用，需要修复。删改不需要数据库操作了，现在还需要load回来
        // Get the exam that is being updated.
        Exam exam = ExamMapper.loadWithId(examId);
        List<Question> originalQuestions = exam.getQuestions();

        // Detect if the questions are new-added/ modified/ deleted.
        // Assume that front end will give all questions of the exam.
        instructorService.updatedQuestions(originalQuestions,jsonArray, exam);

        // TODO: 还是把更新后的exam字段发回来吧

        JSONObject object = new JSONObject();
        object.put("message","success");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().write(object.toString());
    }
}
