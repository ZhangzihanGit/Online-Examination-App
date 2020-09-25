import React, { useEffect, useState } from 'react';
import { useParams, useHistory, Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { List, Button } from 'antd';
import {
  PlayCircleOutlined,
  SettingOutlined,
  DeleteOutlined,
  StopOutlined
} from '@ant-design/icons';
import { getExam, publishExam, closeExam } from '../../../actions/subject';

const ContentList = ({ list, isExam }) => {
  const dispatch = useDispatch();
  const { identity } = useSelector(state => state.user);
  const { code } = useParams();
  const history = useHistory();
  const { pathname } = useSelector(state => state.router.location);
  // const { examList } = useSelector(state => state.subject);
  const [publisedList, setPublisedList] = useState([]);
  const isInstructor = identity && identity.userType === "instructor";
  const isStudent = identity && identity.userType === "student";

  const handleEditExam = (item) => {
    console.log(item);
    history.push(`/dashboard/subjects/${code}/edit-exam/${item.examId}`);
  }

  const handleDeleteExam = (item) => {
    // TODO: send request
    console.log(item);
  }

  const handleCloseExam = (item) => {
    // TODO: send request
    console.log('exam closed');
    console.log(item);
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
    // history.push(`/dashboard/subjects/${code}/${item.id}`);
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
              onClick={() => handleEditExam(item)}
              icon={<SettingOutlined />}
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