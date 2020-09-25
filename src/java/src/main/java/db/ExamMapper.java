package db;

import domain.Exam;
import domain.Question;
import domain.UserType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamMapper {
    private static final Logger logger = LogManager.getLogger(ExamMapper.class);

    /**
     * Delete the exam given the exam object, also delete the related list of questions.
     * @param exam
     */
    public static void deleteExam(Exam exam) {
        String sql = "DELETE FROM exam.exam " +
                " WHERE id = ?";
        int id = exam.getId();
        List<Question> questions = exam.getQuestions();
        for (Question q: questions) {
            QuestionMapper.deleteQuestion(q);
        }
        PreparedStatement statement = null;
        try {
            statement = DBConnection.prepare(sql);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
    /**
     * close the exam. Performed by the instructor.
     * @param exam
     */
    public static void closeExam(Exam exam) {
        String sql = "UPDATE exam.exam SET " +
                " isclosed =? WHERE id=?";
        int examId = exam.getId();
        PreparedStatement statement = null;
        try {
            statement = DBConnection.prepare(sql);
            statement.setBoolean(1,true);
            statement.setInt(2, examId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                logger.info("Exam with id: " + + resultSet.
                        getInt("id") +" is closed");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Publish the exam. Performed by instructor
     * @param exam
     */
    public static void publishExam(Exam exam) {
        String sql = "UPDATE exam.exam SET " +
                " ispublished = ? WHERE id = ? RETURNING id";
        PreparedStatement statement = null;
        int examId = exam.getId();
        try {
            statement = DBConnection.prepare(sql);
            statement.setBoolean(1,true);
            statement.setInt(2,examId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                logger.info("Exam with id: " + + resultSet.
                        getInt("id") +" is published");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
    /**
     * Update the exam by writing data from exam object to DB.
     * TODO: Need a proper error handling
     * @param exam
     */
    public static void updateExam(Exam exam) {
        String sql = "UPDATE exam.exam SET " +
                "show_name=?, subjectId=?, ispublished=?,isstart=?,description=? " +
                "WHERE id=?";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement =  DBConnection.prepare(sql);
            preparedStatement.setString(1,exam.getShowName());
            preparedStatement.setInt(2,exam.getSubjectId());
            preparedStatement.setBoolean(3,exam.isPublished());
            // TODO: 需要修改db schema，这个字段不需要了
            preparedStatement.setBoolean(4,false);
            preparedStatement.setString(5,exam.getDescription());
            preparedStatement.setInt(6,exam.getId());

//            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("The exam is successfully updated, with id " + exam.getId());
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public static List<Exam> loadExamListWithSubject(int subjectId) {
        List<Exam> exams= new ArrayList<>();
        String sql = "SELECT * FROM exam.exam as e INNER JOIN exam.subject as s " +
                " ON e.subjectId = s.id WHERE s.id= ?";
        PreparedStatement statement = null;
        try {
            statement = DBConnection.prepare(sql);
            statement.setInt(1,subjectId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                exams.add(load(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return exams;
    }

    /**
     *
     * @param exam
     */
    public static void addExam(Exam exam) {
        String sql = "INSERT INTO exam.exam (show_name, subjectId, description,isPublished)" +
                "VALUES (?,?,?,?) RETURNING id";
        PreparedStatement statement = null;
        int examId=0;
        try {
            statement = DBConnection.prepare(sql);
            // TODO： 这个字段是否需要不确定，现在写死了是因为db schema要求该字段not null
            statement.setString(1, "exam");
            statement.setInt(2,exam.getSubjectId());
            statement.setString(3,exam.getDescription());
            statement.setBoolean(4,false);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                examId = resultSet.getInt("id");
            }
            // Check if adding successfully
            if (examId !=0 ) {
                exam.setId(examId);
            }
            logger.info("the serial id is: " + examId);
        } catch (SQLException e) {
            logger.error(e.toString());
        }
    }

    /**
     * Load the exam instance given the exam id.
     * @param id
     * @return
     */
    public static Exam loadWithId(Integer id) {
        String sql = "SELECT * FROM exam.exam WHERE id = ?";
        Exam exam = new Exam();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection.prepare(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                exam = ExamMapper.load(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return exam;
    }

    /**
     * Load lists of exams under the subject, given the userId and userType.
     * This will be the full retrieval of list of exams, and maybe later refactored or broke into
     * smaller servlets.
     * TODO: Possible refactoring.
     * @param subjectId
     * @param userId
     * @param userType
     * @return
     */
    public static List<Exam> loadAllExams(int subjectId, int userId, UserType userType)  {
        String sql;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<Exam> exams = new ArrayList<>();

        try {
            switch(userType) {
                case STUDENT:
                    sql = "SELECT * FROM exam.exam as e INNER JOIN exam.subject AS s ON e.subjectId " +
                            "= s.id INNER JOIN exam.student_subject_relation AS n ON s.id = n.subjectId" +
                            " INNER JOIN exam.users as u ON u.id = n.studentId WHERE u.id=? and " +
                            "s.id = ?";
                    preparedStatement = DBConnection.prepare(sql);
                    preparedStatement.setInt(1,userId);
                    preparedStatement.setInt(2,subjectId);
                    resultSet = preparedStatement.executeQuery();
                    break;
                case INSTRUCTOR:
                    sql = "SELECT * FROM exam.exam as e INNER JOIN exam.subject AS s ON e.subjectId " +
                            "= s.id WHERE s.instructorid=? and " +
                            "s.id = ?";
                    preparedStatement = DBConnection.prepare(sql);
                    preparedStatement.setInt(1,userId);
                    preparedStatement.setInt(2,subjectId);
                    resultSet = preparedStatement.executeQuery();
                    break;
                case ADMIN:
                    sql = "SELECT * FROM exam.exam WHERE subjectid = ?";
                    preparedStatement = DBConnection.prepare(sql);
                    preparedStatement.setInt(1,subjectId);
                    resultSet = preparedStatement.executeQuery();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + userType);
            }

            while (resultSet.next()) {
                Exam exam = load(resultSet);
                exams.add(exam);
                logger.info("Hello " + resultSet.getString("description") + resultSet.getString("subjectid"));
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }

        return exams;
    }

    /**
     * Load the exam instance from the DB returning result.
     * @param resultSet
     * @return
     */
    private static Exam load(ResultSet resultSet) {
        Exam exam = null;
        try {
            String showName = resultSet.getString("show_name");
            String description = resultSet.getString("description");
            int subjectId = resultSet.getInt("subjectid");

            int id = resultSet.getInt("id");
            boolean isPublished = resultSet.getBoolean("ispublished");
            List<Question> questions = new ArrayList<>();
            logger.info("Loading questions from Exam: ");
            questions = QuestionMapper.loadQuestionsFromExamId(id);
            for (int i=0; i< questions.size();i ++ ) {
                logger.info(questions.get(i).getQuestionID());
            }
            exam = new Exam(id,subjectId,description,questions,isPublished,showName);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return exam;
    }
}
