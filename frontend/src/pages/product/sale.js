import { useState } from 'react';
import {
  Box,
  Button,
  CssBaseline,
  TextField,
  Container,
  ThemeProvider,
  InputLabel,
  Select,
  MenuItem,
  Typography,
} from '@mui/material';
import theme from '@/styles/theme';

export default function Sale() {
  const [carSaleData, setCarSaleData] = useState({
    carNumber: '', // 차량번호 입력 필드
    garageSelection: '', // 차고지 선택 필드
  });

  const [setCarNumberError, setGarageError] = useState(false);

  const handleSubmit = (event) => {
    event.preventDefault();

    // 차량 번호 필드가 빈 경우 에러를 표시
    if (carSaleData.carNumber.trim() === '') {
      setCarNumberError(true);
    } else {
      setCarNumberError(false);
    }

    // 차고지 선택이 빈 경우 에러를 표시
    if (carSaleData.garageSelection.trim() === '') {
      setGarageError(true);
    } else {
      setGarageError(false);
    }

    // applyForCarSale(carSaleData)
    //   .then((response) => {
    //     // 성공적으로 처리된 후에 수행할 작업을 추가합니다.
    //     console.log('차량 판매 신청이 성공적으로 완료되었습니다.', response);
    //   })
    //   .catch((error) => {
    //     // 오류 처리
    //     console.error('차량 판매 신청 중 오류가 발생했습니다.', error);
    //   });
  };

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="md" sx={{ mt: 8, mb: 6 }}>
        <CssBaseline />
        <Box
          display="flex"
          flexDirection="column"
          alignItems="center"
          justifyContent="center"
        >
          <div style={{ height: '35px' }}></div>
          <Typography variant="h4" sx={{ fontWeight: 'bold', textAlign: 'center' }}>
            내 차 판매 신청서 작성
          </Typography>
          <div style={{ height: '60px' }}></div>
          <Box
            display="flex"
            flexDirection="column"
            alignItems="flex-start"
            width="60%"
            sx={{ mb: 2 }}
          >
            <InputLabel htmlFor="carNumber" sx={{ fontSize: '1.2rem', mb: -0.5 }}>
              차량번호
            </InputLabel>
            <TextField
              margin="normal"
              required
              fullWidth
              id="carNumber"
              label="차량번호를 입력해주세요"
              name="carNumber"
              autoComplete="off"
              autoFocus
              value={carSaleData.carNumber}
              onChange={(e) =>
                setCarSaleData({ ...carSaleData, carNumber: e.target.value })
              }
            />
          </Box>
          <div style={{ height: '20px' }}></div> 
          <Box
            display="flex"
            flexDirection="column"
            alignItems="flex-start"
            width="60%"
            sx={{ mb: 2 }}
          >
            <InputLabel htmlFor="garageSelection" sx={{ fontSize: '1.2rem' }}>
              차고지 선택
            </InputLabel>
            <Box sx={{ mb: 1.3 }}></Box>
            <Select
              fullWidth
              value={carSaleData.garageSelection}
              onChange={(e) =>
                setCarSaleData({ ...carSaleData, garageSelection: e.target.value })
              }
              displayEmpty
              inputProps={{ 'aria-label': 'Without label' }}
              sx={{
                '& .MuiSelect-select': {
                  '&:focus': {
                    background: 'transparent',
                  },
                  color: carSaleData.garageSelection ? 'black' : 'gray',
                },
              }}
            >
              <MenuItem value="" disabled>
                <em>차고지 선택</em>
              </MenuItem>
              <MenuItem value="차고지1">차고지1</MenuItem>
              <MenuItem value="차고지2">차고지2</MenuItem>
              <MenuItem value="차고지3">차고지3</MenuItem>
            </Select>
          </Box>
          <Box
            display="flex"
            justifyContent="flex-end"
            width="60%"
            sx={{ mt: 1, mb: 9 }}
          >
            <Button
              type="submit"
              variant="contained"
              onClick={handleSubmit}
            >
              신청하기
            </Button>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
