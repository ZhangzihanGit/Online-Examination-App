import { message } from 'antd';
import * as api from '../api/user';
import { LOGIN } from '../constants/actions';

export function login(payload = {}) {
  return async (dispatch) => {
    // TODO:
    const { data, status } = await api.login(payload);
    // the result should contain the user identity
    console.log(data);

    // authenticate the user
    if (status === 200) {
      message.success(data.message);
      dispatch({
        type: LOGIN,
        payload: data,
      });

    } else {
      message.error('Fail to login!');
    }
  };
};