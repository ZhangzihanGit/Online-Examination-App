package servlet;

import auth.AuthorisationCenter;
import db.ExamMapper;
import db.LockManager;
import domain.Exam;
import domain.Question;
import exceptions.ExamGotSubmissionException;
import exceptions.NoAuthorisationException;
import exceptions.StudentTakingExamException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import service.InstructorService;
import service.impl.InstructorServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
@WebServlet(urlPatterns = "/update-exam")
public class UpdateExamServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(UpdateExamServlet.class);

    /**
     *
     * @param request request body
     * @param response response body. For more details go and check the API documentation.
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = request.getReader().lines().
                collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);
        int subjectId = jsonObject.getInt("subjectId");
        String sessionId = jsonObject.getString("sessionId");
        int examId = jsonObject.getInt("examId");
        String description = jsonObject.getString("description");
        String showName = jsonObject.getString("showName");
        JSONArray jsonArray = jsonObject.getJSONArray("questions");

        JSONObject object = new JSONObject();
        AuthorisationCenter authorisationCenter = AuthorisationCenter.getInstance();
        try {
            authorisationCenter.checkPermission(sessionId, "instructor");

            InstructorService instructorService = new InstructorServiceImpl();

            // Get the exam that is being updated.
            Exam exam = ExamMapper.loadWithId(examId);
            List<Question> originalQuestions = exam.getQuestions();

            try {
                // If the permission is granted(no submission or student is taking the exam)
                if (instructorService.checkAnySubmission(examId,subjectId) &&
                        instructorService.checkStudentTakingExam(examId,subjectId)) {
                    // Detect if the questions are new-added/ modified/ deleted.
                    // Assume that front end will give all questions of the exam.
                    instructorService.updatedQuestions(originalQuestions,jsonArray, exam);
                    instructorService.updateExam(exam, description, showName);

                    JSONObject examObj = new JSONObject();
                    examObj.put("examId", examId);
                    examObj.put("description", exam.getDescription());
                    examObj.put("showName", exam.getShowName());
                    examObj.put("published", exam.isPublished());
                    examObj.put("closed", exam.isClosed());
                    examObj.put("subjectId", exam.getSubjectId());
                    JSONArray questionArr = new JSONArray(exam.getQuestions());
                    examObj.put("questions", questionArr);

                    object.put("message","success");
                    object.put("exam", examObj);
                    response.setStatus(200);

                    // release lock
                    LockManager.getInstance().releaseLock(examId);
                }
            } catch (ExamGotSubmissionException | StudentTakingExamException e) {
                object.put("message",e.getMessage());
                response.setStatus(403);
            }
        } catch (NoAuthorisationException e) {
            object.put("message",e.getMessage());
            response.setStatus(403);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(object.toString());
    }
}
