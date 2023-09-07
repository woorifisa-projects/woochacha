// TODO: 수정하기
import { useEffect, useRef, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import {
  Box,
  Button,
  Card,
  Container,
  CssBaseline,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  TextField,
  ThemeProvider,
  Typography,
  responsiveFontSizes,
} from '@mui/material';
import { useRouter } from 'next/router';
import withAdminAuth from '@/hooks/withAdminAuth';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import theme from '@/styles/theme';
import { ADMIN_APPROVE_MODAL } from '@/constants/string';
import OneButtonModal from '@/components/common/OneButtonModal';
import { oneApproveFormGetApi } from '@/services/adminpageApi';

function AdminSalesApproveForm() {
  const [mounted, setMounted] = useState(false);
  const [approveSaleForm, setApproveSaleForm] = useState(); // 기존 qldb 값
  const [defaultDistance, setDefaultDistance] = useState(); // 기존 주행거리(유효성 검사용)

  // 교체부위 select box 관련
  const [exchangeVal, setExchangeVal] = useState({
    exchangeType: '',
    exchangeDesc: '',
    exchangeDate: '',
  });

  // 사고유형 select box 관련
  const [accidentVal, setAccidentVal] = useState({
    accidentType: '',
    accidentDesc: '',
    accidentDate: '',
  });

  // 제출 시 넘길 data
  const [approveVal, setApproveVal] = useState({
    distance: null,
    carAccidentInfoDto: {
      accidentType: '',
      accidentDesc: '',
      accidentDate: '',
    },
    carExchangeInfoDtoList: {
      exchangeType: '',
      exchangeDesc: '',
      exchangeDate: '',
    },
  });

  // Modal 버튼 클릭 유무
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);
  const approveForm = useRef();
  const router = useRouter();
  const { saleformId } = router.query;

  let responsiveFontTheme = responsiveFontSizes(theme);

  /**
   * 승인 modal 창 열기 전, 주행거리 확인
   */
  const handleOpenModal = () => {
    if (approveSaleForm.carDistance < defaultDistance) {
      alert('기존 주행거리보다 작을 수 없습니다!');
      return;
    } else {
      handleClickModal();
    }
  };

  /**
   * 승인 요청
   */
  const handleSubmit = (event) => {
    // TODO: submit axios 연결 여기서

    router.push(`/admin/sales/register/${saleformId}`);
    /*
    event.preventDefault();
    const data = new FormData(approveForm.current);
    console.log(approveForm);
    console.log(data);

    const newApproveData = {
      distance: data.get('carDistance').trim(),
      // TODO: 교통사고 & 침수사고 둘 다 있을 경우, 어떤 식으로 데이터를 넘겨야하는지?
      carAccidentInfoDto: {
        accidentType: 'carAccident',
        accidentDesc: data.get('carAccidentDesc'),
        accidentDate: data.get('carAccidentDate').trim(),
      },
      carExchangeInfoDtoList: {
        exchangeType: exchangeVal.exchangeType,
        exchangeDesc: exchangeVal.exchangeDesc,
        exchangeDate: exchangeVal.exchangeDate,
      },
    };
    setApproveVal(newApproveData);
    handleClickModal();
    */
  };

  // [교체] select box func
  const handleExchangeChange = (e) => {
    setExchangeVal({
      ...exchangeVal,
      exchangeType: e.target.value,
    });
  };

  // [교체] input box func
  const handleExchangeDescChange = (e) => {
    setExchangeVal({
      ...exchangeVal,
      exchangeDesc: e.target.value,
    });
  };

  // [교체] date box func
  const handleExchangeDateChange = (e) => {
    setExchangeVal({
      ...exchangeVal,
      exchangeDesc: e.target.value,
    });
  };

  // [사고유형] select box func
  const handleAccidentChange = (e) => {
    setAccidentVal({
      ...accidentVal,
      accidentType: e.target.value,
    });
  };

  // [사고유형] input box func
  const handleAccidentDescChange = (e) => {
    setAccidentVal({
      ...accidentVal,
      accidentDesc: e.target.value,
    });
  };

  // [사고유형] date box func
  const handleAccidentDateChange = (e) => {
    setAccidentVal({
      ...accidentVal,
      accidentDesc: e.target.value,
    });
  };

  // 가격 수정
  const handleChangeDistance = (e) => {
    setApproveSaleForm({
      ...approveSaleForm,
      carDistance: e.target.value,
    });
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  saleformId &&
    useEffect(() => {
      oneApproveFormGetApi(saleformId).then((res) => {
        console.log(res);
        if (res.status === 200) {
          setApproveSaleForm(res.data);
          setDefaultDistance(res.data.carDistance);
        } else {
          alert('데이터가 없습니다!');
        }
      });
      setMounted(true);
    }, []);

  const saleApproveFormCss = {
    approveFormTitle: {
      my: 10,
      color: '#1490ef',
      fontWeight: 'bold',
    },
    titleCard: {
      py: 7,
      boxShadow: 5,
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      gap: '1rem',
      textAlign: 'center',
    },
    subTitleTypo: {
      display: 'inline',
    },
    colorTypo: {
      display: 'inline',
      color: '#1490ef',
      fontWeight: 'bold',
    },
    flexBox: {
      display: 'flex',
      alignItems: 'left',
      marginLeft: 0,
    },
    formContents: {
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'left',
      marginLeft: 0,
    },
    inputLabel: { fontSize: '1.2rem', my: 1 },
    submitBtn: {
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      my: 10,
    },
    historyCard: {
      padding: 5,
    },
  };

  return mounted && approveSaleForm ? (
    <ThemeProvider theme={responsiveFontTheme}>
      <CssBaseline />
      <Typography sx={saleApproveFormCss.approveFormTitle} component="h4" variant="h4" gutterBottom>
        판매 신청 관리
      </Typography>
      {/* subtitle card */}
      <Card sx={saleApproveFormCss.titleCard}>
        <Typography variant="h4" sx={saleApproveFormCss.colorTypo}>
          점검 정보 승인
        </Typography>
        <ArrowForwardIcon fontSize="large" />
        <Typography variant="h4" sx={saleApproveFormCss.subTitleTypo}>
          차량 게시글 등록
        </Typography>
      </Card>

      {/* contents */}
      <Container sx={saleApproveFormCss.formContents} maxWidth="md">
        {/* FORM CONTNETS */}
        <Typography component="h1" variant="h4" sx={saleApproveFormCss.approveFormTitle}>
          점검차량 정보 입력
        </Typography>
        <Grid container sx={saleApproveFormCss.flexBox} spacing={5}>
          <Grid item xs={12} md={6}>
            <Box ref={approveForm} noValidate>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <InputLabel htmlFor="carNum" sx={saleApproveFormCss.inputLabel}>
                    차량번호
                  </InputLabel>
                  <Typography variant="h5">{approveSaleForm.carNum}</Typography>
                </Grid>
                <Grid item xs={12}>
                  <InputLabel htmlFor="carOwnerName" sx={saleApproveFormCss.inputLabel}>
                    차량 소유주
                  </InputLabel>
                  <Typography variant="h5">{approveSaleForm.carOwnerName}</Typography>
                </Grid>
                <Grid item xs={12}>
                  <InputLabel htmlFor="carOwnerPhone" sx={saleApproveFormCss.inputLabel}>
                    차량 소유주 번호
                  </InputLabel>
                  <Typography variant="h5">{approveSaleForm.carOwnerPhone}</Typography>
                </Grid>
                <Grid item xs={12}>
                  <InputLabel htmlFor="carDistance" sx={saleApproveFormCss.inputLabel}>
                    주행거리
                  </InputLabel>
                  <TextField
                    fullWidth
                    id="carDistance"
                    label="주행거리를 입력해주세요"
                    name="carDistance"
                    defaultValue={approveSaleForm.carDistance}
                    onChange={handleChangeDistance}
                    type="number"
                  />
                </Grid>
                <Grid item xs={12}>
                  <InputLabel id="accidentType" sx={saleApproveFormCss.inputLabel}>
                    사고유형
                  </InputLabel>
                  <Select
                    fullWidth
                    labelId="accidentType"
                    id="accidentTypeLabelSelect"
                    value={accidentVal.accidentType}
                    label="accidentType"
                    onChange={handleAccidentChange}>
                    <MenuItem value={1}>교통사고</MenuItem>
                    <MenuItem value={2}>침수사고</MenuItem>
                  </Select>
                </Grid>
                <Grid item xs={12}>
                  <InputLabel htmlFor="accidentDesc" sx={saleApproveFormCss.inputLabel}>
                    사고
                  </InputLabel>
                  <TextField
                    fullWidth
                    name="accidentDesc"
                    id="accidentDesc"
                    label="사고 이력을 입력해주세요."
                  />
                </Grid>
                <Grid item xs={12}>
                  <InputLabel htmlFor="carAccidentDate" sx={saleApproveFormCss.inputLabel}>
                    사고 발생일
                  </InputLabel>
                  <TextField
                    fullWidth
                    name="carAccidentDate"
                    id="carAccidentDate"
                    type="date"
                    onChange={handleExchangeDescChange}
                  />
                </Grid>
                <Grid item xs={12}>
                  <InputLabel htmlFor="exchangeType" sx={saleApproveFormCss.inputLabel}>
                    교체부위
                  </InputLabel>
                  <Select
                    fullWidth
                    labelId="exchangeType"
                    id="exchangeTypeLabelSelect"
                    value={exchangeVal.exchangeType}
                    label="exchangeType"
                    onChange={handleExchangeChange}>
                    <MenuItem value={1}>백미러</MenuItem>
                    <MenuItem value={2}>앞문</MenuItem>
                    <MenuItem value={3}>뒷문</MenuItem>
                  </Select>
                </Grid>
                <Grid item xs={12}>
                  <InputLabel htmlFor="exchangeDesc" sx={saleApproveFormCss.inputLabel}>
                    교체사유
                  </InputLabel>
                  <TextField
                    fullWidth
                    name="exchangeDesc"
                    label="교체 사유를 입력해주세요."
                    id="exchangeDesc"
                  />
                </Grid>
              </Grid>
              <Grid sx={saleApproveFormCss.submitBtn}>
                <Button size="large" type="submit" variant="contained" onClick={handleOpenModal}>
                  승인 신청
                </Button>
              </Grid>
            </Box>
          </Grid>

          {/* HISTORY */}
          <Grid item xs={12} md={6}>
            <Grid container spacing={2}>
              <Card sx={saleApproveFormCss.historyCard}>
                <Grid item xs={12} py={2}>
                  <Typography variant="h5" fontWeight="bold">
                    {`${approveSaleForm.carNum} 차량의 history`}
                  </Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    사고이력 조회
                  </Typography>
                  {approveSaleForm.carAccidentInfoDtoList.map((accidentItem, idx) => {
                    return (
                      <Typography
                        key={
                          idx
                        }>{`[ ${accidentItem.accidentType} ] ${accidentItem.accidentDesc} ( ${accidentItem.accidentDate} )`}</Typography>
                    );
                  })}
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    교체이력 조회
                  </Typography>
                  {approveSaleForm.carExchangeInfoDtoList.map((exchangeItem, idx) => {
                    return (
                      <Typography
                        key={
                          idx
                        }>{`[ ${exchangeItem.exchangeType} ] ${exchangeItem.exchangeDesc} ( ${exchangeItem.exchangeDate} )`}</Typography>
                    );
                  })}
                </Grid>
              </Card>
            </Grid>
          </Grid>
        </Grid>
      </Container>
      {showModal && (
        <OneButtonModal
          onClickModal={handleClickModal}
          isOpen={showModal}
          modalContent={ADMIN_APPROVE_MODAL.CONTENTS}
          callBackFunc={handleSubmit}
        />
      )}
    </ThemeProvider>
  ) : (
    <>
      {/* TODO: 데이터 로딩 component 보여주기 */}
      <Typography>데이터 로딩중...</Typography>
    </>
  );
}

// side menu 레이아웃
AdminSalesApproveForm.Layout = withAdminAuth(AdminPageLayout);
export default AdminSalesApproveForm;
