import { RELATIVE_PATH } from '../constants';
import {
  GET_SUBJECT_LIST,
  GET_SUBJECT,
  CREATE_EXAM,
  GET_EXAM,
  SAVE_ANSWER,
  PUBLISH_EXAM,
  CLOSE_EXAM,
  DELETE_EXAM,
  CREATE_SUBJECT,
  UPDATE_EXAM,
  SAVE_TOTAL_MARK,
  GET_SUBMISSIONS,
} from '../constants/actions';

const initState = {};

export default function reducer(state = initState, action) {
  let newList = [];

  switch (action.type) {
    case GET_SUBJECT_LIST:
      console.log(action.payload.subjectList)
      return {
        ...state,
        subjectList: action.payload.subjectList,
      }
    case CREATE_SUBJECT:
      return {
        ...state,
        subjectList: [action.payload, ...state.subjectList],
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
    case PUBLISH_EXAM:
      newList = state.examList.map(exam => {
        if (exam.examId === action.payload.examId) {
          return { ...exam, published: true };
        }
        return exam;
      });
      return {
        ...state,
        examList: newList,
      }
    case CLOSE_EXAM:
      newList = state.examList.map(exam => {
        if (exam.examId === action.payload.examId) {
          return { ...exam, closed: true };
        }
        return exam;
      });
      return {
        ...state,
        examList: newList,
      };
    case DELETE_EXAM:
      return {
        ...state,
        examList: state.examList.filter(exam => exam.examId !== action.payload.examId),
      }
    case SAVE_ANSWER:
      return {
        ...state,
        studentAnswer: action.payload,
      }
    case CREATE_EXAM:
      return {
        ...state,
        examList: [action.payload, ...state.examList],
      }
    case UPDATE_EXAM:
      newList = state.examList.map(exam => {
        if (exam.examId === action.payload.examId) {
          console.log(action.payload)
          return { ...exam, ...action.payload };
        }
        return exam;
      });
      return {
        ...state,
        examList: newList,
      }
    case GET_SUBMISSIONS:
      return {
        ...state,
        submissionList: action.payload,
      }
    case SAVE_TOTAL_MARK:
      // if the state does not have marks, then create one
      if (state.marks) {
        const found = state.marks.find(s => s.submissionId === action.payload.submissionId);
        if (found) {
          // if the mark already exist, then update the value
          newList = state.marks.map(s => {
            if (s.submissionId === action.payload.submissionId) {
              console.log(action.payload)
              console.log({ ...s, ...action.payload })
              return { ...s, ...action.payload };
            }
            return s;
          });
        } else {
          // otherwise just concatenate it
          newList = [...state.marks, action.payload];
        }
      } else {
        newList = [action.payload];
      }
      return {
        ...state,
        marks: newList,
      }
    default:
      return state;
  }
};