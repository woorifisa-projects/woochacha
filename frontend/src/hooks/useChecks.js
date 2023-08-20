import { checkPhonenumber, checkPassword, checkEmail, checkName } from '@/utils/validate';

/**
 * 회원가입  form 유효성 검사
 */
export const checkFormValidate = (signupData) => {
  return {
    emailErr: !checkEmail(signupData.email),
    pwErr: !checkPassword(signupData.password),
    phoneErr: !checkPhonenumber(signupData.phone),
    nameErr: !checkName(signupData.name),
  };
};

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
