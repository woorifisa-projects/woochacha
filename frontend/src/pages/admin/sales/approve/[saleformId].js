import { useEffect, useState } from 'react';
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
import { oneApproveFormGetApi, oneApproveFormPatchApi } from '@/services/adminpageApi';
import { SwalModals } from '@/utils/modal';

function AdminSalesApproveForm(props) {
  const [mounted, setMounted] = useState(false);
  const [approveSaleForm, setApproveSaleForm] = useState(); // 기존 qldb 값
  const [defaultDistance, setDefaultDistance] = useState(); // 기존 주행거리(유효성 검사용)

  const router = useRouter();
  const { saleformId } = props;

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

  // Modal 버튼 클릭 유무
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);

  let responsiveFontTheme = responsiveFontSizes(theme);

  /**
   * 승인 modal 창 열기 전, 주행거리 확인
   */
  const handleOpenModal = () => {
    if (approveSaleForm.carDistance < defaultDistance) {
      SwalModals('error', '입력 오류', '기존 주행거리보다 작을 수 없습니다!', false).then(() => {
        router.push('/admin/sales');
        return;
      });
    } else {
      handleClickModal();
    }
  };

  /**
   * 승인 요청
   */
  const handleSubmit = () => {
    const newApproveData = {
      distance: approveSaleForm.carDistance,
      carAccidentInfoDto: {
        accidentType: accidentVal.accidentType,
        accidentDesc: accidentVal.accidentDesc,
        accidentDate: accidentVal.accidentDate,
      },
      carExchangeInfoDtoList: {
        exchangeType: exchangeVal.exchangeType,
        exchangeDesc: exchangeVal.exchangeDesc,
        exchangeDate: exchangeVal.exchangeDate,
      },
    };

    console.log(newApproveData);

    oneApproveFormPatchApi(saleformId, newApproveData).then((res) => {
      console.log(res);
      if (res.status === 200 && res.data === true) {
        SwalModals(
          'success',
          '점검 정보 승인',
          '점검 정보가 승인되었습니다. 차량 게시글을 등록으로 이동합니다.',
          false,
        ).then(() => {
          router.replace(`/admin/sales/register/${saleformId}`);
          return;
        });
      }
      if (res.status === 200 && res.data === false) {
        SwalModals('error', '점검 정보 승인 오류', '잘못된 요청입니다. 다시 확인해주세요.', false);
        return;
      }
    });
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
      exchangeDate: e.target.value,
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
    if (e.target.value.trim().length > 0) {
      setAccidentVal({
        ...accidentVal,
        accidentDesc: e.target.value,
      });
    }
  };

  // [사고유형] date box func
  const handleAccidentDateChange = (e) => {
    setAccidentVal({
      ...accidentVal,
      accidentDate: e.target.value,
    });
  };

  // [주행거리] 주행거리 수정
  const handleChangeDistance = (e) => {
    e.target.value = e.target.value.replace(/[^0-9]/g, '');
    setApproveSaleForm({
      ...approveSaleForm,
      carDistance: e.target.value,
    });
  };

  // qldb data 초기 불러오기
  saleformId &&
    useEffect(() => {
      oneApproveFormGetApi(saleformId).then((res) => {
        console.log(res);
        if (res.status === 200) {
          setApproveSaleForm(res.data);
          setDefaultDistance(res.data.carDistance);
        } else {
          SwalModals('error', '데이터 없음', '데이터가 없습니다!', false);
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
            <Box noValidate>
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
                    value={approveSaleForm.carDistance}
                    onInput={handleChangeDistance}
                    type="text"
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
                    <MenuItem value="교통사고">교통사고</MenuItem>
                    <MenuItem value="침수사고">침수사고</MenuItem>
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
                    onChange={handleAccidentDescChange}
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
                    onChange={handleAccidentDateChange}
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
                    {approveSaleForm.exchangeTypeList.map((exchangeItem) => {
                      return (
                        <MenuItem key={exchangeItem.id} value={exchangeItem.type}>
                          {exchangeItem.type}
                        </MenuItem>
                      );
                    })}
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
                    onChange={handleExchangeDescChange}
                    id="exchangeDesc"
                  />
                </Grid>
                <Grid item xs={12}>
                  <InputLabel htmlFor="exchangeDate" sx={saleApproveFormCss.inputLabel}>
                    교체일
                  </InputLabel>
                  <TextField
                    fullWidth
                    name="exchangeDate"
                    id="exchangeDate"
                    type="date"
                    onChange={handleExchangeDateChange}
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

export async function getServerSideProps(context) {
  const saleformId = context.params.saleformId;
  return {
    props: {
      saleformId,
    },
  };
}
