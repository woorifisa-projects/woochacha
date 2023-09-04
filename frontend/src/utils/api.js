import axios from 'axios';
import LocalStorage from './localStorage';
import { Router } from 'next/router';

const BASE_URL = 'http://13.125.32.208:8080';
// const BASE_URL = 'http://localhost:8080';
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

// CORS: 리소스 접근 허용
axios.defaults.headers['Access-Control-Allow-Origin'] = '*';

// CORS: 서로 다른 도메인간 쿠키 전달 허용
axios.defaults.withCredentials = true;

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

// axios interceptor - error handling
/*
axios.interceptors.response.use(
  function (res) {
    return res;
  },
  function (err) {
    if (err.response && err.response.status) {
      switch (err.response.status) {
        // status code가 401인 경우 `/users/login` 페이지로 리다이렉트
        case 401:
          Router.push('users/login').catch(() => {});
          return new Promise(() => {});
        // status code 403인 경우 `/` 페이지로 리다이렉트
        case 403:
          Router.push('/').catch(() => {});
          return new Promise(() => {});
        default:
          return Promise.reject(err);
      }
    }
  },
);
*/

export const jsonInstance = axiosApi(BASE_URL, HEADER_JSON);
export const formInstance = axiosApi(BASE_URL, HEADER_FORM);
export const authInstance = axiosAuthApi(BASE_URL);
