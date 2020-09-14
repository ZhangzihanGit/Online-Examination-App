import React, { lazy, Suspense } from 'react';
import { Route, Switch } from 'react-router-dom';
import { Spin } from 'antd';
import styles from './index.module.less';

const Home = lazy(() => import('../pages/Home'));
const Login = lazy(() => import('../pages/Login'));

const Router = () => (
  <Suspense fallback={
    <div className={styles.spinWrap}>
      <Spin size="large" />
    </div>
  }>
    <Switch>
      <Route exact path="/" component={Home} />
      <Route exact path="/login" component={Login} />
    </Switch>
  </Suspense>
);

export default Router;