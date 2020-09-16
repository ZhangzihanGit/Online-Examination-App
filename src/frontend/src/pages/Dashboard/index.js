import React, { useState } from 'react';
import { Switch, Route, Link, useRouteMatch, } from 'react-router-dom';
import { Layout, Menu, Avatar, Breadcrumb } from 'antd';
import { UserOutlined, HomeOutlined } from '@ant-design/icons';
import Home from '../Home';
import SubjectList from '../SubjectList';
import styles from './index.module.less';

const { Header, Sider, Content, Footer } = Layout;

const subjectList = [{
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
  lastName: 'Wang',
  identity: 'Student',
}

const menuList = [
  { key: '/home', name: 'Home', content: () => <Home /> },
  { key: '/subjects', name: 'Subjects', content: () => <SubjectList list={subjectList} /> },
  { key: '/settings', name: 'Settings', content: () => <h2>Settings</h2> },
];

const breadcrumbNameMap = {
  '/home': <HomeOutlined />,
  '/subjects': 'Subjects',
  '/settings': 'Settings',
};

const extraBreadcrumbItems = location => {
  console.log(location.pathname)
  // get current URL, split them and remove empty string
  const pathSnippets = location.pathname.split('/').filter(i => i);
  console.log(location.pathname.split('/'));
  return pathSnippets.map((_, index) => {
    const url = `/${pathSnippets.slice(0, index + 1).join('/')}`;
    console.log(url)
    return (
      <Breadcrumb.Item key={url}>
        <Link to={url}>{breadcrumbNameMap[url]}</Link>
      </Breadcrumb.Item>
    );
  });
}

const Dashboard = ({ location }) => {
  // const [menuKey, setMenuKey] = useState(null);
  // console.log(menuKey)

  // let { path, url } = useRouteMatch();
  // console.log(path, url)
  // console.log(props.history.location.pathname);


  const breadcrumbItems = [].concat(extraBreadcrumbItems(location));
  // const breadcrumbItems = [
  //   <Breadcrumb.Item key="home">
  //     <Link to="/home"><HomeOutlined /></Link>
  //   </Breadcrumb.Item>,
  // ].concat(extraBreadcrumbItems(location));

  return (
    <Layout className={styles.container}>
      <Sider>
        <div className={styles.avatarContainer}>
          <Avatar size="large" icon={<UserOutlined />} />
          <div>{`${fakeUserInfo.firstName} ${fakeUserInfo.lastName}`}</div>
          <div>{`${fakeUserInfo.identity}`}</div>
        </div>
        <Menu
          theme="dark"
          defaultSelectedKeys={['/home']}
          selectedKeys={[location.pathname]}
          onClick={(item) => {
            console.log(item);
          }}
        >
          {menuList.map(({ key, name }) => (
            <Menu.Item key={key}>
              <Link to={key}>{name}</Link>
            </Menu.Item>
          ))}
        </Menu>
      </Sider>

      <Layout className={styles.rightPanel}>
        <Header>Header</Header>
        <Content className={styles.contentContainer}>
          <Breadcrumb>
            {breadcrumbItems}
          </Breadcrumb>
          <div className={styles.content}>
            {/* {renderContentByKey(menuKey)} */}
            <Switch>
              {menuList.map(({ key, content }) => {
                return (
                  <Route key={key} path={key} render={content} />
                )
              })}
            </Switch>
          </div>
        </Content>
      </Layout>
    </Layout >
  )
}

export default Dashboard;