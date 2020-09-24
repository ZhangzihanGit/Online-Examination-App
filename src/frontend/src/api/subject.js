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

export const getSubjectList = (params) => {
  return ajax.get(`/all-subjects`, { params });
};

export const getSubject = (params) => {
  return ajax.get(`/subject`, { params });
};

export const deleteSubject = (params) => {
  return ajax.post(`/delete`, { data: params });
};

export const createExam = (params) => {
  return ajax.post(`/add-exam`, { data: params });
};