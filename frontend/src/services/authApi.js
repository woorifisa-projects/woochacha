// utils/authApi.js
import LocalStorage from '@/utils/localStorage';
import { authInstance, jsonInstance } from '@/utils/api';
import Swal from 'sweetalert2';

export const signupApi = async (signupData, setUserLoginState, router) => {
  try {
    const response = await jsonInstance.post('/users/register', signupData);

    console.log(response.data);

    if (response.data.code === 0) {
      const loginData = {
        email: signupData.email,
        password: signupData.password,
      };

      Swal.fire({
        icon: 'success',
        title: `회원가입 완료`,
        html: `회원가입이 완료되었습니다.`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      }).then(() => {
        loginApi(loginData, setUserLoginState, router);
      });
    }

    if (response.data.code !== 0) {
      if (response.data.code === -1) {
        Swal.fire({
          icon: 'error',
          title: '회원가입 실패',
          html: '잘못된 입력이 있습니다. 다시 시도해주세요.',
          showConfirmButton: false,
          timer: 1500,
        });
        return;
      }
      if (response.data.code === 1) {
        Swal.fire({
          icon: 'error',
          title: '회원가입 실패',
          html: '핸드폰 번호가 중복되었습니다. 다른 번호로 회원가입해주세요.',
          showConfirmButton: false,
          timer: 1500,
        });
        return;
      }
      if (response.data.code === 2) {
        Swal.fire({
          icon: 'error',
          title: '회원가입 실패',
          html: '이메일 주소가 중복되었습니다. 다른 이메일로 회원가입해주세요.',
          showConfirmButton: false,
          timer: 1500,
        });
        return;
      }
      if (response.data.code === 3) {
        Swal.fire({
          icon: 'error',
          title: '회원가입 실패',
          html: '기존의 정지된 이력이 있는 계정은 다시 회원 가입할 수 없습니다.',
          showConfirmButton: false,
          timer: 1500,
        });
        return;
      }

      return;
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
      Swal.fire({
        icon: 'success',
        title: `로그인 완료`,
        html: `로그인이 완료되었습니다.`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      });
      router.push('/');
    }

    if (!loginToken || response.data.code !== 1) {
      console.log(response.data.code);
      if (response.data.code === 2) {
        Swal.fire({
          icon: 'error',
          title: '로그인 실패',
          html: `${response.data.msg}입니다. 다시 로그인해주세요!`,
          showConfirmButton: false,
          showClass: {
            popup: 'animate__animated animate__fadeInDown',
          },
          hideClass: {
            popup: 'animate__animated animate__fadeOutUp',
          },
          timer: 1500,
        });
        return;
      }
      Swal.fire({
        icon: 'error',
        title: '로그인 실패',
        html: `${response.data.msg}입니다.`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      });
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

// export const confirmTokenApi = async () => {};

/**
 * [인증/인가 - logout] 회원 로그아웃 (POST)
 */
export const logoutApi = async (memberId) => {
  try {
    const response = await authInstance.post(`/users/logout`, null, {
      params: {
        memberId: memberId,
      },
    });

    return response;
  } catch (error) {
    console.log('실패: ', error);
    throw error;
  }
};

/**
 * [인증/인가 - signout] 회원 탈퇴 (PATCH)
 */
export const signoutApi = async (memberId) => {
  try {
    const response = await authInstance.patch(`/users/signout`, null, {
      params: {
        memberId: memberId,
      },
    });
    return response;
  } catch (error) {
    console.log('실패: ', error);
    throw error;
  }
};

export const sendAuthApi = async (phoneNum) => {
  try {
    const data = {
      phone: phoneNum,
    };
    const response = await jsonInstance.post(`/users/auth`, data);
    console.log(response);
    if (response.status === 200) {
      alert('인증 번호를 전송하였습니다.');
    }
  } catch (error) {
    console.log('실패: ', error);
    throw error;
  }
};
export const checkAuthApi = async (phoneNum, authNum) => {
  try {
    const data = {
      phone: phoneNum,
      authNum: authNum,
    };
    const response = await jsonInstance.patch(`/users/auth`, data);
    console.log(response);
    if (response.data === 'success') {
      Swal.fire({
        icon: 'success',
        title: `인증 완료`,
        html: `전화번호 인증이 완료되었습니다.`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      });
      return response.data;
    } else {
      Swal.fire({
        icon: 'error',
        title: '인증번호 실패',
        html: '인증번호가 일치하지 않습니다.',
        showConfirmButton: false,
        timer: 1500,
      });
      return response.data;
    }
  } catch (error) {
    console.log('실패: ', error);
    throw error;
  }
};
