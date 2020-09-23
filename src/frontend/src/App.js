import React from 'react';
import Router from './router';
import { history } from './configureStore';
import { ConnectedRouter } from 'connected-react-router';

const App = () => (
  <ConnectedRouter history={history}>
    <Router />
  </ConnectedRouter>
);

export default App;
