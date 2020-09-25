import * as api from '../api/subject';
import { push } from 'connected-react-router';
import {
  GET_SUBJECT,
  GET_SUBJECT_LIST,
  GET_EXAM,
  PUBLISH_EXAM,
  CLOSE_EXAM,
} from '../constants/actions';
import { message } from 'antd';

export function getSubjectList(payload = {}) {
  return async (dispatch) => {
    const result = await api.getSubjectList(payload);
    console.log(result);

    // TODO: if get subjects successfully
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
    console.log(222, payload);
    const result = await api.getSubject(payload);
    console.log(223, result);

    // TODO: if get subjects successfully
    if (true) {
      dispatch({
        type: GET_SUBJECT,
        payload: result.data,
      });
      dispatch(push(pathname));
    }
  }
};

export function getExam(payload = {}, pathname) {
  return async (dispatch) => {
    console.log(payload)
    const result = await api.getExam(payload);
    console.log(99999999999, result);

    // TODO: if get subjects successfully
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
    console.log(payload)
    const result = await api.submitExam(payload);
    console.log(result);

    // TODO: if get subjects successfully
    // if (result.status === 200) {
    //   dispatch({
    //     type: GET_EXAM,
    //     payload: result.data,
    //   });
    //   dispatch(push(pathname));
    // }
  }
};

export function publishExam(payload = {}) {
  return async (dispatch) => {
    console.log(payload)
    const result = await api.publishExam(payload);
    console.log(result);

    // TODO: if publish exam successfully
    if (result.status === 200) {
      dispatch({
        type: PUBLISH_EXAM,
        payload: payload,
      });
      message.success('Publish exam successfully');
    } else {
      message.success('Fail to publish exam');
    }
  }
};

export function closeExam(payload = {}) {
  return async (dispatch) => {
    console.log(payload)
    const result = await api.closeExam(payload);
    console.log(result);

    // TODO: if close exam successfully
    if (result.status === 200) {
      dispatch({
        type: CLOSE_EXAM,
        payload: payload,
      });
      message.success('Close exam successfully');
    } else {
      message.success('Fail to close exam');
    }
  }
};

export function createExam(payload = {}) {
  return async (dispatch) => {
    console.log(payload)
    const result = await api.createExam(payload);
    console.log(result);
  }
};
