import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams, useHistory } from 'react-router-dom';
import { Pagination, Typography, Divider, Button } from 'antd';
import { CloudUploadOutlined } from '@ant-design/icons';
import AnswerCard from '../AnswerCard';
// import { submitExam } from '../../actions/subject';
// import { findExamById } from '../../utils/helpers';
import styles from './index.module.less';

const { Title } = Typography;

const DetailedSubmission = () => {

  const { code, examId, submissionId } = useParams();
  const dispatch = useDispatch();
  const { submissionList, marks } = useSelector(state => state.subject);
  const { identity } = useSelector(state => state.user);
  const [current, setCurrent] = useState(1);
  const [studentSubmission, setStudentSubmission] = useState(null);

  useEffect(() => {
    if (submissionList) {
      const submission = submissionList.submissions.find(s =>
        s.submissionId === Number(submissionId));
      console.log(submission);
      setStudentSubmission(submission);
    }
  }, [submissionId]);

  console.log(submissionId);
  console.log(studentSubmission)
  const handlePageChange = (page) => {
    setCurrent(page)
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

      {studentSubmission && <AnswerCard currentQuestion={studentSubmission.questions[current - 1]} />}


      {studentSubmission && (studentSubmission.questions.length === current) ? (
        <Button
          className={styles.submitButton}
          type="primary"
          icon={<CloudUploadOutlined />}
        // onClick={handleExamSubmit}
        >
          Submit Marks
        </Button>
      ) : null}

    </div>
  )
};

export default DetailedSubmission;