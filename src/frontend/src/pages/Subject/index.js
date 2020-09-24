import React from 'react';
import { useParams, useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { Divider, Button } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import ContentList from './ContentList';
// import ManageExam from '../manageExam/manageExam';
import styles from './index.module.less';

const adminView = (instructorList, studentList) => (
  <>
    <Divider orientation="center">Instructors</Divider>
    <ContentList list={instructorList} />
    <Divider orientation="center">Students</Divider>
    <ContentList list={studentList} />
  </>
);

const Subject = () => {
  const { code } = useParams();
  const history = useHistory();
  const { identity } = useSelector(state => state.user);
  const { examList, instructorList, studentList } = useSelector(state => state.subject);
  const isAdmin = identity && identity.userType === "admin";
  const isInstructor = identity && identity.userType === "instructor";

  const handleCreateExam = () => {
    history.push(`/dashboard/subjects/${code}/create-exam`);
  };
  console.log(examList);

  return (
    <div className={styles.examListContaner}>
      {isAdmin ? adminView(instructorList, studentList) : null}
      <Divider orientation="center">Exams</Divider>
      <ContentList
        list={examList}
        isExam={true}
      />
      { isInstructor && (
        <div className={styles.subjectContainer}>
          <Button
            className={styles.createExamButton}
            icon={<PlusOutlined />}
            onClick={handleCreateExam}
          >
            Create Exam
        </Button>
        </div>
      )}
    </div>
  )
};

export default Subject;