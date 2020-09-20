import ajax from '../utils/ajax';
import { RELATIVE_PATH } from '../constants';

// In dev environment remove: ${RELATIVE_PATH}
// also remove package.json: "homepage": "http://localhost:8080/online_examination_war_exploded",
export const getTest = (params) => {
  return ajax.get(`/getLogin`, { data: params });
}