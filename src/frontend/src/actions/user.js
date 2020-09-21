import { message } from 'antd';
import * as api from '../api/user';
import { LOGIN } from '../constants/actions';

export function login(payload = {}) {
  return async (dispatch) => {
    // TODO:
    // const result = await api.login(payload);
    // the result should contain the user identity
    // console.log(result);

    console.log(payload);

    // TODO: authenticate the user
    if (true) {
      dispatch({
        type: LOGIN,
        payload: payload,
      });
    }
    message.success(`login success!`);
  }
}