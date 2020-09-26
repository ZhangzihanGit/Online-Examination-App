package servlet;

import domain.Subject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import service.AdminService;
import service.impl.AdminServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/add-subject")
public class AddSubjectServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(AddSubjectServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = request.getReader().lines().
                collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);
        // TODO: 这里应该是给一个userid
        int adminId = jsonObject.getInt("userId");
        String showName = jsonObject.getString("showName");
        String description = jsonObject.getString("description");


        // TODO: 创建完subject之后需要返回字段description, show_name, id用于前端显示

        //TODO: add subject 是admin的行为？
        Subject subject = new Subject(description,showName,adminId);
        AdminService service = new AdminServiceImpl();
        service.addSubject(subject);

        jsonObject = new JSONObject();
        jsonObject.put("message","success");
        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        response.getWriter().write(jsonObject.toString());
    }
}
