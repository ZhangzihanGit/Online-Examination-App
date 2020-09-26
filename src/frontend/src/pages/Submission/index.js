import React from 'react';
import { Input, Divider, List, Button } from 'antd';
import { useParams } from 'react-router-dom';
import { push } from 'connected-react-router';
import { useSelector, useDispatch } from 'react-redux';
import { SAVE_TOTAL_MARK } from '../../constants/actions';
import styles from './index.module.less';

// const submissionList = [
//   {
//     submissionId: 1,
//     userId: 111,
//     questions: [
//       {
//         questionId: 1,
//         answer: "student answer"
//       }, {
//         questionId: 2,
//         answer: "student answer"
//       },
//     ]
//   }, {
//     submissionId: 2,
//     userId: 222,
//     questions: [
//       {
//         questionId: 1,
//         answer: "student answer"
//       }, {
//         questionId: 2,
//         answer: "student answer"
//       },
//     ]
//   }
// ];

// get recent total mark from the store
const getDefaultTotalMark = (marks, submissionId) => {
  if (!marks) return 0;
  const found = marks.find(s => s.submissionId === submissionId);
  if (found) {
    return found.totalMark;
  }
  return 0;
}

const Submission = () => {
  const dispatch = useDispatch();
  const { examId } = useParams();
  const { pathname } = useSelector(state => state.router.location);
  const { submissionList, marks } = useSelector(state => state.subject);

  const handleSubmitMark = () => {
    console.log("save mark");
  }

  const handleSaveMark = (e, item) => {
    // TODO: validate the input mark not exceeding the total mark 
    dispatch({
      type: SAVE_TOTAL_MARK,
      payload: {
        submissionId: item.submissionId,
        userId: item.userId,
        totalMark: Number(e.target.value),
      }
    })
  };

  const handleSubmissionClick = (item) => {
    // goes to the detailed view of the submission
    dispatch(push(`${pathname}/submission-${item.submissionId}`));
  }

  return (
    <div className={styles.listContainer}>
      <Divider orientation="center">Mark Exam</Divider>

      <List
        bordered
        dataSource={submissionList && submissionList.submissions}
        renderItem={item => (
          <List.Item
            actions={[
              <Input
                className={styles.markInput}
                defaultValue={getDefaultTotalMark(marks, item.submissionId)}
                prefix="Total:  "
                suffix={submissionList ? `/ ${submissionList.totalMark}` : 100}
                onBlur={(e) => handleSaveMark(e, item)}
              />
            ]}
          >
            <List.Item.Meta
              style={{ cursor: 'pointer' }}
              title={`Student Id: ${item.userId}`}
              description={`Submission Id: ${item.submissionId}`}
              onClick={() => handleSubmissionClick(item)}
            />
          </List.Item>
        )}
      />

      <div className={styles.buttonContainer}>
        <Button
          className={styles.submitButton}
          type="primary"
          onClick={handleSubmitMark}
        >
          Save All Marks
        </Button>
      </div>
    </div>
  )
};

export default Submission;