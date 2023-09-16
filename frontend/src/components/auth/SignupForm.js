import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';
import { useRouter } from 'next/router';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import { signupApi, sendAuthApi, checkAuthApi } from '@/services/authApi';
import {
  Button,
  CssBaseline,
  TextField,
  FormControlLabel,
  Checkbox,
  Link,
  Grid,
  Box,
  Typography,
  Container,
  ThemeProvider,
  InputLabel,
  Accordion,
  AccordionSummary,
  AccordionDetails,
} from '@mui/material';
import theme from '@/styles/theme';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { SwalModals } from '@/utils/modal';
import { formatPhoneNumber } from '@/utils/validate';
import {
  handleEmailBlur,
  handleNameBlur,
  handlePasswordBlur,
  handlePhoneBlur,
} from '@/hooks/useChecks';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import Swal from 'sweetalert2';
import { debounce } from 'lodash';

const Timer = ({ mm, ss }) => {
  const [minutes, setMinutes] = useState(parseInt(mm));
  const [seconds, setSeconds] = useState(parseInt(ss));

  return (
      <div>
        {minutes}:{seconds < 10 ? `0${seconds}` : seconds}
      </div>
  );
};

export default function SignupForm() {
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);
  const [mounted, setMouted] = useState();
  // const [userInfo, setUserInfo] = useRecoilState(userInfoState);
  const router = useRouter();

  const [clickSubmit, setClickSubmit] = useState(true);
  const [signupData, setSignupData] = useState({
    email: '',
    password: '',
    phone: '',
    name: '',
  });
  const [formValid, setFormValid] = useState({
    emailErr: false,
    pwErr: false,
    phoneErr: false,
    nameErr: false,
  });

  // 비밀번호 확인
  const [passwordConfirm, setPasswordConfirm] = useState({
    passwordConfirm: '',
  });

  // 약관동의 확인
  const [agreeTerms, setAgreeTerms] = useState(false);
  const [phoneAgree, setPhoneAgree] = useState(false);
  const [checkAgree, setCheckAgree] = useState(true);
  const [successMessage, setSuccessMessage] = useState(false);
  const [showVerification, setShowVerification] = useState(false);
  const [countdown, setCountdown] = useState(0);
  const [intervalId, setIntervalId] = useState(null); // intervalId 상태 추가
  const [verificationCode, setVerificationCode] = useState('');

  const handleVerificationCodeChange = (event) => {
    const code = event.target.value.replace(/[^0-9]/g, '');
    setVerificationCode(code);
  };
  const isButtonDisabled = verificationCode.length !== 6;

  const handleCheckboxChange = () => {
    setPhoneAgree(!phoneAgree);
  };

  const handleSendVerification = debounce(() => {
    //console.log(signupData.phone.replace(/-/g, ''));
    sendAuthApi(signupData.phone.replace(/-/g, ''));
    setShowVerification(true);
    if (intervalId) {
      clearInterval(intervalId); // 기존의 타이머 정리
    }
    startCountdownTimer(); // 새로운 타이머 시작
  }, 400);
  const startCountdownTimer = () => {
    const endTime = new Date().getTime() + 3 * 60 * 1000;

    const id = setInterval(() => {
      // intervalId 대신 id 변수 사용
      const currentTime = new Date().getTime();
      const remainingTime = endTime - currentTime;

      if (remainingTime <= 0) {
        setShowVerification(false);
        setCountdown(0);

        clearInterval(id); // id 변수 사용

        setIntervalId(null); // intervalId 상태 초기화
      } else {
        const minutes = Math.floor((remainingTime % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((remainingTime % (1000 * 60)) / 1000);

        setCountdown(`${minutes}분 ${seconds < 10 ? `0${seconds}` : seconds}초`);
      }
    }, 1000);

    setIntervalId(id); // intervalId 상태 업데이트
  };
  const handleVerify = () => {
    checkAuthApi(signupData.phone.replace(/-/g, ''), verificationCode).then((data) => {
      if (data === 'success') {
        setPhoneAgree(false);
        setShowVerification(false);
        setCheckAgree(false);
        setSuccessMessage(true);
      } else {
        setVerificationCode('');
      }
    });
  };

  useEffect(() => {
    return () => {
      if (intervalId) {
        clearInterval(intervalId); // cleanup 함수에서 이전의 타이머 정리
      }
    };
  }, []);

  /**
   * mounted
   */
  useEffect(() => {
    setMouted(true);
  }, []);

  /**
   * 비밀번호 확인 일치 여부 확인
   */
  const validatePasswordConfirmation = (pw) => {
    if (pw !== passwordConfirm.passwordConfirm) {
      setFormValid({ ...formValid, pwErr: true });
      return false;
    }
    return true;
  };

  /**
   * 휴대폰 번호 포맷 변경
   */
      // 전화번호 변경 핸들러
  const handlePhoneNumChange = (event) => {
        const formattedPhoneNumber = formatPhoneNumber(event.target.value);
        setSignupData({ ...signupData, phone: formattedPhoneNumber });
      };

  /**
   * 회원가입 form 제출
   */
  const handleSubmit = (event) => {
    setAgreeTerms(false);
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const newSignupData = {
      email: data.get('email').trim(),
      password: data.get('password').trim(),
      phone: signupData.phone.replace(/-/g, ''),
      name: data.get('name').trim(),
    };

    // 비밀번호 확인 유효성 검사
    if (!validatePasswordConfirmation(newSignupData.password)) {
      SwalModals('error', '비밀번호 불일치', '비밀번호와 비밀번호 확인이 같지 않습니다.', false);
      setAgreeTerms(true);
      return;
    }
    if (
        !formValid.emailErr &&
        !formValid.nameErr &&
        !formValid.phoneErr &&
        !formValid.pwErr &&
        successMessage &&
        newSignupData.email !== '' &&
        newSignupData.name !== '' &&
        newSignupData.password !== '' &&
        newSignupData.phone !== ''
    ) {
      setSignupData(newSignupData);
      setClickSubmit((prev) => !prev);
      signupApi(newSignupData, setUserLoginState, router);
    } else {
      console.log(formValid);
      console.log(successMessage);
      console.log(newSignupData);
      Swal.fire({
        icon: 'error',
        title: `회원가입 실패!`,
        html: `회원정보 작성 및 핸드폰 인증을 완료해주세요!`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      });
      setAgreeTerms(true);
    }
  };

  return (
      mounted && (
          <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
              <CssBaseline />
              <Box
                  sx={{
                    marginY: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                  }}
                  disabled={true}>
                <Typography component="h1" variant="h4" sx={{ fontWeight: 'bold' }}>
                  회원 가입
                </Typography>
                <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
                  <Grid container spacing={2}>
                    <Grid item xs={12}>
                      <InputLabel htmlFor="email" sx={{ fontSize: '1.2rem', my: 1 }}>
                        이메일
                      </InputLabel>
                      <TextField
                          onBlur={(event) => handleEmailBlur(event, setFormValid, formValid)}
                          required
                          fullWidth
                          id="email"
                          label="이메일을 입력해주세요"
                          name="email"
                          autoComplete="email"
                          type="email"
                          autoFocus
                          error={formValid.emailErr}
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <InputLabel htmlFor="password" sx={{ fontSize: '1.2rem', my: 1 }}>
                        비밀번호
                      </InputLabel>
                      <TextField
                          onBlur={(event) => handlePasswordBlur(event, setFormValid, formValid)}
                          required
                          fullWidth
                          name="password"
                          label="비밀번호를 입력해주세요"
                          type="password"
                          id="password"
                          autoComplete="new-password"
                          error={formValid.pwErr}
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <InputLabel htmlFor="password-confirm" sx={{ fontSize: '1.2rem', my: 1 }}>
                        비밀번호 확인
                      </InputLabel>
                      <TextField
                          onBlur={(event) => handlePasswordBlur(event, setFormValid, formValid)}
                          required
                          fullWidth
                          name="password-confirm"
                          label="비밀번호를 확인해주세요"
                          type="password"
                          id="password-confirm"
                          error={formValid.pwErr}
                          value={passwordConfirm.passwordConfirm}
                          onChange={(e) => setPasswordConfirm({ passwordConfirm: e.target.value })}
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <InputLabel htmlFor="phoneNum" sx={{ fontSize: '1.2rem', my: 1 }}>
                        이름
                      </InputLabel>
                      <TextField
                          onBlur={(event) => handleNameBlur(event, setFormValid, formValid)}
                          required
                          fullWidth
                          id="name"
                          label="이름을 입력해주세요"
                          name="name"
                          type="text"
                          error={formValid.nameErr}
                      />
                    </Grid>
                    <Grid container item xs={12} justifyContent="space-between" alignItems="center">
                      <Grid item xs={4}>
                        <InputLabel htmlFor="phoneNum" sx={{ fontSize: '1.2rem', my: 1 }}>
                          전화번호
                        </InputLabel>
                      </Grid>
                      <Grid item xs={8}>
                        <div
                            style={{ display: 'flex', justifyContent: 'flex-end', alignItems: 'center' }}>
                          <Checkbox
                              value="allowExtraEmails"
                              color="primary"
                              disabled={!checkAgree}
                              onChange={() => setPhoneAgree(!phoneAgree)}
                          />
                          <Typography variant="body1">개인정보 이용에 동의합니다.</Typography>
                        </div>
                      </Grid>
                      <Grid container spacing={2} alignItems="center">
                        <Grid item xs={8.5}>
                          <TextField
                              onBlur={(event) => {
                                handlePhoneBlur(
                                    event,
                                    setFormValid,
                                    formValid,
                                    setSignupData,
                                    signupData,
                                );
                              }}
                              required
                              fullWidth
                              id="phoneNum"
                              label="전화번호를 입력해주세요"
                              name="phoneNum"
                              type="tel"
                              autoComplete="tel"
                              error={formValid.phoneErr}
                              value={signupData.phone}
                              onChange={handlePhoneNumChange}
                              disabled={!phoneAgree}
                          />
                        </Grid>
                        <Grid item xs={3.5}>
                          <Button
                              variant="contained"
                              onClick={handleSendVerification}
                              disabled={!phoneAgree}>
                            {showVerification ? '인증번호 재전송' : '인증번호 전송'}
                          </Button>
                        </Grid>
                      </Grid>
                    </Grid>
                    <Grid container item xs={12} alignItems="center">
                      {/* 애니메이션으로 나타나는 인증번호 입력 필드 */}
                      {showVerification && (
                          <Grid container spacing={2} alignItems="center">
                            <Grid item xs={8.5}>
                              <TextField
                                  disabled={!phoneAgree}
                                  fullWidth
                                  id="verificationCode"
                                  label="인증번호 입력"
                                  name="verificationCode"
                                  type="text"
                                  autoComplete="off"
                                  value={verificationCode}
                                  onChange={handleVerificationCodeChange}
                                  InputProps={{
                                    inputProps: {
                                      maxLength: 6, // 최대 길이 제한
                                    },
                                    endAdornment: (
                                        <Typography style={{ color: 'red', whiteSpace: 'nowrap' }}>
                                          {countdown}
                                        </Typography>
                                    ),
                                    style: { display: 'flex', alignItems: 'center' },
                                  }}
                              />
                            </Grid>
                            <Grid item xs={3.5}>
                              <Button
                                  variant="contained"
                                  color="primary"
                                  fullWidth
                                  disabled={isButtonDisabled}
                                  onClick={handleVerify}>
                                인증 확인
                              </Button>
                            </Grid>
                          </Grid>
                      )}
                      {successMessage && (
                          <Grid display="flex" justifyContent="center" alignItems="center">
                            <CheckCircleOutlineIcon color="success" />
                            <Typography disabled={!successMessage} color="green" ml={1}>
                              전화번호 인증에 성공하였습니다.
                            </Typography>
                          </Grid>
                      )}
                    </Grid>
                    <Grid item xs={12}>
                      {/* 약관 내용 */}
                      <Accordion sx={{ my: 3 }}>
                        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                          <Typography>약관 동의서</Typography>
                        </AccordionSummary>
                        <AccordionDetails>
                          <div>
                            <Typography>
                              {`본 약관은 (주)우차차(이하 “회사”라 합니다)이 운영하는 웹사이트 ‘우차차’
                          (www.woochacha.store) (이하 “웹사이트”라 합니다)에서 제공하는 온라인
                          서비스(이하 “서비스”라 한다)를 이용함에 있어 사이버몰과 이용자의 권리,
                          의무 및 책임사항을 규정함을 목적으로 합니다.`}
                            </Typography>
                          </div>
                        </AccordionDetails>
                      </Accordion>
                      {/* 약관 동의 버튼 */}
                      <FormControlLabel
                          control={<Checkbox value="allowExtraEmails" color="primary" />}
                          label="약관에 동의합니다."
                          checked={agreeTerms}
                          onChange={() => setAgreeTerms(!agreeTerms)}
                      />
                    </Grid>
                  </Grid>
                  <Button
                      type="submit"
                      fullWidth
                      variant="contained"
                      sx={{ my: 3 }}
                      disabled={!agreeTerms} // 약관 비동의 - 버튼 비활성화
                  >
                    회원가입
                  </Button>
                  <Grid container>
                    <Grid item>
                      <span>이미 저희 회원이신가요?</span>
                      <Link sx={{ ml: 2 }} href="/users/login" variant="body2">
                        로그인하기
                      </Link>
                    </Grid>
                  </Grid>
                </Box>
              </Box>
            </Container>
          </ThemeProvider>
      )
  );
}