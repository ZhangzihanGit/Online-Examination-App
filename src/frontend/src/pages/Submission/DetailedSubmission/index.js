import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { push } from 'connected-react-router';
import { useParams } from 'react-router-dom';
import { Pagination, Typography, Divider, Button, message } from 'antd';
import AnswerCard from '../AnswerCard';
import styles from './index.module.less';

const { Title } = Typography;

const DetailedSubmission = () => {
  const { code, examId, submissionId } = useParams();
  const dispatch = useDispatch();
  const { submissionList } = useSelector(state => state.subject);
  const [current, setCurrent] = useState(1);
  const [studentSubmission, setStudentSubmission] = useState(null);

  useEffect(() => {
    if (submissionList) {
      const submission = submissionList.submissions.find(s =>
        s.submissionId === Number(submissionId));
      setStudentSubmission(submission);
    }
  }, [submissionId, submissionList]);

  const handlePageChange = (page) => {
    setCurrent(page)
  };

  // Marks have already been temporarily saved in Redux store,
  // so just redirect back to previous page
  const handleSaveMarks = () => {
    message.success('Marks are temporarily saved');
    dispatch(push(`/dashboard/subjects/${code}/mark-exam-${examId}`));
  }

  return (
    <div className={styles.examContainer}>
      <Title level={4}>{`Submission: ${submissionId}`}</Title>
      <Divider type="horizontal" />

      <Pagination
        onChange={handlePageChange}
        pageSize={1}
        current={current}
        defaultCurrent={1}
        total={studentSubmission ? studentSubmission.questions.length : 0}
      />

      {studentSubmission && <AnswerCard currentQuestion={studentSubmission.questions[current - 1]} userId={studentSubmission.userId} />}


      {studentSubmission && (studentSubmission.questions.length === current) ? (
        <Button
          className={styles.submitButton}
          type="primary"
          onClick={handleSaveMarks}
        >
          Save Marks
        </Button>
      ) : null}

    </div>
  )
};

export default DetailedSubmission;