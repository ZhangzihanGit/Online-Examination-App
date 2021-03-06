import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';
import { Pagination, Typography, Divider, Button } from 'antd';
import { CloudUploadOutlined } from '@ant-design/icons';
import { submitExam } from '../../actions/subject';
import { findExamById } from '../../utils/helpers';
import QuestionCard from './QuestionCard';
import styles from './index.module.less';

const { Title } = Typography;

const generateDefaultSubmission = (questions) => {
  return questions.map(question => ({
    questionId: question.questionId,
    answer: ""
  }));
};

const Exam = () => {
  const { code, examId } = useParams();
  const dispatch = useDispatch();
  const { examList, studentAnswer } = useSelector(state => state.subject);
  const { identity } = useSelector(state => state.user);
  const isStudent = identity && identity.userType === "student";
  const [current, setCurrent] = useState(1);
  const [exam, setExam] = useState(null);

  useEffect(() => {
    if (examList) {
      const exam = findExamById(examList, Number(examId));
      setExam(exam);
    }
  }, [examList, examId]);

  const handlePageChange = (page) => {
    setCurrent(page)
  }

  const handleExamSubmit = () => {
    if (studentAnswer) {
      dispatch(submitExam({
        userId: identity.userId,
        examId,
        questions: studentAnswer,
      }, `/dashboard/subjects/${code}`));
    } else {
      const defaultAnswer = generateDefaultSubmission(exam.questions);
      dispatch(submitExam({
        userId: identity.userId,
        examId,
        questions: defaultAnswer,
      }, `/dashboard/subjects/${code}`))
    }
  };

  return (
    <div className={styles.examContainer}>
      <Title level={4}>{exam ? exam.showName : `Exam Title`}</Title>
      <Divider type="horizontal" />

      <Pagination
        onChange={handlePageChange}
        pageSize={1}
        current={current}
        defaultCurrent={1}
        total={exam ? exam.questions.length : 0}
      />

      {exam && <QuestionCard currentQuestion={exam.questions[current - 1]} />}

      {exam && (exam.questions.length === current) ? (
        <Button
          className={styles.submitButton}
          type="primary"
          icon={<CloudUploadOutlined />}
          onClick={handleExamSubmit}
          disabled={!isStudent}
        >
          Submit Exam
        </Button>
      ) : null}

    </div>
  )
};

export default Exam;