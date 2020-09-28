import {
  LOGIN,
  GET_INSTRUCTOR_LIST
} from '../constants/actions';

const initState = {};

export default function reducer(state = initState, action) {
  switch (action.type) {
    case LOGIN:
      return {
        ...state,
        identity: action.payload
      }
    case GET_INSTRUCTOR_LIST:
      return {
        ...state,
        instructorList: action.payload,
      }
    default:
      return state;
  }
};