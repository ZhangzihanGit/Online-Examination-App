import { RELATIVE_PATH } from '../constants';
import {
  GET_SUBJECT_LIST,
  GET_SUBJECT,
  CREATE_EXAM,
  GET_EXAM,
  SAVE_ANSWER,
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
    case GET_EXAM:
      const oldExamList = state.examList;
      const incomeExamId = action.payload.examId;
      const examIndex = oldExamList.findIndex(({ examId }) => examId === incomeExamId);
      const updateExam = { ...oldExamList[examIndex], ...action.payload };
      return {
        ...state,
        examList: [
          ...oldExamList.slice(0, examIndex),
          updateExam,
          ...oldExamList.slice(examIndex + 1),
        ]
      }
    case SAVE_ANSWER:
      return {
        ...state,
        studentAnswer: action.payload,
      }
    // case CREATE_EXAM:
    //   return {
    //     ...state,
    //     newExam: action.payload,
    //   }
    default:
      return state;
  }
};