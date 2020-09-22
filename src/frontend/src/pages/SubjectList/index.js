import React, { useState } from 'react';
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

const SubjectList = ({ list }) => {
  const dispatch = useDispatch();
  const { identity } = useSelector(state => state.user);
  const { subjectList } = useSelector(state => state.subject);
  const [imgLoading, setImgLoading] = useState(true);
  const history = useHistory();
  const { url } = useRouteMatch();
  console.log(subjectList)

  console.log(identity);
  const isAdmin = identity && identity.username === "admin";

  const addSubjectButton = {
    name: "Add Subject"
  };

  const handleAddSubject = () => {
    console.log("subject created!");
  };

  const handleSelectSubject = (item) => {
    // TODO: get subject content from server
    history.push(`${url}/${item.showName}`);
  };

  const handleDeleteSubject = (item) => {
    console.log(item)
    dispatch(deleteSubject(item));
  };

  return (
    <List
      className={styles.subjectsContainer}
      size="small"
      rowKey="id"
      grid={{ gutter: 24, xxl: 4, xl: 3, lg: 2, md: 2, sm: 2, xs: 1 }}
      dataSource={isAdmin ? [addSubjectButton, ...list] : list}
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
                    alt={item.cover}
                    src={item.cover}
                    onLoad={() => setImgLoading(false)}
                    onClick={() => handleSelectSubject(item)}
                  />}
                actions={isAdmin ? [
                  <SettingOutlined key="setting" />,
                  <DeleteOutlined key="delete" onClick={() => handleDeleteSubject(item)} />
                ] : null}
              >
                <Card.Meta
                  title={item.showName}
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