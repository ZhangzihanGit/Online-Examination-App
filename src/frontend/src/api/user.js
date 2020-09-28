import ajax from '../utils/ajax';
import { RELATIVE_PATH } from '../constants';

/**
 * In frontend development environment that needs to fetch data from server,
 * remove: ${RELATIVE_PATH}.
 * 
 * If need to build bundle and run it in server or in production, then add ${RELATIVE_PATH}, 
 * also add below in package.json:
 * "homepage": "http://localhost:8080/online_examination_war_exploded",
 */
export const login = (params) => {
  return ajax.post(`/login`, { data: params });
};

export const getAllInstructors = (params) => {
  return ajax.post(`/all-instructors`, { data: params });
};

export const getAllStudents = (params) => {
  return ajax.post(`/all-students`, { data: params });
};