import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams, useHistory } from 'react-router-dom';
import { Pagination, Typography, Divider, Button } from 'antd';
import { CloudUploadOutlined } from '@ant-design/icons';
import { submitExam } from '../../actions/subject';
import { findExamById } from '../../utils/helpers';

import QuestionCard from './QuestionCard';
import styles from './index.module.less';

const { Title } = Typography;

const generateDefaultSubmission = (questions) => {
  return questions.map(question => ({
    questionId: question.questionID,
    answer: ""
  }));
};

const Exam = () => {
  const { code, examId } = useParams();
  const dispatch = useDispatch();
  const { examList, studentAnswer } = useSelector(state => state.subject);
  const { identity } = useSelector(state => state.user);
  const [current, setCurrent] = useState(1);
  const [exam, setExam] = useState(null);

  useEffect(() => {
    if (examList) {
      const exam = findExamById(examList, Number(examId));
      console.log(exam);
      setExam(exam);
    }
  }, [examList]);

  console.log(exam)

  const handlePageChange = (page, pageSize) => {
    console.log(page);

    setCurrent(page)
  }

  const handleExamSubmit = () => {
    console.log('exam submitted');
    if (studentAnswer) {
      console.log(studentAnswer);
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

      {exam && <QuestionCard exam={exam} current={current} currentQuestion={exam.questions[current - 1]} />}

      {exam && (exam.questions.length === current) ? (
        <Button
          className={styles.submitButton}
          type="primary"
          icon={<CloudUploadOutlined />}
          onClick={handleExamSubmit}
        >
          Submit Exam
        </Button>
      ) : null}

    </div>
  )
};

export default Exam;