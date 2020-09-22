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

@WebServlet(name = "LoginServlet", urlPatterns = {"/getLogin"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Hello from Get method");
        String jsonStr = "{\"userName\": \"zzh\"}";
        String mock = DBConnection.mockDataTest();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonObject.toString() + mock);
        }catch (JSONException err) {
            System.out.println("Error to convert from String to JSON!");
        }
    }
}
