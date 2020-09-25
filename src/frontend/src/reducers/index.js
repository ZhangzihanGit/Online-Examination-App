import { combineReducers } from 'redux';
import { connectRouter } from 'connected-react-router';
import { LOGOUT } from '../constants/actions';
import user from './user';
import subject from './subject';

/**
 * Clear Redux store when user logs out
 */
export const resetEnhancer = rootReducer => (state, action) => {
  if (action.type !== LOGOUT) return rootReducer(state, action);

  const newState = rootReducer(undefined, {});
  newState.router = state.router;
  return newState;
};

const createRootReducer = (history) => combineReducers({
  router: connectRouter(history),
  user,
  subject,
});

export default createRootReducer;