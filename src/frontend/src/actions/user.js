import { message } from 'antd';
import { push } from 'connected-react-router';
import * as api from '../api/user';
import { LOGIN, LOGOUT, GET_INSTRUCTOR_LIST, GET_STUDENT_LIST } from '../constants/actions';

export function login(payload = {}) {
  return async (dispatch) => {
    const { data, status } = await api.login(payload);

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

export function getAllInstructors(payload = {}) {
  return async (dispatch) => {
    console.log(payload)
    const response = await api.getAllInstructors(payload);
    console.log(response)

    if (response.status === 200) {
      dispatch({
        type: GET_INSTRUCTOR_LIST,
        payload: response.data.instructorList,
      });
      message.success(response.data.message);
    } else {
      message.error(response.data.message);
    }
  };
};