import { REGEXP } from '@/constants/regExp';

// 형식에 맞는 경우 true 리턴

// 이메일 유효성 검사
export const checkEmail = (data) => {
  return REGEXP.EMAIL_REGEXP.test(data);
};

// 비밀번호 유효성 검사
export const checkPassword = (data) => {
  return REGEXP.PW_REGEXP.test(data);
};

// 이름 유효성 검사
export const checkName = (data) => {
  return REGEXP.NAME_REGEXP.test(data);
};

// 핸드폰번호 유효성 검사
export const checkPhonenumber = (data) => {
  return REGEXP.CALL_REGEXP.test(data);
};

// 핸드폰번호 자동 - 입력
export const formatPhoneNumber = (value) => {
  const phoneNumber = value.replace(/\D/g, ''); // 숫자만 남기고 나머지 문자 제거
  // 전화번호를 포맷에 맞게 변경
  const formattedPhoneNumber = phoneNumber.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
  return formattedPhoneNumber;
};
