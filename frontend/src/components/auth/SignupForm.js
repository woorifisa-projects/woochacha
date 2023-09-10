import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';
import { useRouter } from 'next/router';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import { signupApi } from '@/services/authApi';

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
    event.preventDefault();

    const data = new FormData(event.currentTarget);

    const newSignupData = {
      email: data.get('email').trim(),
      password: data.get('password').trim(),
      phone: data.get('phoneNum').replace(/-/g, ''),
      name: data.get('name').trim(),
    };

    setSignupData(newSignupData);

    // 비밀번호 확인 유효성 검사
    if (!validatePasswordConfirmation(newSignupData.password)) {
      SwalModals('error', '비밀번호 불일치', '비밀번호와 비밀번호 확인이 같지 않습니다.', false);
      return;
    }

    setClickSubmit((prev) => !prev);
    signupApi(newSignupData, setUserLoginState, router);
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
            }}>
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
                <Grid item xs={12}>
                  <InputLabel htmlFor="phoneNum" sx={{ fontSize: '1.2rem', my: 1 }}>
                    전화번호
                  </InputLabel>
                  <TextField
                    onBlur={(event) =>
                      handlePhoneBlur(event, setFormValid, formValid, setSignupData, signupData)
                    }
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
                  />
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
