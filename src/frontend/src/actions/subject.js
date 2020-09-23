import * as api from '../api/subject';
import { push } from 'connected-react-router';
import { DELETE_SUBJECT, GET_SUBJECT_LIST } from '../constants/actions';

export function getSubjectList(payload = {}) {
  return async (dispatch) => {
    console.log(payload)
    const result = await api.getSubjectList(payload);
    console.log(result);

    // console.log(result.data.data);

    // TODO: if get subjects successfully
    if (true) {
      dispatch({
        type: GET_SUBJECT_LIST,
        payload: result.data.data,
      });
    }
  }
};

export function getSubject(payload = {}, pathname) {
  return async (dispatch) => {
    console.log(payload)
    console.log(pathname)
    const result = await api.getSubject(payload);
    console.log(result);

    // console.log(result.data.data);

    // TODO: if get subjects successfully
    if (true) {
      // dispatch({
      //   type: GET_SUBJECT_LIST,
      //   payload: result.data.data,
      // });
      dispatch(push(pathname));
    }
  }
};

export function deleteSubject(payload = {}) {
  return async (dispatch) => {
    // TODO:
    // const result = await api.deleteSubect(payload);
    // console.log(result);

    console.log(payload);

    // TODO: if delete successfully
    if (true) {
      dispatch({
        type: DELETE_SUBJECT,
        payload: payload,
      });
    }
  }
};