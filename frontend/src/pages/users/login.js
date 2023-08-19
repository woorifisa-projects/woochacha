import { useState } from 'react';
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
import { checkEmail, checkPassword } from '@/hooks/useChecks';

export default function SignIn() {
  const [emailVaild, setEmailVaild] = useState(false);
  const [pwVaild, setPwVaild] = useState(false);

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    setEmailVaild(!checkEmail(data.get('email')));
    setPwVaild(!checkPassword(data.get('password')));

    // TODO: 로그인 관련 form 데이터 보내주기
    console.log({
      email: data.get('email'),
      password: data.get('password'),
    });
  };

  const handleBlur = (e) => {
    if (e.target.type.includes('email')) {
      setEmailVaild(!checkEmail(e.target.value));
      return;
    }
    if (e.target.type.includes('password')) {
      setPwVaild(!checkPassword(e.target.value));
      return;
    }
  };

  return (
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
              onBlur={(e) => handleBlur(e)}
              margin="normal"
              required
              fullWidth
              id="email"
              label="이메일을 입력해주세요"
              name="email"
              autoComplete="email"
              type="email"
              autoFocus
              error={emailVaild}
            />
            <InputLabel htmlFor="password" sx={{ fontSize: '1.2rem', my: 1 }}>
              비밀번호
            </InputLabel>
            <TextField
              onBlur={(e) => handleBlur(e)}
              margin="normal"
              required
              fullWidth
              name="password"
              label="비밀번호를 입력해주세요"
              type="password"
              id="password"
              autoComplete="current-password"
              error={pwVaild}
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="이메일 기억하기"
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
  );
}
