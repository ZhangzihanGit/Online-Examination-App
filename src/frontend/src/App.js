import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import Router from './router';
import { RELATIVE_PATH } from './constants';

const App = () => (
  // In dev env replace: RELATIVE_PATH with ''
  <BrowserRouter basename={process.env.REACT_APP_ROUTER_BASE || RELATIVE_PATH}>
    <Router />
  </BrowserRouter>
)

export default App;
