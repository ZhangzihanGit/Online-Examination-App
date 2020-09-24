import * as api from '../api/subject';
import { push } from 'connected-react-router';
import { GET_SUBJECT, GET_SUBJECT_LIST, GET_EXAM } from '../constants/actions';

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
    const result = await api.getSubject(payload);
    console.log(result);

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
    console.log(result);

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

export function createExam(payload = {}) {
  return async (dispatch) => {
    console.log(payload)
    const result = await api.createExam(payload);
    console.log(result);
  }
};
