import {
  checkPhonenumber,
  checkPassword,
  checkEmail,
  checkName,
  formatPhoneNumber,
} from '@/utils/validate';

/**
 * 회원가입  form 유효성 검사
 */
// 이메일 필드 onBlur 이벤트 핸들러
export const handleEmailBlur = (event, setFormValid, formValid) => {
  const email = event.target.value;
  const isValid = checkEmail(email);
  setFormValid({ ...formValid, emailErr: !isValid });
};

// 비밀번호 필드 onBlur 이벤트 핸들러
export const handlePasswordBlur = (event, setFormValid, formValid) => {
  const password = event.target.value;
  const isValid = checkPassword(password);
  setFormValid({ ...formValid, pwErr: !isValid });
};

// 이름 필드 onBlur 이벤트 핸들러
export const handleNameBlur = (event, setFormValid, formValid) => {
  const name = event.target.value;
  const isValid = checkName(name);
  setFormValid({ ...formValid, nameErr: !isValid });
};

// 전화번호 필드 onBlur 이벤트 핸들러
export const handlePhoneBlur = (event, setFormValid, formValid, setSignupData, signupData) => {
  const phoneNumber = event.target.value;
  const formattedPhoneNumber = formatPhoneNumber(phoneNumber);
  setSignupData({ ...signupData, phone: formattedPhoneNumber });
  const isValid = checkPhonenumber(formattedPhoneNumber);
  setFormValid({ ...formValid, phoneErr: !isValid });
};

/// ==== ///

/**
 * 로그인 form 유효성 검사
 */
export const checkLoginValidate = (loginData) => {
  return {
    emailErr: !checkEmail(loginData.email),
    pwErr: !checkPassword(loginData.password),
  };
};

export const handleSignupBlur = (e, setFormValid, formValid) => {
  if (e.target.type.includes('email')) {
    setFormValid({
      ...formValid,
      emailErr: !checkEmail(e.target.value),
    });
    return;
  }
  if (e.target.type.includes('password')) {
    setFormValid({
      ...formValid,
      pwErr: !checkPassword(e.target.value),
    });
    return;
  }
  if (e.target.type.includes('tel')) {
    setFormValid({
      ...formValid,
      phoneErr: !checkPhonenumber(e.target.value),
    });
    return;
  }
  if (e.target.id.includes('name')) {
    setFormValid({
      ...formValid,
      nameErr: !checkName(e.target.value),
    });
    return;
  }
};
