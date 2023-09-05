import { useEffect, useState } from 'react';
import { checkFormValidate, handleSignupBlur } from '@/hooks/useChecks';
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
} from '@mui/material';
import theme from '@/styles/theme';

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

  /**
   * mounted
   */
  useEffect(() => {
    setMouted(true);
  }, []);

  /**
   * 유효성 검사
   */
  useEffect(() => {
    if (
      signupData.email !== '' ||
      signupData.password !== '' ||
      signupData.phone !== '' ||
      signupData.name !== ''
    ) {
      const validationResult = checkFormValidate(signupData);
      setFormValid(validationResult);
    }
  }, [signupData]);

  const handleInputBlur = (event) => {
    handleSignupBlur(event, setFormValid, formValid);
  };

  // TODO: 데이터 넘기기
  useEffect(() => {
    if (!Object.values(formValid).includes(true) && !Object.values(signupData).includes('')) {
      console.log(signupData);
      signupApi(signupData, setUserLoginState, router);
    }
  }, [clickSubmit]);

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
    setClickSubmit((prev) => !prev);
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
                    onBlur={handleInputBlur}
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
                    onBlur={handleInputBlur}
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
                    onBlur={handleInputBlur}
                    required
                    fullWidth
                    name="password-confirm"
                    label="비밀번호를 확인해주세요"
                    type="password"
                    id="password-confirm"
                  />
                </Grid>
                <Grid item xs={12}>
                  <InputLabel htmlFor="phoneNum" sx={{ fontSize: '1.2rem', my: 1 }}>
                    이름
                  </InputLabel>
                  <TextField
                    onBlur={handleInputBlur}
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
                    onBlur={handleInputBlur}
                    required
                    fullWidth
                    id="phoneNum"
                    label="전화번호를 입력해주세요"
                    name="phoneNum"
                    type="tel"
                    autoComplete="tel"
                    error={formValid.phoneErr}
                  />
                </Grid>

                <Grid item xs={12}>
                  <FormControlLabel
                    control={<Checkbox value="allowExtraEmails" color="primary" />}
                    label="약관 내용 들어옴"
                  />
                </Grid>
              </Grid>
              <Button type="submit" fullWidth variant="contained" sx={{ my: 3 }}>
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
