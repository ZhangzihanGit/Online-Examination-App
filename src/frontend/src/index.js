import React from 'react';
import ReactDOM from 'react-dom';
import throttle from 'lodash/throttle';
import { Provider } from 'react-redux';
import configureStore from './configureStore';
import { loadState, saveState } from './localStorage';
import App from './App';

const persistedState = loadState();
const store = configureStore(persistedState);

/**
 * By wrapping callback in a throttle call, we can insure 
 * that the inner function we pass is not going to be called 
 * more often than our specified number of milliseconds.
 */
store.subscribe(throttle(() => {
  saveState(store.getState());
}, 1000));

/** only store user state in localStorage ? */
// store.subscribe(() => {
//   saveState({
//     user: store.getState().user,
//   });
// });

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>
  ,
  document.getElementById('root')
);

