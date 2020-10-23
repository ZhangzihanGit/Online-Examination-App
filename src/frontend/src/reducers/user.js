import {
  LOGIN,
  GET_INSTRUCTOR_LIST,
  GET_STUDENT_LIST,
} from '../constants/actions';

const initState = {};

export default function reducer(state = initState, action) {
  switch (action.type) {
    case LOGIN:
      return {
        ...state,
        identity: action.payload,
        authenticated: true,
      }
    case GET_INSTRUCTOR_LIST:
      return {
        ...state,
        instructorList: action.payload,
      }
    case GET_STUDENT_LIST:
      return {
        ...state,
        studentList: action.payload,
      }
    default:
      return state;
  }
};