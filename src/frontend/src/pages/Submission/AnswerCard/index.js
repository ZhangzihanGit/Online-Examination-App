import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams, useHistory } from 'react-router-dom';
import { Card, Form, Radio, Input, Button, Checkbox } from 'antd';
import { HighlightOutlined } from '@ant-design/icons';
import { splitOptions, findAnswerById } from '../../../utils/helpers';
import { SAVE_ANSWER, SAVE_INDIVIDUAL_MARK } from '../../../constants/actions';
import styles from './index.module.less';

const AnswerCard = ({ currentQuestion }) => {
  const dispatch = useDispatch();
  const { code, examId, submissionId } = useParams();
  const { studentAnswer, marks } = useSelector(state => state.subject);
  const { answer, mark, description, questionId } = currentQuestion;

  const handleSaveMark = (e) => {
    console.log(e.target.value);
    // TODO: validate the mark not exceeding the total mark
    // const submission = marks.find(o => o.submissionId === Number(submissionId));
    // const totalMark = submission.totalMark + Number(e.target.value);
    dispatch({
      type: SAVE_INDIVIDUAL_MARK,
      payload: {
        submissionId: Number(submissionId),
        questionId,
        mark: Number(e.target.value),
      }
    });
  }

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
          defaultValue={"0"}
          addonBefore={<span><HighlightOutlined /> Mark this question</span>}
          suffix={`/ ${mark}`}
          onBlur={handleSaveMark}
        />

        <Input
          className={styles.totalMarkInput}
          value="0"
          addonBefore={`Total`}
          onBlur={handleSaveMark}
          disabled
        />
      </div>
    </div>
  );
};

export default AnswerCard;