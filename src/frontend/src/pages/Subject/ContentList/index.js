import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { List, Typography, Divider } from 'antd';
import {
  PlusOutlined,
  SettingOutlined,
  DeleteOutlined
} from '@ant-design/icons';
import styles from './index.module.less';

const ContentList = ({ list, isExam }) => {
  const { identity } = useSelector(state => state.user);
  const isInstructor = identity && identity.userType === "instructor";

  const handleEditExam = (item) => {
    console.log(item);
  }

  const handleDeleteExam = (item) => {
    // TODO: send request
    console.log(item);
  }

  return (
    <List
      bordered
      dataSource={list}
      renderItem={item => (
        <List.Item
          actions={isExam && isInstructor ? [
            <span onClick={() => handleEditExam(item)}>
              <SettingOutlined className={styles.actionIcon} />Edit
            </span>,
            <span onClick={() => handleDeleteExam(item)}>
              <DeleteOutlined className={styles.actionIcon} />Delete
            </span>,
          ] : null}
        >
          <List.Item.Meta
            title={<a href="#!">{item.userName}</a>}
            description={`${item.userId} ${item.userType}`}
          />
        </List.Item>
      )}
    />
  )
};

export default ContentList;