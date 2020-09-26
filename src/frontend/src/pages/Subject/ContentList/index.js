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
  publishExam,
  closeExam,
  deleteExam,
  getSubmissions
} from '../../../actions/subject';

const ContentList = ({ list, isExam }) => {
  const dispatch = useDispatch();
  const { identity } = useSelector(state => state.user);
  const { pathname } = useSelector(state => state.router.location);
  // const { examList } = useSelector(state => state.subject);
  const [publisedList, setPublisedList] = useState([]);
  const isInstructor = identity && identity.userType === "instructor";
  const isStudent = identity && identity.userType === "student";

  const handleEditExam = (item) => {
    // when click edith exam, first get the exam
    dispatch(getExam({
      examId: item.examId,
    }, `${pathname}/edit-exam-${item.examId}`));
  }

  const handleDeleteExam = (item) => {
    console.log(item);
    dispatch(deleteExam({
      userId: identity.userId,
      examId: item.examId,
      subjectId: item.subjectId,
    }));
  }

  const handleCloseExam = (item) => {
    dispatch(closeExam({
      userId: identity.userId,
      examId: item.examId,
    }));
  }

  const handlePublishExam = (item) => {
    dispatch(publishExam({
      userId: identity.userId,
      examId: item.examId,
    }));
  }

  const handleClickExam = (item) => {
    dispatch(getExam({
      examId: item.examId,
    }, `${pathname}/exam-${item.examId}`));
  }

  const handleMarkExam = (item) => {
    dispatch(getSubmissions({
      examId: item.examId,
      subjectId: item.subjectId
    }, `${pathname}/mark-exam-${item.examId}`));
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
              disabled={item.closed}
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
          ] : null}
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