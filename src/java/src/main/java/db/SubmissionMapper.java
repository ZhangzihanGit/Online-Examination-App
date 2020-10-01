package db;

import domain.Answer;
import domain.Exam;
import domain.Submission;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubmissionMapper {
    private final static Logger logger = LogManager.getLogger(SubmissionMapper.class);

    public static void updateSubmission(Submission submission) {
        String sql = "UPDATE exam.submission SET " +
                " mark = ? WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = DBConnection.prepare(sql);
            statement.setInt(1,submission.getMark());
            statement.setInt(2,submission.getId());
            ResultSet resultSet = statement.executeQuery();
            logger.info("the submission is updated with mark: " +submission.getMark());
        } catch (SQLException e){
            logger.error(e.getMessage());
        }
    }

    public static List<Submission> loadSubmissionsExam(int examId) {
        String sql = "SELECT * FROM exam.submission WHERE " +
                " examid=?";
        PreparedStatement statement = null;
        List<Submission> submissions = null;
        try {
            statement = DBConnection.prepare(sql);
            statement.setInt(1,examId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                submissions.add(load(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return submissions;
    }

    public static boolean examIsSubmitted(Exam exam) {
        String sql = "SELECT * FROM exam.submission WHERE " +
                " examid = ?";
        PreparedStatement statement = null;
        int examId = exam.getId();
        try {
            statement = DBConnection.prepare(sql);
            statement.setInt(1,examId);
            ResultSet resultSet = statement.executeQuery();
            // No data was found in the DB.
            // What's in the DB is not care, we want to know if there is any records.
            if (!resultSet.isBeforeFirst()) {
                return false;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return true;
    }

    public static void addSubmission(Submission submission ) {
        String sql = "INSERT INTO exam.submission (studentid, examid) " +
                " VALUES(?,?) RETURNING id";
        int studentId = submission.getStudentId();
        int examId = submission.getExamId();
//        int mark = submission.getMark();
        int id = 0;

        PreparedStatement statement = null;
        try {
            statement = DBConnection.prepare(sql);
            statement.setInt(1,studentId);
            statement.setInt(2,examId);
//            statement.setInt(3,mark);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                if (id!=0) {
                    submission.setId(id);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Return a submission for one student in one exam. This will be unique, as
     * one student can only have one submission for one exam.
     * @param studentId
     * @param examId
     * @return
     */
    public static Submission loadWithStudentExam(int studentId, int examId) {
        String sql = "SELECT * FROM exam.submission WHERE studentid = ? AND " +
                "examid = ?";
        PreparedStatement statement = null;
        Submission submission = null;
        try {
            statement = DBConnection.prepare(sql);
            statement.setInt(1,studentId);
            statement.setInt(2,examId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                submission = SubmissionMapper.load(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return submission;
    }

    public static Submission loadWithId(int id) {
        String sql = "SELECT * FROM exam.submission WHERE id = ?";
        PreparedStatement statement = null;
        Submission submission = null;
        try {
            statement = DBConnection.prepare(sql);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                submission = SubmissionMapper.load(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return  submission;
    }

    public static Submission load(ResultSet resultSet ) {
        Submission submission = null;
        List<Answer> answers = new ArrayList<>();
        try {
            int id = resultSet.getInt("id");
            int examId = resultSet.getInt("examid");
            int studentId = resultSet.getInt("studentid");
            int mark = resultSet.getInt("mark");
            answers = AnswerMapper.loadAnswers(id);

            submission = new Submission(id,answers,mark,studentId, examId);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return submission;
    }



}
