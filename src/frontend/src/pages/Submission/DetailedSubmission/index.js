import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams, useHistory } from 'react-router-dom';
import { Pagination, Typography, Divider, Button } from 'antd';
import AnswerCard from '../AnswerCard';
// import { submitExam } from '../../actions/subject';
// import { findExamById } from '../../utils/helpers';
import styles from './index.module.less';

const { Title } = Typography;

const DetailedSubmission = () => {

  const { code, examId, submissionId } = useParams();
  const dispatch = useDispatch();
  const { examList, studentAnswer } = useSelector(state => state.subject);
  const { identity } = useSelector(state => state.user);
  const [current, setCurrent] = useState(1);
  const [exam, setExam] = useState(null);

  console.log(submissionId);
  const handlePageChange = (page) => {
    setCurrent(page)
  }

  return (
    <div className={styles.examContainer}>
      <Title level={4}>{`Exam Title`}</Title>
      <Divider type="horizontal" />

      <Pagination
        onChange={handlePageChange}
        pageSize={1}
        current={current}
        defaultCurrent={1}
        total={10}
      />

      <AnswerCard />

      {/* {exam && <QuestionCard exam={exam} current={current} currentQuestion={exam.questions[current - 1]} />}

      {exam && (exam.questions.length === current) ? (
        <Button
          className={styles.submitButton}
          type="primary"
          icon={<CloudUploadOutlined />}
          onClick={handleExamSubmit}
        >
          Submit Exam
        </Button>
      ) : null} */}

    </div>
  )
};

export default DetailedSubmission;