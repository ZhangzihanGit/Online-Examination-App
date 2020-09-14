import axios from 'axios';

const getAxiosInstance = (option) => {
  const instance = axios.create();
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