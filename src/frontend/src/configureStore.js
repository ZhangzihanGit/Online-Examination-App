import { createBrowserHistory } from 'history';
import { applyMiddleware, createStore } from 'redux';
import thunk from 'redux-thunk';
import { composeWithDevTools } from 'redux-devtools-extension';
import { routerMiddleware } from 'connected-react-router';
import createRootReducer from './reducers';
import { RELATIVE_PATH } from './constants';

/**
 * In frontend development environment (without fetching request from server),
 * replace: RELATIVE_PATH with ''.
 * If need to fetch data from the server or in production, then add RELATIVE_PATH
 */
export const history = createBrowserHistory({
  basename: RELATIVE_PATH,
});

export default function configureStore(preloadedState) {
  const store = createStore(
    createRootReducer(history), // root reducer with router state
    preloadedState,
    composeWithDevTools(
      applyMiddleware(
        routerMiddleware(history), // for dispatching history actions
        thunk,
      )
    ),
    // compose(
    //   applyMiddleware(
    //     routerMiddleware(history), // for dispatching history actions
    //     // ... other middlewares ...
    //   ),
    // ),
  )

  return store;
};
