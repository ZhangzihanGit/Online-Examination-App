import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import Router from './router';

const App = () => (
  <BrowserRouter basename={process.env.REACT_APP_ROUTER_BASE || ''}>
    <Router />
  </BrowserRouter>
)

export default App;
