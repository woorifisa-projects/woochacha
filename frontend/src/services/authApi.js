// utils/authApi.js
import LocalStorage from '@/utils/localStorage';
import { jsonInstance } from '@/utils/api';

export const signupApi = async (signupData, setUserLoginState, router) => {
  try {
    const response = await jsonInstance.post('/users/register', signupData);

    const loginToken = response.headers['Authorization'];
    LocalStorage.setItem('loginToken', loginToken);
    console.log(loginToken);
    console.log(response.data);
    setUserLoginState({
      loginStatus: true,
      userId: response.data.id,
      userName: response.data.name,
    });
    console.log('로그인 성공');
    router.push('/');
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

export const loginApi = async (loginData, setUserLoginState, router) => {
  try {
    const response = await jsonInstance.post('/users/login', loginData);

    const loginToken = response.headers.authorization;
    LocalStorage.setItem('loginToken', loginToken);
    console.log(response);
    console.log(loginToken);
    console.log(response.data);
    setUserLoginState({
      loginStatus: true,
      userId: response.data.id,
      userName: response.data.name,
    });
    console.log('로그인 성공');
    router.push('/');
  } catch (error) {
    console.log('실패 : ', error);
    if (error.response) {
      // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
      console.log(error.response.data);
      console.log(error.response.status);
      console.log(error.response.headers);
    } else if (error.request) {
      // 요청이 이루어 졌으나 응답을 받지 못했습니다.
      // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
      // Node.js의 http.ClientRequest 인스턴스입니다.
      console.log(error.request);
    } else {
      // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
      console.log('Error', error.message);
    }
    console.log(error.config);
    throw error;
  }
};

export const confirmTokenApi = async () => {};
