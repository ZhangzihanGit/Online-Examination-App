import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { List, Card, Button } from 'antd';
import {
  PlusOutlined,
  SettingOutlined,
  DeleteOutlined
} from '@ant-design/icons';
import { getSubject } from '../../actions/subject';
import styles from './index.module.less';

const getRandomANumber = (min, max) => {
  return Math.random() * (max - min) + min;
};

const SubjectList = () => {
  const dispatch = useDispatch();
  const history = useHistory();
  const { identity } = useSelector(state => state.user);
  const { pathname } = useSelector(state => state.router.location);
  const { subjectList } = useSelector(state => state.subject);
  const [imgLoading, setImgLoading] = useState(true);
  const isAdmin = identity && identity.userType === "admin";

  const addSubjectButton = {
    name: "Add Subject"
  };

  const renderList = subjectList ? (isAdmin ? [addSubjectButton, ...subjectList] : subjectList)
    : [];

  const handleAddSubject = () => {
    history.push(`/dashboard/subjects/create-subject`);
  };

  const handleSelectSubject = (item) => {
    dispatch(getSubject({
      userId: identity.userId,
      userType: identity.userType,
      subjectId: item.id,
    }, `${pathname}/${item.subjectCode}`));
  };

  const handleDeleteSubject = (item) => {
  };

  return (
    <List
      className={styles.subjectsContainer}
      size="small"
      rowKey="id"
      grid={{ gutter: 24, xxl: 4, xl: 3, lg: 2, md: 2, sm: 2, xs: 1 }}
      dataSource={renderList}
      renderItem={(item) => {
        if (item.name === "Add Subject") {
          return (
            <List.Item>
              <Button
                className={styles.addSubjectButton}
                type="dashed"
                onClick={handleAddSubject}
              >
                <PlusOutlined /> {item.name}
              </Button>
            </List.Item>
          )
        } else {
          return (
            <List.Item>
              <Card
                hoverable
                loading={imgLoading}
                size="small"
                cover={
                  <img
                    alt={`https://picsum.photos/seed/${getRandomANumber(1, 10)}/300/180`}
                    src={`https://picsum.photos/seed/${getRandomANumber(1, 10)}/300/180`}
                    onLoad={() => setImgLoading(false)}
                    onClick={() => handleSelectSubject(item)}
                  />}
                actions={isAdmin ? [
                  <SettingOutlined key="setting" />,
                  <DeleteOutlined key="delete" onClick={() => handleDeleteSubject(item)} />
                ] : null}
              >
                <Card.Meta
                  title={item.subjectCode}
                  description={item.description}
                  onClick={() => handleSelectSubject(item)}
                />
              </Card>
            </List.Item>
          )
        }
      }}
    />
  )
};

export default SubjectList;