package servlet;

import auth.AuthorisationCenter;
import db.ExamMapper;
import db.SubjectMapper;
import domain.Exam;
import domain.Subject;
import exceptions.ExamGotSubmissionException;
import exceptions.NoAuthorisationException;
import exceptions.StudentTakingExamException;
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

@WebServlet(urlPatterns = "/delete-exam")
public class DeleteExamServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(DeleteExamServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = request.getReader().lines().
                collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);
        int examId = jsonObject.getInt("examId");
        String sessionId = jsonObject.getString("sessionId");
        int subjectId = jsonObject.getInt("subjectId");
        int userId = jsonObject.getInt("userId");

        InstructorServiceImpl service = new InstructorServiceImpl();

        JSONObject data = new JSONObject();
        AuthorisationCenter authorisationCenter = AuthorisationCenter.getInstance();
        try {
            authorisationCenter.checkPermission(sessionId, "instructor");

            try {
                if (service.checkAnySubmission(examId,subjectId) &&
                        service.checkStudentTakingExam(examId,subjectId)) {
                    // delete exam only if no submission and no student is taking exam
                    service.deleteExam(subjectId,examId);
                    data.put("message", "success");
                    response.setStatus(200);
                }
            } catch (StudentTakingExamException | ExamGotSubmissionException e) {
                data.put("message", e.getMessage());
                response.setStatus(403);
            }
        } catch (NoAuthorisationException e) {
            data.put("message", e.getMessage());
            response.setStatus(403);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(data.toString());
    }
}
