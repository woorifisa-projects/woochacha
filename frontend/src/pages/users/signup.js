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
import { checkPhonenumber, checkPassword, checkEmail } from '@/hooks/useChecks';

export default function SignUp() {
  const [emailVaild, setEmailVaild] = useState(false);
  const [pwVaild, setPwVaild] = useState(false);
  const [phoneVaild, setPhoneVaild] = useState(false);

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    setEmailVaild(!checkEmail(data.get('email')));
    setPwVaild(!checkPassword(data.get('password')));
    setPhoneVaild(!checkPhonenumber(data.get('phoneNum')));

    // TODO: 회원가입 관련 form 데이터 보내주기
    console.log({
      email: data.get('email'),
      password: data.get('password'),
      phone: data.get('phoneNum'),
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
    setPhoneVaild(!checkPhonenumber(e.target.value));
  };

  return (
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
                  onBlur={(e) => handleBlur(e)}
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
              </Grid>
              <Grid item xs={12}>
                <InputLabel htmlFor="password" sx={{ fontSize: '1.2rem', my: 1 }}>
                  비밀번호
                </InputLabel>
                <TextField
                  onBlur={handleBlur}
                  required
                  fullWidth
                  name="password"
                  label="비밀번호를 입력해주세요"
                  type="password"
                  id="password"
                  autoComplete="new-password"
                  error={pwVaild}
                />
              </Grid>
              <Grid item xs={12}>
                <InputLabel htmlFor="password-confirm" sx={{ fontSize: '1.2rem', my: 1 }}>
                  비밀번호 확인
                </InputLabel>
                <TextField
                  onBlur={handleBlur}
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
                  전화번호
                </InputLabel>
                <TextField
                  onBlur={handleBlur}
                  required
                  fullWidth
                  id="phoneNum"
                  label="전화번호를 입력해주세요"
                  name="phoneNum"
                  type="tel"
                  autoComplete="tel"
                  error={phoneVaild}
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
  );
}
