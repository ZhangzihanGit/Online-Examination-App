import * as api from '../api/subject';
import { DELETE_SUBJECT, GET_SUBJECT_LIST } from '../constants/actions';

export function getSubjectList(payload = {}) {
  return async (dispatch) => {
    // TODO:
    const result = await api.getSubjectList(payload);
    console.log(result);

    console.log(result.data.data);

    // TODO: if get subjects successfully
    if (true) {
      dispatch({
        type: GET_SUBJECT_LIST,
        payload: result.data.data,
      });
    }
  }
}

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