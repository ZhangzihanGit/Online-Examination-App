import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Switch, Route, Link } from 'react-router-dom';
import { Layout, Menu, Avatar, Breadcrumb } from 'antd';
import { UserOutlined, HomeOutlined, LogoutOutlined } from '@ant-design/icons';
import SubjectList from '../SubjectList';
import Subject from '../Subject';
import { capitalizeFirstLetter } from '../../utils/helpers';
import { logout } from '../../actions/user';
import { getSubjectList } from '../../actions/subject';
import styles from './index.module.less';
import ExamForm from '../Exam/ExamForm';
import Exam from '../Exam';
import SubjectForm from '../Subject/SubjectForm';
import Submission from '../Submission';
import DetailedSubmission from '../Submission/DetailedSubmission';


const { Header, Sider, Content } = Layout;

const renderDashboard = (identity) => (
  <h2>{`Welcome, ${identity ? identity.username : `XXX`}.`}</h2>
);

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
  const dispatch = useDispatch();
  const breadcrumbItems = [].concat(extraBreadcrumbItems(location));
  const { identity } = useSelector(state => state.user);

  const menuList = [
    { key: '/dashboard/subjects', name: 'Subjects', content: () => <SubjectList /> },
    { key: '/dashboard/settings', name: 'Settings', content: () => <h2>Settings</h2> },
  ];

  const handleMenuClick = ({ _, key }) => {
    if (key === '/dashboard/subjects') {
      dispatch(getSubjectList({
        userId: identity.userId,
        userType: identity.userType,
        sessionId: identity.sessionId,
      }));
    }
  };

  const handleLogout = () => {
    dispatch(logout({
      sessionId: identity.sessionId
    }));
  }

  return (
    <Layout className={styles.container}>
      <Sider>
        <div className={styles.avatarContainer}>
          <Avatar size="large" icon={<UserOutlined />} />
          {/** first line renders username, second line renders identity */}
          <div>{identity && identity.showName}</div>
          <div>{identity && capitalizeFirstLetter(identity.userType)}</div>
        </div>
        <Menu
          theme="dark"
          selectedKeys={[location.pathname]}
          onClick={handleMenuClick}
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
              <span onClick={handleLogout}>
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
              <Route exact path="/dashboard/subjects/:code/mark-exam-:examId/submission-:submissionId" component={DetailedSubmission} />
              <Route exact path="/dashboard/subjects/create-subject" component={SubjectForm} />
              <Route exact path="/dashboard/subjects/:code/create-exam" component={ExamForm} />
              <Route exact path="/dashboard/subjects/:code/edit-exam-:examId" component={ExamForm} />
              <Route exact path="/dashboard/subjects/:code/mark-exam-:examId" component={Submission} />
              <Route exact path="/dashboard/subjects/:code/exam-:examId" component={Exam} />
              <Route exact path="/dashboard/subjects/:code" component={Subject} />


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