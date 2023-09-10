import { useEffect, useState } from 'react';
import { checkLoginValidate, handleSignupBlur } from '@/hooks/useChecks';
import { loginApi } from '@/services/authApi';
import { useRecoilState } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import { useRouter } from 'next/router';

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

export default function LoginForm() {
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);
  const [mounted, setMounted] = useState(false);
  const router = useRouter();

  const [clickSubmit, setClickSubmit] = useState(true);
  const [loginData, setLoginData] = useState({
    email: '',
    password: '',
  });

  const [formValid, setFormValid] = useState({
    emailErr: false,
    pwErr: false,
  });

  /**
   * 렌더링
   */
  // 컴포넌트가 마운트될 때 저장된 이메일을 가져와서 이메일 필드에 설정
  useEffect(() => {
    const rememberedEmail = localStorage.getItem('rememberEmail');
    if (rememberedEmail) {
      setLoginData((prev) => ({
        ...prev,
        email: rememberedEmail,
      }));
    }
    setMounted(true);
  }, []);

  /**
   * 유효성 검사
   */
  useEffect(() => {
    if (loginData.email !== '' || loginData.password !== '') {
      const validationResult = checkLoginValidate(loginData);
      setFormValid(validationResult);
    }
  }, [loginData]);

  const handleInputBlur = (event) => {
    handleSignupBlur(event, setFormValid, formValid);
  };

  /**
   * login api 호출
   */
  useEffect(() => {
    if (!Object.values(formValid).includes(true) && !Object.values(loginData).includes('')) {
      loginApi(loginData, setUserLoginState, router);
    }
  }, [clickSubmit]);

  /**
   * 로그인 form 제출
   */
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    const newLoginData = {
      email: data.get('email').trim(),
      password: data.get('password').trim(),
    };
    setLoginData(newLoginData);
    setClickSubmit((prev) => !prev);
  };

  /**
   * 이메일 기억하기 함수
   */
  const handleRemember = (event) => {
    if (event.target.checked) {
      localStorage.setItem('rememberEmail', loginData.email); // "이메일 기억하기" 이메일 저장
    } else {
      localStorage.removeItem('rememberEmail'); // "이메일 기억하기" 이메일 삭제
    }
  };

  return (
    mounted && (
      <ThemeProvider theme={theme}>
        <Container component="main" maxWidth="xs">
          <CssBaseline />
          <Box
            sx={{
              my: 11,
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
            }}>
            <Typography component="h1" variant="h4" sx={{ fontWeight: 'bold' }}>
              로그인
            </Typography>
            <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
              <InputLabel htmlFor="email" sx={{ fontSize: '1.2rem', my: 1 }}>
                이메일
              </InputLabel>
              <TextField
                onBlur={handleInputBlur}
                onChange={(e) => setLoginData({ ...loginData, email: e.target.value })}
                margin="normal"
                required
                fullWidth
                id="email"
                label="이메일을 입력해주세요"
                name="email"
                autoComplete="email"
                type="email"
                autoFocus
                error={formValid.emailErr}
                value={loginData.email}
              />
              <InputLabel htmlFor="password" sx={{ fontSize: '1.2rem', my: 1 }}>
                비밀번호
              </InputLabel>
              <TextField
                onBlur={handleInputBlur}
                margin="normal"
                required
                fullWidth
                name="password"
                label="비밀번호를 입력해주세요"
                type="password"
                id="password"
                autoComplete="current-password"
                error={formValid.pwErr}
              />
              <FormControlLabel
                control={<Checkbox name="remember" color="primary" onChange={handleRemember} />}
                label="이메일 기억하기"
                // 체크박스 상태를 localStorage에서 가져와 설정
                checked={!!localStorage.getItem('rememberEmail')}
              />

              <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                로그인
              </Button>
              <Grid container>
                <Grid item xs>
                  <span>아직 회원이 아니신가요?</span>
                  <Link sx={{ ml: 2 }} href="/users/signup" variant="body2">
                    회원 가입하기
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
