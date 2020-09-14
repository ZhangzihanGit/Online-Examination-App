import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import Router from './router';

const App = () => (
  // In dev env remove: /online_examination_war_exploded
  <BrowserRouter basename={process.env.REACT_APP_ROUTER_BASE || ''}>
    <Router />
  </BrowserRouter>
)

export default App;
