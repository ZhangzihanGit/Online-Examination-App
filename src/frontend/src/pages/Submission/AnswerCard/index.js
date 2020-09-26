import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams, useHistory } from 'react-router-dom';
import { Card, Form, Radio, Input, Button, Checkbox } from 'antd';
import { HighlightOutlined } from '@ant-design/icons';
import { SAVE_TOTAL_MARK, SAVE_INDIVIDUAL_MARK } from '../../../constants/actions';
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

const AnswerCard = ({ currentQuestion }) => {
  const dispatch = useDispatch();
  const { code, examId, submissionId } = useParams();
  const { studentAnswer, totalMarks, detailedMarks } = useSelector(state => state.subject);
  const { answer, mark, description, questionId } = currentQuestion;
  let preMark = 0;

  if (detailedMarks) {
    preMark = getIndividualMark(detailedMarks, Number(submissionId), Number(questionId));
  }

  const handleSaveMark = (e) => {
    // TODO: validate the mark not exceeding the total mark
    dispatch({
      type: SAVE_INDIVIDUAL_MARK,
      payload: {
        submissionId: Number(submissionId),
        questionId: Number(questionId),
        mark: Number(e.target.value),
      }
    });
  };

  useEffect(() => {
    const newTotalMark = sumMarks(detailedMarks, Number(submissionId));
    // update totalMark
    dispatch({
      type: SAVE_TOTAL_MARK,
      payload: {
        submissionId: Number(submissionId),
        userId: Number(questionId),
        totalMark: newTotalMark,
      }
    })
  }, [detailedMarks]);

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