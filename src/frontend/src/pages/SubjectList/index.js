import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { List, Card, Button } from 'antd';
import {
  PlusOutlined,
  SettingOutlined,
  DeleteOutlined
} from '@ant-design/icons';
import { deleteSubject } from '../../actions/subject';
import styles from './index.module.less';

const getRandomANumber = (min, max) => {
  return Math.random() * (max - min) + min;
};

const SubjectList = () => {
  const dispatch = useDispatch();
  const { identity } = useSelector(state => state.user);
  const { subjectList } = useSelector(state => state.subject);
  const [imgLoading, setImgLoading] = useState(true);
  const history = useHistory();
  const { url } = useRouteMatch();

  const isAdmin = identity && identity.userType === "admin";

  const addSubjectButton = {
    name: "Add Subject"
  };

  const renderList = subjectList ? (isAdmin ? [addSubjectButton, ...subjectList] : subjectList)
    : [];

  // useEffect(() => {
  //   dispatch(getSubjectList(10));
  // }, [dispatch]);

  const handleAddSubject = () => {
    console.log("subject created!");
  };

  const handleSelectSubject = (item) => {
    // TODO: get subject content from server
    history.push(`${url}/${item.subjectCode}`);
  };

  const handleDeleteSubject = (item) => {
    dispatch(deleteSubject(item));
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