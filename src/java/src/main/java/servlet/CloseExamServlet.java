package servlet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import service.InstructorService;
import service.impl.InstructorServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/close-exam")
public class CloseExamServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(CloseExamServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = request.getReader().lines().
                collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);
        int userId = jsonObject.getInt("userId");
        int examId = jsonObject.getInt("examId");

        InstructorServiceImpl service = new InstructorServiceImpl();

        // 1. 拿到所有注册这门课的学生的list
        // 2. 对每一个学生，创建一个submission对象
        // 3. 对每一个submission对象中的每一个question，把answer设为""
        // 4. 将submission对象插入到表中
        // 5. 将答案插入answer表中



        service.closeExam(userId,examId);

        JSONObject data = new JSONObject();
        data.put("message", "success");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().write(data.toString());
    }
}
