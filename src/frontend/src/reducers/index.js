import { combineReducers } from 'redux';
import user from './user';
import subject from './subject';

const rootReducer = combineReducers({
  user,
  subject,
});

export default rootReducer;