import { combineReducers } from 'redux';
import { connectRouter } from 'connected-react-router';
import user from './user';
import subject from './subject';

const createRootReducer = (history) => combineReducers({
  router: connectRouter(history),
  user,
  subject,
})
export default createRootReducer;