import axios from 'axios';
// import * as interceptors from './interceptors';

const getAxiosInstance = (option) => {
  const instance = axios.create();
  // interceptors.install(instance, option);
  return instance;
}

const makeGet = () => {
  return (url, option) => {
    const instance = getAxiosInstance(option);
    return instance({
      url,
      method: 'get',
      ...option,
    });
  }
};

const makePost = () => {
  return (url, option) => {
    const instance = getAxiosInstance(option);
    return instance({
      url,
      method: 'post',
      ...option,
    });
  }
};

export default {
  get: makeGet(),
  post: makePost(),
};