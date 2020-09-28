import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Card, Radio, Input } from 'antd';
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
 * @param {*} questionId target question
 * @param {*} e new answer value
 * @return new state
 */
const getNewState = (studentAnswer, questionId, e) => {
  let newState = [];
  if (studentAnswer) {
    const previousAnswerIndex = studentAnswer.findIndex(s => s.questionId === questionId);
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
          questionId,
          answer: e.target.value,
        }
      ];
    }
  } else {
    newState = [{
      questionId,
      answer: e.target.value,
    }];
  }

  return newState;
}

const QuestionCard = ({ currentQuestion }) => {
  const dispatch = useDispatch();
  const { studentAnswer } = useSelector(state => state.subject);
  const { questionType, options, description, questionId } = currentQuestion;
  const { identity } = useSelector(state => state.user);
  const isStudent = identity && identity.userType === "student";
  const formattedOptions = splitOptions(options);
  let prevAnswer = null;

  if (studentAnswer) {
    const found = findAnswerById(studentAnswer, questionId);
    if (found) {
      prevAnswer = found.answer;
    }
  }
  splitOptions(options);

  // store the answer to redux store
  const handleAnswer = (e) => {
    const newState = getNewState(studentAnswer, questionId, e);
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
            disabled={!isStudent}
            onChange={handleAnswer}
            autoSize={{ minRows: 5, maxRows: 10 }}
            allowClear
            value={prevAnswer ? prevAnswer : ""}
          />
        )}
        {questionType.toLowerCase() === "multiplechoice" && (
          <Radio.Group
            style={radioStyle}
            onChange={handleAnswer}
            value={prevAnswer ? prevAnswer : ""}
            disabled={!isStudent}
          >
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