import { message } from 'antd';
import { push } from 'connected-react-router';
import * as api from '../api/user';
import { LOGIN, LOGOUT, GET_INSTRUCTOR_LIST, GET_STUDENT_LIST } from '../constants/actions';

export function login(payload = {}) {
  return async (dispatch) => {
    try {
      const { data } = await api.login(payload);
      dispatch({
        type: LOGIN,
        payload: data,
      });
      message.success(data.message);
      dispatch(push('/dashboard'));
    } catch (error) {
      const { message: msg } = error.response.data;
      message.error(msg);
    }
  };
};

export function logout(payload = {}) {
  return async (dispatch) => {
    console.log(payload)
    try {
      const { data, status } = await api.logout(payload);
      // clear the Redux store
      dispatch({
        type: LOGOUT,
        payload: {},
      });
      dispatch(push('/'));
    } catch (error) {
      console.log('Logout error')
    } 
  };
};

export function getAllInstructors(payload = {}) {
  return async (dispatch) => {
    try {
      const response = await api.getAllInstructors(payload);
      dispatch({
        type: GET_INSTRUCTOR_LIST,
        payload: response.data.instructorList,
      });
    } catch (error) {
      message.error('Fail to fetch all instructors');
    }
  };
};

export function getAllStudents(payload = {}) {
  return async (dispatch) => {
    try {
      const response = await api.getAllStudents(payload);
      dispatch({
        type: GET_STUDENT_LIST,
        payload: response.data.studentList,
      });
    } catch (error) {
      message.error('Fail to fetch all students');
    }
  };
};