import * as api from '../api/subject';
import { push } from 'connected-react-router';
import {
  GET_SUBJECT,
  GET_SUBJECT_LIST,
  GET_EXAM,
  PUBLISH_EXAM,
  CLOSE_EXAM,
  DELETE_EXAM,
  CREATE_SUBJECT,
  CREATE_EXAM,
  UPDATE_EXAM,
  GET_SUBMISSIONS,
  SAVE_TOTAL_MARK,
  SAVE_INDIVIDUAL_MARK
} from '../constants/actions';
import { message } from 'antd';

export function getSubjectList(payload = {}) {
  return async (dispatch) => {
    try {
      const result = await api.getSubjectList(payload);
      dispatch({
        type: GET_SUBJECT_LIST,
        payload: result.data,
      });
    } catch (error) {
      message.error('Fail to fetch all subjects');
    }
  }
};

export function getSubject(payload = {}, pathname) {
  return async (dispatch) => {
    try {
      const result = await api.getSubject(payload);
      dispatch({
        type: GET_SUBJECT,
        payload: result.data,
      });
      dispatch(push(pathname));
    } catch (error) {
      message.error('Fail to fetch subject');
    }
  }
};

export function createSubject(payload = {}, pathname) {
  return async (dispatch) => {
    try {
      const result = await api.createSubject(payload);
      dispatch({
        type: CREATE_SUBJECT,
        payload: result.data.subject,
      });
      message.success('Create subject successfully');
      dispatch(push(pathname));
    } catch (error) {
      message.error('Fail to create subject')
    }
  }
};

export function getExam(payload = {}, pathname) {
  return async (dispatch) => {
    try {
      const result = await api.getExam(payload);
      dispatch({
        type: GET_EXAM,
        payload: result.data,
      });
      dispatch(push(pathname));
      message.success('Fetching exam successfully');
    } catch (error) {
      message.error('Fail to fetch exam');
    }
  }
};

export function startExam(payload = {}, pathname) {
  return async (dispatch) => {
    try {
      const result = await api.startExam(payload);
      console.log(result);
    } catch (error) {
      message.error(error.response.data.message);
    }
  }
};

export function submitExam(payload = {}, pathname) {
  return async (dispatch) => {
    try {
      const result = await api.submitExam(payload);
      // submit exam does not need to update store
      message.success('Submit exam successfully');
      // goes back to exam page
      dispatch(push(pathname));
    } catch (error) {
      message.error(error.response.data.message);
    }
  }
};

export function updateExam(payload = {}, pathname) {
  return async (dispatch) => {
    try {
      const result = await api.updateExam(payload);
      dispatch({
        type: UPDATE_EXAM,
        payload: result.data,
      });
      message.success('Update exam successfully');
      // goes back to exam page
      dispatch(push(pathname));
    } catch (error) {
      message.error(error.response.data.message);
    }
  }
};

export function publishExam(payload = {}) {
  return async (dispatch) => {
    try {
      const result = await api.publishExam(payload);
      dispatch({
        type: PUBLISH_EXAM,
        payload: payload,
      });
      message.success('Publish exam successfully');
    } catch (error) {
      message.error(error.response.data.message);
    }
  }
};

export function closeExam(payload = {}) {
  return async (dispatch) => {
    try {
      const result = await api.closeExam(payload);
      dispatch({
        type: CLOSE_EXAM,
        payload: payload,
      });
      message.success('Close exam successfully');
    } catch (error) {
      message.error(error.response.data.message);
    }
  }
};

export function createExam(payload = {}, pathname) {
  return async (dispatch) => {
    try {
      const result = await api.createExam(payload);
      dispatch({
        type: CREATE_EXAM,
        payload: result.data,
      });
      message.success('Create exam successfully');
      dispatch(push(pathname));
    } catch (error) {
      message.error('Fail to create exam');
    }
  }
};

export function deleteExam(payload = {}) {
  return async (dispatch) => {
    try {
      const result = await api.deleteExam(payload);
      dispatch({
        type: DELETE_EXAM,
        payload: payload,
      });
      message.success('Delete exam successfully');
    } catch (error) {
      message.error('Fail to delete exam');
    }
  }
};

export function getSubmissions(payload = {}, pathname) {
  return async (dispatch) => {
    try {
      const result = await api.getSubmissions(payload);
      // update assignedTotalMark to Store
      const totalMarks = [];
      result.data.submissions.forEach(s => {
        let assignedTotalMark = 0;
        s.questions.forEach(q => assignedTotalMark += q.assignedMark);
        totalMarks.push({
          submissionId: s.submissionId,
          userId: s.userId,
          totalMark: assignedTotalMark,
        })
      });

      // update all submissions to Store
      dispatch({
        type: GET_SUBMISSIONS,
        payload: result.data,
      });

      totalMarks.forEach(t => dispatch({
        type: SAVE_TOTAL_MARK,
        payload: t,
      }))

      // update assignedMark of each question to Store
      const individualMarks = [];
      result.data.submissions.forEach(s => {
        const questions = [];
        s.questions.forEach(q => questions.push({
          questionId: q.questionId,
          mark: q.assignedMark,
        }))

        individualMarks.push({
          submissionId: s.submissionId,
          questions,
        })
      });

      individualMarks.forEach(s => {
        s.questions.forEach(q => dispatch({
          type: SAVE_INDIVIDUAL_MARK,
          payload: {
            submissionId: s.submissionId,
            questionId: q.questionId,
            mark: q.mark
          },
        }))
      });

      dispatch(push(pathname));
    } catch (error) {
      message.error('Fail to fetch submissions');
    }
  }
};

export function submitMarks(payload = {}, pathname) {
  return async (dispatch) => {
    try {
      const result = await api.submitMarks(payload);
      message.success('Submit marks successfully');
      dispatch(push(pathname));
    } catch (error) {
      message.error('Fail to submit marks');
    }
  }
};
