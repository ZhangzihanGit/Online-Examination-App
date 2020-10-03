import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { List, Button } from 'antd';
import {
  PlayCircleOutlined,
  EditOutlined,
  DeleteOutlined,
  StopOutlined,
  CheckOutlined
} from '@ant-design/icons';
import {
  getExam,
  startExam,
  publishExam,
  closeExam,
  deleteExam,
  getSubmissions
} from '../../../actions/subject';

const renderAdminView = (item) => [
  <Button
    size="small"
    type="ghost"
    disabled
    icon={<PlayCircleOutlined />}
  >
    {item.published ? "Publised" : "Unpublished"}
  </Button>,
  <Button
    size="small"
    type="ghost"
    disabled
    icon={<StopOutlined />}
  >
    {item.closed ? "Closed" : "Unclosed"}
  </Button>,
];

const ContentList = ({ list, isExam }) => {
  const dispatch = useDispatch();
  const { identity } = useSelector(state => state.user);
  const { pathname } = useSelector(state => state.router.location);
  const [publisedList, setPublisedList] = useState([]);
  const isAdmin = identity && identity.userType === "admin";
  const isInstructor = identity && identity.userType === "instructor";
  const isStudent = identity && identity.userType === "student";

  const handleEditExam = (item) => {
    if (isExam) {
      // when click edith exam, first get the exam
      dispatch(getExam({
        examId: item.examId,
      }, `${pathname}/edit-exam-${item.examId}`));
    }
  }

  const handleDeleteExam = (item) => {
    if (isExam) {
      dispatch(deleteExam({
        userId: identity.userId,
        examId: item.examId,
        subjectId: item.subjectId,
      }));
    }
  }

  const handleCloseExam = (item) => {
    if (isExam) {
      dispatch(closeExam({
        userId: identity.userId,
        examId: item.examId,
      }));
    }
  }

  const handlePublishExam = (item) => {
    if (isExam) {
      dispatch(publishExam({
        userId: identity.userId,
        examId: item.examId,
      }));
    }
  }

  const handleClickExam = (item) => {
    if (isExam) {
      if (isStudent) {
        dispatch(startExam({
          examId: item.examId,
          userId: identity.userId,
        }));
      }
      dispatch(getExam({
        examId: item.examId,
      }, `${pathname}/exam-${item.examId}`));
    }
  }

  const handleMarkExam = (item) => {
    if (isExam) {
      dispatch(getSubmissions({
        examId: item.examId,
        subjectId: item.subjectId
      }, `${pathname}/mark-exam-${item.examId}`));
    }
  }

  useEffect(() => {
    if (list) {
      const filteredList = list.filter(exam => exam.published);
      setPublisedList(filteredList);
    }
  }, [list]);

  return (
    <List
      bordered
      dataSource={isStudent ? publisedList : list}
      renderItem={item => (
        <List.Item
          actions={isExam && isInstructor ? [
            <Button
              size="small"
              type="ghost"
              disabled={item.published}
              onClick={() => handlePublishExam(item)}
              icon={<PlayCircleOutlined />}
            >
              {item.published ? "Publised" : "Publish"}
            </Button>,
            <Button
              size="small"
              type="ghost"
              disabled={item.published ? item.closed : true}
              onClick={() => handleCloseExam(item)}
              icon={<StopOutlined />}
            >
              {item.closed ? "Closed" : "Close"}
            </Button>,
            <Button
              size="small"
              type="ghost"
              onClick={() => handleMarkExam(item)}
              icon={<CheckOutlined />}
            >
              Mark
          </Button>,
            <Button
              size="small"
              type="ghost"
              onClick={() => handleEditExam(item)}
              icon={<EditOutlined />}
            >
              Edit
            </Button>,
            <Button
              size="small"
              type="ghost"
              onClick={() => handleDeleteExam(item)}
              icon={<DeleteOutlined />}
            >
              Delete
            </Button>,
          ] : (isExam && isAdmin ? renderAdminView(item) : null)}
        >
          <List.Item.Meta
            style={{ cursor: 'pointer' }}
            title={item.showName}
            description={isExam ? `${item.description}` : `id: ${item.userId}`}
            onClick={() => handleClickExam(item)}
          />
        </List.Item>
      )}
    />
  )
};

export default ContentList;