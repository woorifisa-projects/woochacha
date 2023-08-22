import axios from 'axios';
import LocalStorage from './localStorage';

const BASE_URL = 'http://localhost:8080';
const HEADER_JSON = {
  headers: {
    'Content-Type': 'application/json',
  },
};
const HEADER_FORM = {
  headers: {
    'Content-Type': 'multipart/form-data',
  },
};

// 인증 필요 X 경우
const axiosApi = (url, options) => {
  const instance = axios.create({ baseURL: url, ...options });
  return instance;
};

// 인증 필요 O 경우
const axiosAuthApi = (url, options) => {
  const token = LocalStorage.getItem('loginToken');
  const instance = axios.create({
    baseURL: url,
    headers: { Authorization: 'Bearer ' + token },
    ...options,
  });
  return instance;
};

export const jsonInstance = axiosApi(BASE_URL, HEADER_JSON);
export const formInstance = axiosApi(BASE_URL, HEADER_FORM);
export const authInstance = axiosAuthApi(BASE_URL);
