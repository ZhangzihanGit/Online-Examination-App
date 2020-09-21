import * as api from '../api/user';
import { DELETE_SUBJECT } from '../constants/actions';

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
}