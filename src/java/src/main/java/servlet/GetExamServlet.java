package servlet;

import domain.Exam;
import domain.Question;
import org.json.JSONArray;
import org.json.JSONObject;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/get-exam")
public class GetExamServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int examId = Integer.parseInt(request.getParameter("examId"));
        UserService service = new UserServiceImpl();
        Exam exam = service.getExam(examId);
        List<Question> questionLists = exam.getQuestions();
        JSONObject data = new JSONObject();
        JSONArray questions = new JSONArray(questionLists);
        data.put("questions",questions);
        data.put("showName", exam.getShowName());
        data.put("description", exam.getDescription());
        data.put("published", exam.isPublished());
        data.put("message", "success");
        data.put("subjectId", exam.getSubjectId());
        data.put("examId", examId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().write(data.toString());
    }
}
