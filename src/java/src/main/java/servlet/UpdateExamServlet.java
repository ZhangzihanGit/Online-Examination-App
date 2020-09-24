package servlet;

import db.ExamMapper;
import domain.Exam;
import domain.Question;
import domain.QuestionType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import service.InstructorService;
import service.impl.InstructorServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateExamServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(UpdateExamServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestData = request.getReader().lines().
                collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestData);
        int subjectId = jsonObject.getInt("subjectId");
        int examId = jsonObject.getInt("examId");
        JSONArray jsonArray = jsonObject.getJSONArray("questions");

        // TODO: Identity Map 很有用，需要修复。删改不需要数据库操作了，现在还需要load回来
        Exam exam = ExamMapper.loadWithId(examId);
        List<Question> questions = new ArrayList<>();
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String description = object.getString("description");
            String options = object.getString("options");
            int mark = object.getInt("mark");
            QuestionType questionType = QuestionType.valueOf(object
                    .getString("questionType").toUpperCase());
            questions.add(new Question(description,options,questionType,examId,mark));
        }
        InstructorService instructorService = new InstructorServiceImpl();

        // Commit list of questions, including unitofwork.commit()
        instructorService.addQuestions();

        // exam becomes dirty, unitofwork.commit()
        exam.setQuestions(questions);
    }
}
