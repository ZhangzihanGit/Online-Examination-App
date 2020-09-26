import { message } from 'antd';
import { push } from 'connected-react-router';
import * as api from '../api/user';
import { LOGIN, LOGOUT } from '../constants/actions';

export function login(payload = {}) {
  return async (dispatch) => {
    const { data, status } = await api.login(payload);
    console.log(data);

    // authenticate the user
    if (status === 200) {
      dispatch({
        type: LOGIN,
        payload: data,
      });
      message.success(data.message);
      dispatch(push('/dashboard'));
    } else {
      message.error('Fail to login!');
    }
  };
};

export function logout(payload = {}) {
  return async (dispatch) => {
    // TODO:
    // const { data, status } = await api.logout(payload);
    // the result should contain the user identity
    // console.log(data);

    // clear the Redux store
    dispatch({
      type: LOGOUT,
      payload: {},
    });
    dispatch(push('/'));
  };
};