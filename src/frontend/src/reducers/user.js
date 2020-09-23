import {
  LOGIN,
  LOGOUT,
} from '../constants/actions';

const initState = {};

export default function reducer(state = initState, action) {
  switch (action.type) {
    case LOGIN:
      return {
        ...state,
        identity: action.payload
      }
    // case LOGOUT:
    //   return {}
    default:
      return state;
  }
};