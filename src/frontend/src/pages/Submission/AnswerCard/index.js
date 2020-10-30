import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';
import { Card, message, Input } from 'antd';
import { HighlightOutlined } from '@ant-design/icons';
import { SAVE_TOTAL_MARK, SAVE_INDIVIDUAL_MARK } from '../../../constants/actions';
import { isValidateNumber } from '../../../utils/helpers';
import styles from './index.module.less';

const getIndividualMark = (detailedMarks, submissionId, questionId) => {
  if (!detailedMarks) return 0;
  const submission = detailedMarks.find(d => d.submissionId === submissionId);
  if (submission) {
    const detailedMark = submission.questions.find(q => q.questionId === questionId);
    if (detailedMark) {
      return detailedMark.mark;
    }
  }
  return 0;
};

const getTotalMark = (totalMarks, submissionId) => {
  if (!totalMarks) return 0;
  const submission = totalMarks.find(t => t.submissionId === submissionId);
  if (submission) {
    return submission.totalMark;
  }
  return 0;
};

// sum up all marks in a questions list
const sumMarks = (detailedMarks, submissionId) => {
  if (!detailedMarks) return 0;
  const submission = detailedMarks.find(d => d.submissionId === submissionId);
  if (submission) {
    let sum = 0;
    submission.questions.forEach(q => sum = sum + q.mark);
    return sum;
  }
  return 0;
}

const AnswerCard = ({ currentQuestion, userId }) => {
  const dispatch = useDispatch();
  const { submissionId } = useParams();
  const { totalMarks, detailedMarks } = useSelector(state => state.subject);
  const { answer, mark, description, questionId } = currentQuestion;
  let preMark = 0;

  if (detailedMarks) {
    preMark = getIndividualMark(detailedMarks, Number(submissionId), Number(questionId));
  }

  const handleSaveMark = (e) => {
    if (isValidateNumber(e.target.value, 0, mark)) {
      dispatch({
        type: SAVE_INDIVIDUAL_MARK,
        payload: {
          submissionId: Number(submissionId),
          questionId: Number(questionId),
          mark: Number(e.target.value),
        }
      });
    } else {
      message.error("Please enter a valid mark");
    }
  };

  useEffect(() => {
    const newTotalMark = sumMarks(detailedMarks, Number(submissionId));
    console.log(`newTotalMarks: ${newTotalMark}`);
    // update totalMark
    dispatch({
      type: SAVE_TOTAL_MARK,
      payload: {
        submissionId: Number(submissionId),
        userId: Number(userId),
        totalMark: newTotalMark,
      }
    })
  }, [detailedMarks, submissionId, dispatch, userId]);

  return (
    <div className={styles.cardContainer}>
      <Card title={description} className={styles.questionCard}>
        <Input.TextArea
          disabled
          autoSize={{ minRows: 5, maxRows: 10 }}
          value={answer}
        />
      </Card>

      <div className={styles.inputContainer}>
        <Input
          className={styles.markInput}
          value={`${preMark}`}
          addonBefore={<span><HighlightOutlined /> Mark this question</span>}
          suffix={`/ ${mark}`}
          onChange={handleSaveMark}
        />

        <Input
          className={styles.totalMarkInput}
          value={`${getTotalMark(totalMarks, Number(submissionId))}`}
          addonBefore={`Total`}
          disabled
        />
      </div>
    </div>
  );
};

export default AnswerCard;