import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Card, Form, Radio, Input, Button, Checkbox } from 'antd';
import { splitOptions, findAnswerById } from '../../../utils/helpers';
import { SAVE_ANSWER } from '../../../constants/actions';
import styles from './index.module.less';

const radioStyle = {
  display: 'block',
  height: '30px',
  lineHeight: '30px',
};

/**
 * Replace the old answer of the question with the new one.
 * @param {*} studentAnswer old answer state
 * @param {*} questionID target question
 * @param {*} e new answer value
 * @return new state
 */
const getNewState = (studentAnswer, questionID, e) => {
  let newState = [];
  if (studentAnswer) {
    const previousAnswerIndex = studentAnswer.findIndex(({ questionId }) => questionId === questionID);
    if (previousAnswerIndex !== -1) {
      const newAnswer = { ...studentAnswer[previousAnswerIndex], answer: e.target.value };
      newState = [
        ...studentAnswer.slice(0, previousAnswerIndex),
        newAnswer,
        ...studentAnswer.slice(previousAnswerIndex + 1)
      ];
    } else {
      newState = [
        ...studentAnswer,
        {
          questionId: questionID,
          answer: e.target.value,
        }
      ];
    }
  } else {
    newState = [{
      questionId: questionID,
      answer: e.target.value,
    }];
  }

  return newState;
}

const QuestionCard = ({ currentQuestion }) => {
  const dispatch = useDispatch();
  const { studentAnswer } = useSelector(state => state.subject);
  const { questionType, options, description, questionID } = currentQuestion;
  const formattedOptions = splitOptions(options);
  let prevAnswer = null;

  if (studentAnswer) {
    const found = findAnswerById(studentAnswer, questionID);
    if (found) {
      prevAnswer = found.answer;
    }
  }
  splitOptions(options);

  // store the answer to redux store
  const handleAnswer = (e) => {
    const newState = getNewState(studentAnswer, questionID, e);
    dispatch({
      type: SAVE_ANSWER,
      payload: newState,
    });
  };

  return (
    <div className={styles.cardContainer}>
      <Card title={description} className={styles.questionCard}>
        {questionType.toLowerCase() === "shortanswer" && (
          <Input.TextArea
            onChange={handleAnswer}
            autoSize={{ minRows: 5, maxRows: 10 }}
            allowClear
            value={prevAnswer ? prevAnswer : ""}
          />
        )}
        {questionType.toLowerCase() === "multiplechoice" && (
          <Radio.Group style={radioStyle} onChange={handleAnswer} value={prevAnswer ? prevAnswer : ""}>
            {formattedOptions.map(option => (
              <Radio
                key={option}
                style={radioStyle}
                value={option}
              >
                {option}
              </Radio>
            ))}
          </Radio.Group>
        )}
      </Card>

    </div>
  );
};

export default QuestionCard;