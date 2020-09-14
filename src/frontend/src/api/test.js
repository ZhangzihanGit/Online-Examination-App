import ajax from '../utils/ajax';

// In dev environment remove: /online_examination_war_exploded
// also remove package.json: "homepage": "http://localhost:8080/online_examination_war_exploded",
export const getTest = (params) => {
  return ajax.get('/getLogin', { data: params });
}