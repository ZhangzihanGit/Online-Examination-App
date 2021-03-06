import React, { lazy, Suspense } from 'react';
import { Route, Switch } from 'react-router-dom';
import { Spin } from 'antd';
import AuthRoute from '../auth';
import styles from './index.module.less';

const Dashboard = lazy(() => import('../pages/Dashboard'));
const Home = lazy(() => import('../pages/Home'));

const Router = () => (
  <Suspense fallback={
    <div className={styles.spinWrap}>
      <Spin size="large" />
    </div>
  }>
    <Switch>
      <Route exact path="/" component={Home} />
      <AuthRoute path='/dashboard' component={Dashboard} />
      {/* <Route path="/dashboard" component={Dashboard} /> */}
    </Switch>
  </Suspense>
);

export default Router;