import axios from 'axios';
import LocalStorage from './localStorage';
import Swal from 'sweetalert2';

// const router = useRouter();
const BASE_URL = 'https://server.web.back.woochacha.store';
// const BASE_URL = 'http://localhost:8080';
const HEADER_JSON = {
  headers: {
    'Content-Type': 'application/json',
  },
};
const HEADER_AUTH_FORM = {
  'Content-Type': 'multipart/form-data',
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

// 인증 필요 X 경우
const axiosApi2 = (url, options) => {
  const instance = axios.create({
    baseURL: url,
    headers: {
      ...options,
    },
  });
  return instance;
};

// // 인증 필요 O 경우
// const axiosAuthApi = (url, options) => {
//   const token = LocalStorage.getItem('loginToken');
//   const instance = axios.create({
//     baseURL: url,
//     headers: {
//       Authorization: 'Bearer ' + token,
//       ...options,
//     },
//   });
//   console.log(token);
//   return instance;
// };

export const jsonInstance = axiosApi(BASE_URL, HEADER_JSON);
export const formInstance = axiosApi(BASE_URL, HEADER_FORM);
export const authInstance = axiosApi2(BASE_URL);
export const authFormInstance = axiosApi2(BASE_URL, HEADER_AUTH_FORM);

/**
 * 1. 요청 인터셉터
 */
authInstance.interceptors.request.use(
  (config) => {
    const token = LocalStorage.getItem('loginToken');
    try {
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }

      return config;
    } catch (err) {
      console.error('[_axios.interceptors.request] config : ' + err);
    }
    return config;
  },
  (error) => {
    // 요청 에러 직전 호출됩니다.
    return Promise.reject(error);
  },
);

authFormInstance.interceptors.request.use(
  (config) => {
    const token = LocalStorage.getItem('loginToken');
    try {
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }

      return config;
    } catch (err) {
      console.error('[_axios.interceptors.request] config : ' + err);
    }
    return config;
  },
  (error) => {
    // 요청 에러 직전 호출됩니다.
    return Promise.reject(error);
  },
);

/**
 * 2. 응답 인터셉터
 */

// jsonInstance
jsonInstance.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    // 오류 응답을 처리
    console.log(error.response.status);
    if (error.response.status === 401) {
      Swal.fire({
        icon: 'error',
        title: `잘못된 접근`,
        html: `잘못된 접근입니다. 다시 접속해주세요!`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      }).then(() => {
        window.location.href = '/users/login';
        return;
      });
    }
    if (error.response.status === 403) {
      Swal.fire({
        icon: 'error',
        title: `잘못된 접근`,
        html: `잘못된 접근입니다. 다시 접속해주세요!`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      }).then(() => {
        window.location.href = '/users/login';
        return;
      });
    }
    if (error.response.status === 500) {
      Swal.fire({
        icon: 'error',
        title: `잘못된 접근`,
        html: `잘못된 접근입니다. 다시 접속해주세요!`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      }).then(() => {
        window.location.href = '/users/login';
        return;
      });
    }
    return Promise.reject(error);
  },
);

// formInstance
formInstance.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    console.log(error.response.status);
    if (error.response.status === 401) {
      Swal.fire({
        icon: 'error',
        title: `잘못된 접근`,
        html: `잘못된 접근입니다. 다시 접속해주세요!`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      }).then(() => {
        window.location.href = '/users/login';
        return;
      });
    }
    if (error.response.status === 403) {
      Swal.fire({
        icon: 'error',
        title: `잘못된 접근`,
        html: `잘못된 접근입니다. 다시 접속해주세요!`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      }).then(() => {
        window.location.href = '/users/login';
        return;
      });
    }
    if (error.response.status === 500) {
      Swal.fire({
        icon: 'error',
        title: `잘못된 접근`,
        html: `잘못된 접근입니다. 다시 접속해주세요!`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      }).then(() => {
        window.location.href = '/users/login';
        return;
      });
    }
    return Promise.reject(error);
  },
);

// authInstance
authInstance.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    // 오류 응답을 처리
    console.log(error.response.status);
    if (error.response.status === 401) {
      LocalStorage.removeItem('loginToken');
      LocalStorage.removeItem('recoil-persist');

      Swal.fire({
        icon: 'error',
        title: `잘못된 접근`,
        html: `기간이 만료되었거나 잘못된 접근입니다. 다시 로그인해주세요!`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      }).then(() => {
        window.location.href = '/users/login';
        return;
      });
    }
    if (error.response.status === 403) {
      Swal.fire({
        icon: 'error',
        title: `잘못된 접근`,
        html: `잘못된 접근입니다. 다시 접속해주세요!`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      }).then(() => {
        window.location.href = '/users/login';
        return;
      });
    }
    if (error.response.status === 500) {
      Swal.fire({
        icon: 'error',
        title: `잘못된 접근`,
        html: `잘못된 접근입니다. 다시 접속해주세요!`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      }).then(() => {
        window.location.href = '/users/login';
        return;
      });
    }
    return Promise.reject(error);
  },
);

// authFormInstance
authFormInstance.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    // 오류 응답을 처리
    console.log(error.response.status);
    if (error.response.status === 401) {
      LocalStorage.removeItem('loginToken');
      LocalStorage.removeItem('recoil-persist');

      Swal.fire({
        icon: 'error',
        title: `잘못된 접근`,
        html: `기간이 만료되었거나 잘못된 접근입니다. 다시 로그인해주세요!`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      }).then(() => {
        window.location.href = '/users/login';
        return;
      });
    }
    if (error.response.status === 403) {
      Swal.fire({
        icon: 'error',
        title: `잘못된 접근`,
        html: `잘못된 접근입니다. 다시 접속해주세요!`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      }).then(() => {
        window.location.href = '/users/login';
        return;
      });
    }
    if (error.response.status === 500) {
      Swal.fire({
        icon: 'error',
        title: `잘못된 접근`,
        html: `잘못된 접근입니다. 다시 접속해주세요!`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      }).then(() => {
        window.location.href = '/users/login';
        return;
      });
    }
    return Promise.reject(error);
  },
);
