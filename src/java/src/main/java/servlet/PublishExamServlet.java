package servlet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(urlPatterns = "/publish-exam")
public class PublishExamServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(PublishExamServlet.class);

    
}
