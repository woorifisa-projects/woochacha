// utils/authApi.js
import LocalStorage from '@/utils/localStorage';
import { jsonInstance } from '@/utils/api';

export const signupApi = async (signupData, setUserLoginState, router) => {
  try {
    const response = await jsonInstance.post('/users/register', signupData);

    console.log(response.data);

    if (response.data.code === 0) {
      const loginData = {
        email: signupData.email,
        password: signupData.password,
      };
      console.log('회원가입 되었습니다!');
      loginApi(loginData, setUserLoginState, router);
    }

    if (response.data.code !== 0) {
      console.log(response.data.code);
      alert(`잘못된 입력이 있습니다. 다시 시도해주세요.`);
      return;
      /*
      if (response.data.code === -1) {
        alert(`잘못된 입력이 있습니다. 다시 시도해주세요.`);
        return;
      }
      if (response.data.code === 1) {
        alert(`핸드폰 번호가 중복되었습니다. 다른 번호로 회원가입해주세요.`);
        return;
      }
      if (response.data.code === 2) {
        alert(`이메일 주소가 중복되었습니다. 다른 이메일로 회원가입해주세요.`);
        return;
      }
      if (response.data.code === 3) {
        alert(`기존의 정지된 이력이 있는 계정은 다시 회원 가입할 수 없습니다.`);
        router.push('/');
        return;
      }
      */
    }
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

export const loginApi = async (loginData, setUserLoginState, router) => {
  try {
    const response = await jsonInstance.post('/users/login', loginData);

    const loginToken = response.data.token;

    if (loginToken && response.data.code === 1) {
      LocalStorage.setItem('loginToken', loginToken);
      setUserLoginState({
        loginStatus: true,
        userId: response.data.id,
        userName: response.data.name,
      });
      console.log('로그인 성공');
      router.push('/');
    }

    if (!loginToken || response.data.code !== 1) {
      console.log(response.data.code);
      if (response.data.code === 2) {
        alert(`${response.data.msg}입니다. 다시 로그인해주세요!`);
        router.push('/');
        return;
      }
      alert(`${response.data.msg}입니다.`);
      router.push('/');
    }
  } catch (error) {
    console.log('실패 : ', error);
    if (error.response) {
      // 2xx의 범위를 벗어나는 상태 코드로 응답
      console.log(`${error.response.data} | ${error.response.status} | ${error.response.headers}`);
    } else if (error.request) {
      // 요청 O - 응답을 X
      console.log(error.request);
    } else {
      // 오류를 발생시킨 요청을 설정하는 중에 문제 O
      console.log('Error', error.message);
    }
    console.log(error.config);
    throw error;
  }
};

export const confirmTokenApi = async () => {};
