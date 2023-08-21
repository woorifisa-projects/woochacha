import { jsonInstance } from '@/utils/api';

export const signupApi = async (signupData) => {
  console.log(signupData);
  jsonInstance
    .post('/users/register', JSON.stringify(signupData))
    .then((res) => {
      console.log('성공 : ', res.data);
    })
    .catch((err) => {
      console.log('실패 : ', err);
    });
};

export const loginApi = async (loginData) => {
  console.log(loginData);
  jsonInstance
    .post('/users/login', JSON.stringify(loginData))
    .then((res) => {
      console.log('성공 : ', res.data);
    })
    .catch((err) => {
      console.log('실패 : ', err);
    });
};
