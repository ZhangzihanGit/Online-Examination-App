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
  return ajax.get(`${RELATIVE_PATH}/all-subjects`, { params });
};

export const getSubject = (params) => {
  return ajax.get(`${RELATIVE_PATH}/subject`, { params });
};

export const createSubject = (params) => {
  return ajax.post(`${RELATIVE_PATH}/add-subject`, { data: params });
};

export const deleteSubject = (params) => {
  return ajax.post(`${RELATIVE_PATH}/delete`, { data: params });
};

export const getExam = (params) => {
  return ajax.get(`${RELATIVE_PATH}/get-exam`, { params });
};

export const startExam = (params) => {
  return ajax.get(`${RELATIVE_PATH}/start-exam`, { params });
};

export const createExam = (params) => {
  return ajax.post(`${RELATIVE_PATH}/add-exam`, { data: params });
};

export const updateExam = (params) => {
  return ajax.post(`${RELATIVE_PATH}/update-exam`, { data: params });
};

export const deleteExam = (params) => {
  return ajax.post(`${RELATIVE_PATH}/delete-exam`, { data: params });
};

export const submitExam = (params) => {
  return ajax.post(`${RELATIVE_PATH}/submission`, { data: params });
};

export const publishExam = (params) => {
  return ajax.post(`${RELATIVE_PATH}/publish-exam`, { data: params });
};

export const closeExam = (params) => {
  return ajax.post(`${RELATIVE_PATH}/close-exam`, { data: params });
};

export const getSubmissions = (params) => {
  return ajax.get(`${RELATIVE_PATH}/all-submissions`, { params });
};

export const submitMarks = (params) => {
  return ajax.post(`${RELATIVE_PATH}/mark-submit`, { data: params });
};