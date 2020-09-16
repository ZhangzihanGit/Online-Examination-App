import React, { lazy, Suspense } from 'react';
import { Route, Switch } from 'react-router-dom';
import { Spin } from 'antd';
import styles from './index.module.less';

const Dashboard = lazy(() => import('../pages/Dashboard'));
const Login = lazy(() => import('../pages/Login'));

const Router = () => (
  <Suspense fallback={
    <div className={styles.spinWrap}>
      <Spin size="large" />
    </div>
  }>
    <Switch>
      <Route path="/login" component={Login} />
      <Route path="/" component={Dashboard} />
    </Switch>
  </Suspense>
);

export default Router;