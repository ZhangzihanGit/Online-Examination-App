import React, { useEffect, useState } from 'react';
import { useParams, useHistory, Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { List, Button } from 'antd';
import {
  PlayCircleOutlined,
  SettingOutlined,
  DeleteOutlined
} from '@ant-design/icons';
import { getExam } from '../../../actions/subject';

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
    history.push(`/dashboard/subjects/${code}/edit-exam`);
  }

  const handleDeleteExam = (item) => {
    // TODO: send request
    console.log(item);
  }

  const handlePublishExam = (item) => {
    console.log(item);
  }

  const handleClickExam = (item) => {
    console.log(item);
    dispatch(getExam({
      examId: item.examId,
    }, `${pathname}/${item.examId}`));
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