import {
  GET_SUBJECT_LIST,
  GET_SUBJECT,
} from '../constants/actions';

const initState = {};

export default function reducer(state = initState, action) {

  switch (action.type) {
    case GET_SUBJECT_LIST:
      console.log(action.payload.subjectList)
      return {
        ...state,
        subjectList: action.payload.subjectList,
      }
    case GET_SUBJECT:
      return {
        ...state,
        examList: action.payload.examList,
        instructorList: action.payload.instructorList,
        studentList: action.payload.studentList,
      }
    // case CREATE_EXAM:
    //     return {
    //       ...state,
    //       examList
    //     }
    default:
      return state;
  }
};