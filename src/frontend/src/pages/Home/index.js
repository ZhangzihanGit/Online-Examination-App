import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Tabs, Typography } from 'antd';
import Login from '../Login';
import Register from '../Register';
import * as api from '../../api/test';
import styles from './index.module.less';

const { Title } = Typography;
const { TabPane } = Tabs;

const Home = () => {

  const [activeKey, setActiveKey] = useState('login');

  return (
    <div className={styles.loginContainer}>
      <div className={styles.login}>
        <Tabs
          centered
          activeKey={activeKey}
          onTabClick={(key) => setActiveKey(key)}
        >
          <TabPane tab="Login" key="login">
            {activeKey === "login" && <Login />}
          </TabPane>
          <TabPane tab="Register" key="register">
            {activeKey === "register" && <Register />}
          </TabPane>
        </Tabs>
      </div>
    </div>
  )
};

export default Home;