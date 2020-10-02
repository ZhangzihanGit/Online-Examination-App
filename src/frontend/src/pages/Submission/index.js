import React, { useEffect, useState } from 'react';
import { Input, Divider, List, Button, message } from 'antd';
import { CloudUploadOutlined } from '@ant-design/icons';
import { useParams } from 'react-router-dom';
import { push } from 'connected-react-router';
import { useSelector, useDispatch } from 'react-redux';
import { submitMarks } from '../../actions/subject';
import { SAVE_TOTAL_MARK } from '../../constants/actions';
import { isValidateNumber } from '../../utils/helpers';
import styles from './index.module.less';

// get recent total mark from the store
const getDefaultTotalMark = (totalMarks, submissionId) => {
  if (!totalMarks) return 0;
  const found = totalMarks.find(s => s.submissionId === submissionId);
  if (found) {
    return found.totalMark;
  }
  return 0;
}

const Submission = () => {
  const dispatch = useDispatch();
  const { code, examId } = useParams();
  const { pathname } = useSelector(state => state.router.location);
  const { submissionList, totalMarks, detailedMarks } = useSelector(state => state.subject);
  const [haveSubmissions, setHaveSubmissions] = useState(false);
  console.log(haveSubmissions)

  useEffect(() => {
    if (submissionList) {
      if (submissionList.submissions.length === 0) {
        setHaveSubmissions(false);
      } else {
        setHaveSubmissions(true);
      }
    }
  }, [submissionList]);

  const handleSubmitMarks = () => {
    // if (totalMarks.length !== detailedMarks.length) {
    //   message.error('Please mark each question, not just a total mark!');
    //   return;
    // }

    // check if we have submissions for this exam
    // assignedMark

    const data = totalMarks.map(submission => {
      const found = detailedMarks.find(d => submission.submissionId === d.submissionId);
      if (found) {
        return { ...submission, ...found };
      } else
        return { ...submission };
    });

    dispatch(submitMarks({
      examId: Number(examId),
      marks: data,
    }, `/dashboard/subjects/${code}`))
  }

  const handleSaveMark = (e, item) => {
    // validate the input mark not exceeding the total mark 
    const totalMark = submissionList ? submissionList.totalMark : 100;
    if (isValidateNumber(e.target.value, 0, totalMark)) {
      dispatch({
        type: SAVE_TOTAL_MARK,
        payload: {
          submissionId: item.submissionId,
          userId: item.userId,
          totalMark: Number(e.target.value),
        }
      })
    } else {
      message.error("Please enter a valid mark");
    }
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
                defaultValue={getDefaultTotalMark(totalMarks, item.submissionId)}
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
          disabled={!haveSubmissions}
          icon={<CloudUploadOutlined />}
          onClick={handleSubmitMarks}
        >
          Submit All Marks
        </Button>
      </div>
    </div>
  )
};

export default Submission;