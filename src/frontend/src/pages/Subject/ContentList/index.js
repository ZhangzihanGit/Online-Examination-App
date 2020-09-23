import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { List, Button } from 'antd';
import {
  PlusOutlined,
  PlayCircleOutlined,
  SettingOutlined,
  DeleteOutlined
} from '@ant-design/icons';

const ContentList = ({ list, isExam }) => {
  const { identity } = useSelector(state => state.user);
  // const { examList } = useSelector(state => state.subject);
  const [publisedList, setPublisedList] = useState([]);
  const isInstructor = identity && identity.userType === "instructor";
  const isStudent = identity && identity.userType === "student";

  const handleEditExam = (item) => {
    console.log(item);
  }

  const handleDeleteExam = (item) => {
    // TODO: send request
    console.log(item);
  }

  const handlePublishExam = (item) => {
    console.log(item);
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
            title={<a href="#!">{item.showName}</a>}
            description={`${item.description}`}
          />
        </List.Item>
      )}
    />
  )
};

export default ContentList;