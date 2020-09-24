package servlet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

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
        int adminId = jsonObject.getInt("userId");
    }
}
