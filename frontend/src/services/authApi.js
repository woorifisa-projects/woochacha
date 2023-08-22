// utils/authApi.js
import LocalStorage from '@/utils/localStorage';
import { jsonInstance } from '@/utils/api';

export const signupApi = async (signupData, setLoginToken, setUserInfo, router) => {
  try {
    const response = await jsonInstance.post('/users/register', JSON.stringify(signupData));

    const loginToken = response.headers['authorization'];
    LocalStorage.setItem('loginToken', loginToken);
    setLoginToken(loginToken);
    setUserInfo(response.data);
    console.log('로그인 성공');
    router.push('/');
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

export const loginApi = async (loginData, setLoginToken, setUserInfo, router) => {
  try {
    const response = await jsonInstance.post('/users/login', JSON.stringify(loginData));

    const loginToken = response.headers['authorization'];
    LocalStorage.setItem('loginToken', loginToken);
    setLoginToken(loginToken);
    setUserInfo(response.data);
    console.log('로그인 성공');
    router.push('/');
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};
