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
import LocalStorage from '@/utils/localStorage';
import Swal from 'sweetalert2';

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
  const [rememberEmail, setRememberEmail] = useState(false);
  const [disabledCheckBox, setDisabledCheckBox] = useState(true);
  const [disabledSubmitBtn, setDisabledSubmitBtn] = useState(false);

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
      setRememberEmail(true);
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

  /**
   * 이메일 미입력 시 - 체크박스 비활성화
   */
  useEffect(() => {
    if (loginData.email !== '') {
      setDisabledCheckBox(false);
    } else {
      setDisabledCheckBox(true);
    }
  }, [loginData.email]);

  const handleInputBlur = (event) => {
    handleSignupBlur(event, setFormValid, formValid);
  };

  /**
   * login api 호출
   */
  useEffect(() => {
    if (!Object.values(formValid).includes(true) && !Object.values(loginData).includes('')) {
      if (!disabledSubmitBtn) {
        // 버튼이 비활성화된 경우에만 API 호출
        loginApi(loginData, setUserLoginState, router)
          .then((res) => {
            if (res === 'success') {
              setDisabledSubmitBtn(true); // 성공적으로 로그인한 경우 버튼 비활성화
            }
          })
          .catch((error) => {
            console.error(error);
          });
      }
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

    if (
      !formValid.emailErr &&
      !formValid.pwErr &&
      newLoginData.email.trim().length !== 0 &&
      newLoginData.password.trim().length !== 0
    ) {
      setLoginData(newLoginData);
      setClickSubmit((prev) => !prev);
    } else {
      Swal.fire({
        icon: 'error',
        title: `로그인 실패!`,
        html: `로그인 입력값을 모두 작성해주세요!`,
        showConfirmButton: false,
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        timer: 1500,
      });
    }
  };

  /**
   * 이메일 기억하기 함수
   */
  const handleRemember = (event) => {
    if (event.target.checked) {
      LocalStorage.setItem('rememberEmail', loginData.email); // "이메일 기억하기" 이메일 저장
      setRememberEmail(true);
    } else {
      LocalStorage.removeItem('rememberEmail'); // "이메일 기억하기" 이메일 삭제
      setRememberEmail(false);
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
                onChange={(e) => setLoginData({ ...loginData, password: e.target.value })}
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
                checked={rememberEmail}
                disabled={disabledCheckBox}
              />

              <Button
                type="submit"
                fullWidth
                variant="contained"
                disabled={disabledSubmitBtn}
                sx={{ mt: 3, mb: 2 }}>
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
