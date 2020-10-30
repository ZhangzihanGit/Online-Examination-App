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
  SAVE_INDIVIDUAL_MARK
} from '../constants/actions';

const initState = {};

export default function reducer(state = initState, action) {
  let newList = [];

  switch (action.type) {
    case GET_SUBJECT_LIST:
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
      // if (action.payload === 'clean') {
      //   console.log(state)
      //   return {
      //     ...state,
      //     totalMarks: [],
      //   }
      // }
        

      // if the state does not have marks, then create one
      if (state.totalMarks) {

        const currentSumissionIds = [];
        state.submissionList.submissions.forEach(s => currentSumissionIds.push(s.submissionId));
        const newTotalMarks = state.totalMarks.filter(s => currentSumissionIds.includes(s.submissionId));
        const found = newTotalMarks.find(s => s.submissionId === action.payload.submissionId);
        // const found = state.totalMarks.find(s => s.submissionId === action.payload.submissionId);
        if (found) {
          // if the mark already exist, then update the value
          newList = newTotalMarks.map(s => {
            if (s.submissionId === action.payload.submissionId) {
              return { ...s, ...action.payload };
            }
            return s;
          });
        } else {
          // otherwise just concatenate it
          newList = [...newTotalMarks, action.payload];
          // newList = [...state.totalMarks, action.payload];
          // newList = [action.payload];
        }
      } else {
        newList = [action.payload];
      }
      return {
        ...state,
        totalMarks: newList,
      }
    case SAVE_INDIVIDUAL_MARK:

      if (state.detailedMarks) {
        const currentSumissionIds = [];
        state.submissionList.submissions.forEach(s => currentSumissionIds.push(s.submissionId));
        const newDetailedMarks = state.detailedMarks.filter(s => currentSumissionIds.includes(s.submissionId));
        const found = newDetailedMarks.find(d => d.submissionId === action.payload.submissionId);
        // const found = state.detailedMarks.find(d => d.submissionId === action.payload.submissionId);
        if (found) {
          // if the mark for that question already exist, then update the value
          newList = newDetailedMarks.map(d => {
            if (d.submissionId === action.payload.submissionId) {
              // try to see if the question is in the questions list
              const foundQuestion = d.questions.find(q => q.questionId === action.payload.questionId);
              let newQuestions = [];
              if (foundQuestion) {
                newQuestions = d.questions.map(q => {
                  if (q.questionId === action.payload.questionId) {
                    return { ...q, mark: action.payload.mark };
                  }
                  return q;
                })
              } else {
                // question not in the list yet, so just concatenate it
                newQuestions = [...d.questions, {
                  questionId: action.payload.questionId,
                  mark: action.payload.mark,
                }]
              }

              return { ...d, questions: newQuestions }
            }
            return d;
          })
        } else {
          // otherwise just concatenate it
          // newList = [{
          //   submissionId: action.payload.submissionId,
          //   questions: [{
          //     questionId: action.payload.questionId,
          //     mark: action.payload.mark,
          //   }]
          // }];
          newList = [...newDetailedMarks, {
            submissionId: action.payload.submissionId,
            questions: [{
              questionId: action.payload.questionId,
              mark: action.payload.mark,
            }]
          }];
        }
      } else {
        newList = [{
          submissionId: action.payload.submissionId,
          questions: [{
            questionId: action.payload.questionId,
            mark: action.payload.mark,
          }]
        }];
      }
      return {
        ...state,
        detailedMarks: newList,
      }

    default:
      return state;
  }
};