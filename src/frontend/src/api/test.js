import ajax from '../utils/ajax';

export const getTest = (params) => {
  return ajax.get('/login', { data: params });
}