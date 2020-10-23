import React from 'react';
import { useSelector } from 'react-redux';
import { Redirect, Route } from 'react-router-dom';

const AuthRoute =  ({component: Component, ...rest}) => {
  const { authenticated } = useSelector(state => state.user);
  console.log(authenticated);

  return (
    <Route
      {...rest}
      render={(props) => authenticated === true
        ? <Component {...props} />
        : <Redirect to='/' />}
    />
  )
};

export default AuthRoute;