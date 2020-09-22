package servlet;

import db.DBConnection;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Hello from Post method");
        String jsonStr = "{\"userName\": \"zzh\"}";

        String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(test);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        System.out.println(username);
        System.out.println(password);

        /**
         * TODO: Authenticate the user's credential in Part 3,
         * for now just find the user_type based on username
         */



//        try {
//            JSONObject jsonObject = new JSONObject(jsonStr);
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//            response.getWriter().write(jsonObject.toString());
//        }catch (JSONException err) {
//            System.out.println("Error to convert from String to JSON!");
//        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Hello from Get method");
    }
}
