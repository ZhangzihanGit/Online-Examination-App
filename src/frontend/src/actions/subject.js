import * as api from '../api/subject';
import { push } from 'connected-react-router';
import { GET_SUBJECT, GET_SUBJECT_LIST } from '../constants/actions';

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