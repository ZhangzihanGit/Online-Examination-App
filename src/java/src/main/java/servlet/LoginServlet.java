package servlet;

import auth.AuthenticationCenter;
import domain.User;
import exceptions.AlreadyLoggedInException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
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

        JSONObject data = new JSONObject();

//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//
//        DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
//        DefaultSessionManager sessionManager = (DefaultSessionManager) securityManager.getSessionManager();
//        Collection<Session> currentSessions = sessionManager.getSessionDAO().getActiveSessions();

//        EnterpriseCacheSessionDAO dao = new EnterpriseCacheSessionDAO();
//        Collection<Session> currentSessions = dao.getActiveSessions();

        // 这个o其实就是username
//        Object o = token.getPrincipal();
//        if (o != null) {
//            logger.info("old principal: " + o.toString());
//        }
//
//        // 检查该username是否已经存在于某个session中，若存在，说明已经登录过了
//        for (Session s : currentSessions) {
//            Object newO = s.getAttribute("principal");
//            if (o != null && newO.equals(o)) {
//                logger.info("You have already logged in!");
//                data.put("message", "You have already logged in somewhere else, please logout first!");
//                response.setStatus(401);
//            } else {
//                logger.info("First time log in!");
//            }
//        }

        AuthenticationCenter authentication = new AuthenticationCenter();

        try {
            Serializable sessionId = authentication.login(username, password);
            UserService userService = new UserServiceImpl();
            User user = userService.getUser(username);
            logger.info("User name : " + user.getName() + "User showName: " + user.getShowName() + "  user Type: " + user.getUserType());
            data.put("message", "Login successfully!");
            data.put("username", user.getName());
            data.put("showName", user.getShowName());
            data.put("userType", user.getUserType().toString().toLowerCase());
            data.put("userId", user.getUserId());
            data.put("sessionId", sessionId);
            response.setStatus(200);
        } catch (UnknownAccountException e) {
            data.put("message", e.getMessage());
            response.setStatus(401);
        } catch (IncorrectCredentialsException e) {
            data.put("message", e.getMessage());
            response.setStatus(401);
        } catch (AuthenticationException e) {
            data.put("message", e.getMessage());
            response.setStatus(401);
        } catch (AlreadyLoggedInException e) {
            data.put("message", e.getMessage());
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
