import { REGEXP } from '@/constants/RegExp';

// 형식에 맞는 경우 true 리턴

// 핸드폰번호 유효성 검사
export const checkPhonenumber = (data) => {
  return REGEXP.CALL_REGEXP.test(data);
};

//비밀번호 유효성 검사
export const checkPassword = (data) => {
  return REGEXP.PW_REGEXP.test(data);
};

// 이메일 유효성 검사
export const checkEmail = (data) => {
  return REGEXP.EMAIL_REGEXP.test(data);
};
