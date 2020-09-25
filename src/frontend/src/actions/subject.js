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
  CREATE_EXAM
} from '../constants/actions';
import { message } from 'antd';

export function getSubjectList(payload = {}) {
  return async (dispatch) => {
    const result = await api.getSubjectList(payload);

    if (true) {
      dispatch({
        type: GET_SUBJECT_LIST,
        payload: result.data,
      });
    }
  }
};

export function getSubject(payload = {}, pathname) {
  return async (dispatch) => {
    const result = await api.getSubject(payload);

    if (true) {
      dispatch({
        type: GET_SUBJECT,
        payload: result.data,
      });
      dispatch(push(pathname));
    }
  }
};

export function createSubject(payload = {}, pathname) {
  return async (dispatch) => {
    const result = await api.createSubject(payload);

    if (result.status === 200) {
      dispatch({
        type: CREATE_SUBJECT,
        payload: result.data,
      });
      message.success('Create subject successfully');
      dispatch(push(pathname));
    } else {
      message.error('Fail to create subject');
    }
  }
};

export function getExam(payload = {}, pathname) {
  return async (dispatch) => {
    const result = await api.getExam(payload);

    if (result.status === 200) {
      dispatch({
        type: GET_EXAM,
        payload: result.data,
      });
      dispatch(push(pathname));
    }
  }
};

export function submitExam(payload = {}, pathname) {
  return async (dispatch) => {
    const result = await api.submitExam(payload);

    if (result.status === 200) {
      // TODO: current submit exam does not need to update store
      // dispatch({
      //   type: GET_EXAM,
      //   payload: result.data,
      // });
      message.success('Submit exam successfully');
      // goes back to exam page
      dispatch(push(pathname));
    } else {
      message.error('Fail to submit exam');
    }
  }
};

export function publishExam(payload = {}) {
  return async (dispatch) => {
    const result = await api.publishExam(payload);

    if (result.status === 200) {
      dispatch({
        type: PUBLISH_EXAM,
        payload: payload,
      });
      message.success('Publish exam successfully');
    } else {
      message.error('Fail to publish exam');
    }
  }
};

export function closeExam(payload = {}) {
  return async (dispatch) => {
    const result = await api.closeExam(payload);

    if (result.status === 200) {
      dispatch({
        type: CLOSE_EXAM,
        payload: payload,
      });
      message.success('Close exam successfully');
    } else {
      message.error('Fail to close exam');
    }
  }
};

export function createExam(payload = {}, pathname) {
  return async (dispatch) => {
    const result = await api.createExam(payload);

    if (result.status === 200) {
      dispatch({
        type: CREATE_EXAM,
        payload: result.data,
      });
      message.success('Create exam successfully');
      dispatch(push(pathname));
    } else {
      message.error('Fail to create exam');
    }
  }
};

export function deleteExam(payload = {}) {
  return async (dispatch) => {
    const result = await api.deleteExam(payload);

    if (result.status === 200) {
      dispatch({
        type: DELETE_EXAM,
        payload: payload,
      });
      message.success('Delete exam successfully');
    } else {
      message.error('Fail to delete exam');
    }
  }
};
