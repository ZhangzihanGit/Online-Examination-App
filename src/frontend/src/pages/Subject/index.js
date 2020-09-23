import React from 'react';
import { useParams } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { List, Typography, Divider } from 'antd';
import ContentList from './ContentList';

const instructorList = [
  {
    userId: 1,
    userName: "maria",
    userType: "XXX",
  },
  {
    userId: 2,
    userName: "AAA",
    userType: "XXX",
  }, {
    userId: 3,
    userName: "BBB",
    userType: "XXX",
  }
];

const studentList = [
  {
    userId: 1,
    userName: "student1",
    userType: "XXX",
  },
  {
    userId: 2,
    userName: "student2",
    userType: "XXX",
  }, {
    userId: 3,
    userName: "student3",
    userType: "XXX",
  }
];

// const examList = [
//   {
//     userId: 1,
//     userName: "Exam1",
//     userType: "XXX",
//   },
//   {
//     userId: 2,
//     userName: "Exam2",
//     userType: "XXX",
//   }, {
//     userId: 3,
//     userName: "Exam3",
//     userType: "XXX",
//   }
// ];

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
  const { identity } = useSelector(state => state.user);
  const { examList } = useSelector(state => state.subject);
  const isAdmin = identity && identity.userType === "admin";
  const isInstructor = identity && identity.userType === "instructor";

  // const handleEditExam = (item) => {
  //   console.log(item);
  // }

  // const handleDeleteExam = ({ item, key }) => {
  //   // TODO: send request
  //   console.log(item);
  // }

  // const examActions = [
  //   <span onClick={handleEditExam}>
  //     <SettingOutlined className={styles.actionIcon} />Edit
  //   </span>,
  //   <span onClick={handleDeleteExam}>
  //     <DeleteOutlined className={styles.actionIcon} />Delete
  //   </span>,
  // ];

  return (
    <>
      {isAdmin ? adminView(instructorList, studentList) : null}
      <Divider orientation="center">Exams</Divider>
      <ContentList
        list={examList}
        isExam={true}
      />
    </>
  )
};

export default Subject;