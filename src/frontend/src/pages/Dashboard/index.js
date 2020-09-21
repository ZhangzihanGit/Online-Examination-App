import React from 'react';
import { useSelector } from 'react-redux';
import { Switch, Route, Link } from 'react-router-dom';
import { Layout, Menu, Avatar, Breadcrumb } from 'antd';
import { UserOutlined, HomeOutlined, LogoutOutlined } from '@ant-design/icons';
import SubjectList from '../SubjectList';
import Subject from '../TakeExam/subject';
import styles from './index.module.less';

const { Header, Sider, Content } = Layout;

const renderDashboard = (identity) => (
  <h2>{`Welcome, ${identity ? identity.username : `XXX`}.`}</h2>
);

const capitalizeFirstLetter = (str) => str.charAt(0).toUpperCase() + str.slice(1);

const extraBreadcrumbItems = location => {
  // Get current URL, split them and remove empty string
  const pathSnippets = location.pathname.split('/').filter(i => i);
  let i = 0;
  return pathSnippets.map((_, index) => {
    const currentTitle = pathSnippets.slice(i++, index + 1).join('/');
    const url = `/${pathSnippets.slice(0, index + 1).join('/')}`;
    const breadcrumbText = currentTitle === 'dashboard'
      ? <HomeOutlined />
      : capitalizeFirstLetter(currentTitle);

    return (
      <Breadcrumb.Item key={url}>
        <Link to={`${url}`}>{breadcrumbText}</Link>
      </Breadcrumb.Item>
    );
  });
}

const Dashboard = ({ location }) => {
  const breadcrumbItems = [].concat(extraBreadcrumbItems(location));
  const { identity } = useSelector(state => state.user);
  const { subjectList } = useSelector(state => state.subject);
  console.log(subjectList)

  const menuList = [
    { key: '/dashboard/subjects', name: 'Subjects', content: () => <SubjectList list={subjectList} /> },
    { key: '/dashboard/settings', name: 'Settings', content: () => <h2>Settings</h2> },
  ];

  return (
    <Layout className={styles.container}>
      <Sider>
        <div className={styles.avatarContainer}>
          <Avatar size="large" icon={<UserOutlined />} />
          {/** first line renders username, second line renders identity */}
          <div>{identity && identity.username}</div>
          <div>{identity && identity.username}</div>
        </div>
        <Menu
          theme="dark"
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
        <Header>
          <div className={styles.header}>
            <Link to="/">
              <span>
                <LogoutOutlined className={styles.logout} />
                Log out
              </span>
            </Link>
          </div>
        </Header>
        <Content className={styles.contentContainer}>
          <Breadcrumb>
            {breadcrumbItems}
          </Breadcrumb>
          <div className={styles.content}>
            <Switch>
              <Route path="/dashboard/subjects/:code" component={Subject} />
              {menuList.map(({ key, content }) => {
                return (
                  <Route key={key} path={key} component={content} />
                )
              })}
              <Route path="/dashboard" component={() => renderDashboard(identity)} />
            </Switch>
          </div>
        </Content>
      </Layout>
    </Layout >
  )
}

export default Dashboard;