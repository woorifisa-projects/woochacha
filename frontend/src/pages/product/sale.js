import { useEffect, useState } from 'react';
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
import { getBranchApi, saleFormRequest } from '@/services/productApi';
import { useRecoilState } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import { useRouter } from 'next/router';
import { SwalModals } from '@/utils/modal';
import LoadingBar from '@/components/common/LoadingBar';

export default function Sale() {
  const [carSaleData, setCarSaleData] = useState({
    carNumber: '', // 차량번호 입력 필드
    garageSelection: '', // 차고지 선택 필드
  });
  const [branches, setBranches] = useState([]); // branch 목록을 저장할 state
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);
  const [disabledSubmitBtn, setDisabledSubmitBtn] = useState(false); // 버튼 비활성화 여부
  const [isSubmitting, setIsSubmitting] = useState(false); // 진행 중 상태 확인
  const memberId = userLoginState.userId;
  const router = useRouter();

  useEffect(() => {
    getBranchApi().then((res) => {
      if (res.status === 200) {
        setBranches(res.data);
      }
    });
  }, []); // 컴포넌트가 마운트될 때 한 번만 API 호출

  const handleSubmit = (event) => {
    event.preventDefault();

    // 이미 제출 요청이 진행 중인 경우
    if (isSubmitting) {
      return;
    }

    // 제출 요청 시작
    setIsSubmitting(true);

    const saleForm = {
      memberId: memberId,
      branchId: carSaleData.garageSelection,
      carNum: carSaleData.carNumber,
    };

    saleFormRequest(saleForm)
      .then((response) => {
        if (response.data === '차량 판매 신청이 성공적으로 완료되었습니다.') {
          setDisabledSubmitBtn(true);
          SwalModals('success', '차량 판매 신청 완료', response.data, false);
          router.push('/');
        } else {
          setDisabledSubmitBtn(false); // 차량판매 신청 실패 시, 재활성화
          SwalModals('error', '차량 판매 신청 실패', response.data, false);

          setCarSaleData({
            carNumber: '',
            garageSelection: '',
          });
        }
      })
      .catch((error) => {
        SwalModals('error', '차량 판매 신청 실패', '해당 차량은 없는 차량입니다.', false);
        setCarSaleData({
          carNumber: '',
          garageSelection: '',
        });
        SwalModals('error', '차량 판매 신청 실패', '차량 판매 신청 중 오류가 발생했습니다.', false);
        console.error('차량 판매 신청 중 오류가 발생했습니다.', error);

        setDisabledSubmitBtn(false); // 차량 판매 오류 발생 시, 버튼 재활성화
      })
      .finally(() => {
        setIsSubmitting(false); // 요청 종료
      });
  };

  return branches ? (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="md" sx={{ mt: 8, mb: 6 }}>
        <CssBaseline />
        <Box display="flex" flexDirection="column" alignItems="center" justifyContent="center">
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
            sx={{ mb: 2 }}>
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
              onChange={(e) => setCarSaleData({ ...carSaleData, carNumber: e.target.value })}
            />
          </Box>
          <div style={{ height: '20px' }}></div>
          <Box
            display="flex"
            flexDirection="column"
            alignItems="flex-start"
            width="60%"
            sx={{ mb: 2 }}>
            <InputLabel htmlFor="garageSelection" sx={{ fontSize: '1.2rem' }}>
              차고지 선택
            </InputLabel>
            <Box sx={{ mb: 1.3 }}></Box>
            <Select
              fullWidth
              value={carSaleData.garageSelection}
              onChange={(e) => setCarSaleData({ ...carSaleData, garageSelection: e.target.value })}
              displayEmpty
              inputProps={{ 'aria-label': 'Without label' }}
              sx={{
                '& .MuiSelect-select': {
                  '&:focus': {
                    background: 'transparent',
                  },
                  color: carSaleData.garageSelection ? 'black' : 'gray',
                },
              }}>
              <MenuItem value="" disabled>
                <em>차고지 선택</em>
              </MenuItem>
              {branches.map((branch) => (
                <MenuItem key={branch.id} value={branch.id}>
                  {branch.name}
                </MenuItem>
              ))}
            </Select>
          </Box>
          <Box display="flex" justifyContent="flex-end" width="60%" sx={{ mt: 1, mb: 9 }}>
            <Button
              type="submit"
              variant="contained"
              onClick={handleSubmit}
              disabled={disabledSubmitBtn}>
              신청하기
            </Button>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  ) : (
    <LoadingBar />
  );
}
