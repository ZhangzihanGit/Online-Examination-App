import React from 'react';
import { Link } from 'react-router-dom';
import { Layout, Menu, Card, Avatar, PageHeader } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import Subjects from './components/Subjects';
import styles from './index.module.less';

const { Header, Sider, Content, Footer } = Layout;

const subjects = [{
  id: 1,
  code: 'SWEN90010',
  name: 'High Integrity Systems Engineering',
  cover: "https://picsum.photos/seed/1/300/180",
}, {
  id: 2,
  code: 'SWEN90004',
  name: 'Modelling Complex Software System',
  cover: "https://picsum.photos/seed/2/300/180",
}, {
  id: 3,
  code: 'SWEN90007',
  name: 'Software Design and Architecture',
  cover: "https://picsum.photos/seed/3/300/180",
}, {
  id: 4,
  code: 'COMP90043',
  name: 'Cryptography and Security',
  cover: "https://picsum.photos/seed/4/300/180",
}, {
  id: 5,
  code: 'COMP90048',
  name: 'Declarative Programming',
  cover: "https://picsum.photos/seed/5/300/180",
}, {
  id: 6,
  code: 'COMP30027',
  name: 'Machine Learning',
  cover: "https://picsum.photos/seed/6/300/180",
}, {
  id: 7,
  code: 'SWEN30006',
  name: 'Software Modelling and Design',
  cover: "https://picsum.photos/seed/7/300/180",
}];

const fakeUserInfo = {
  id: 1,
  firstName: 'Mike',
  lastName: 'Wong',
  identity: 'Student',
}

const Home = () => {

  return (
    <Layout className={styles.container}>

      <Sider collapsible>
        <div className={styles.avatarContainer}>
          <Avatar size="large" icon={<UserOutlined />} />
          <div>{`${fakeUserInfo.firstName} ${fakeUserInfo.lastName}`}</div>
          <div>{`${fakeUserInfo.identity}`}</div>
        </div>
        <Menu theme="dark">
          <Menu.Item key="1">Subjects</Menu.Item>
        </Menu>
      </Sider>

      <Layout className={styles.rightPanel}>
        <Header>Header</Header>
        <Content className={styles.contentContainer}>
          <div className={styles.content}>
            hah
            <Subjects list={subjects} />
          </div>
        </Content>
      </Layout>
    </Layout>
  )
}

export default Home;