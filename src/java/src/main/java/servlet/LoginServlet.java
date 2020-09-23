package servlet;

import domain.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(LoginServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestData = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        System.out.println(username);
        System.out.println(password);

        /**
         * TODO: Authenticate the user's credential in Part 3,
         * TODO: for now just find the user_type based on username,
         * TODO: maybe need to do the check somewhere else
         */
        Boolean authenticated = true;
        UserService userService = new UserServiceImpl();
        User user = userService.getUser(username);
        logger.info("User name : " + user.getName() + "User showName: " + user.getShowName() + "  user Type: " + user.getUserType());
        JSONObject data = new JSONObject();
        if (authenticated) {
            data.put("message", "Login successfully!");
            data.put("username", user.getName());
            data.put("showName", user.getShowName());
            data.put("userType", user.getUserType().toString().toLowerCase());
            data.put("userId", user.getUserId());
            response.setStatus(200);
        } else {
            // TODO: failure reason
            // if password incorrect
//            data.put("message", "Wrong password!");
            // if no such user
//            data.put("message", "User " + user.getName() + " is not registered!");
            data.put("message", "Login fail!");
            response.setStatus(401);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(data.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Get method");
    }
}
