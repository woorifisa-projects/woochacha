import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import {
  Box,
  Button,
  Card,
  Chip,
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
import theme from '@/styles/theme';
import { ADMIN_APPROVE_MODAL } from '@/constants/string';
import OneButtonModal from '@/components/common/OneButtonModal';
import { oneApproveFormGetApi, oneApproveFormPatchApi } from '@/services/adminpageApi';
import { SwalModals } from '@/utils/modal';
import LoadingBar from '@/components/common/LoadingBar';
import styles from './approve.module.css';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import CarCrashIcon from '@mui/icons-material/CarCrash';
import WaterDropOutlinedIcon from '@mui/icons-material/WaterDropOutlined';
import BuildIcon from '@mui/icons-material/Build';

function AdminSalesApproveForm(props) {
  const [mounted, setMounted] = useState(false);
  const [approveSaleForm, setApproveSaleForm] = useState(); // 기존 qldb 값
  const [defaultDistance, setDefaultDistance] = useState(); // 기존 주행거리(유효성 검사용)
  const [isSubmitting, setIsSubmitting] = useState(false); // 중복 요청 방지를 위한 상태
  const [accidentDateValid, setAccidentDateValid] = useState(true); // 교통사고 선택 시, 날짜 필수값
  const [exchangeDateValid, setExchangeDateValid] = useState(true); // 교체 선택 시, 날짜 필수값
  const [accidentSelected, setAccidentSelected] = useState(false); // 사고 select 선택 시, 유효성 검사 조건
  const [exchangeSelected, setExchangeSelected] = useState(false); // 교체 select 선택 시, 유효성 검사 조건
  const today = new Date();
  today.setHours(0, 0, 0, 0); // 현재 날짜의 자정

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
   * 유효성 검사
   */
  const isAccidentValid = () => {
    return (
      (accidentVal.accidentType === '교통사고' || accidentVal.accidentType === '침수사고') &&
      accidentVal.accidentDesc.trim() !== ''
    );
  };

  /**
   * 유효성 검사 - 교체이력 값 여부 확인
   */
  const isExchangeValid = () => {
    return exchangeVal.exchangeType !== '' && exchangeVal.exchangeDesc.trim() !== '';
  };

  /**
   * 승인 modal 창 열기 전, 주행거리 확인
   */
  const handleOpenModal = () => {
    if (approveSaleForm.carDistance < defaultDistance) {
      SwalModals('error', '입력 오류', '기존 주행거리보다 작을 수 없습니다!', false);
      router.push('/admin/sales');
      return;
    } else {
      handleClickModal();
    }
  };

  /**
   * 승인 요청
   */
  const handleSubmit = () => {
    if (isSubmitting) {
      // 이미 요청 중이면 무시
      return;
    }

    if (accidentSelected && !isAccidentValid()) {
      // 사고 정보를 선택했을 때, 사고 정보 유효성 검사 실패 시 사용자에게 메시지 표시
      SwalModals('error', '입력 오류', '사고 정보를 올바르게 입력해주세요.', false);
      return;
    }

    if (exchangeSelected && !isExchangeValid()) {
      // 교체 정보를 선택했을 때, 교체 정보 유효성 검사 실패 시 사용자에게 메시지 표시
      SwalModals('error', '입력 오류', '교체 정보를 올바르게 입력해주세요.', false);
      return;
    }

    // 버튼 클릭 후 요청이 시작됐음을 표시
    setIsSubmitting(true);

    const newApproveData = {
      distance: approveSaleForm.carDistance,
      carAccidentInfoDto: accidentSelected
        ? {
            accidentType: accidentVal.accidentType,
            accidentDesc: accidentVal.accidentDesc,
            accidentDate: accidentVal.accidentDate,
          }
        : null, // 사고 정보를 선택하지 않았을 경우 null로 설정
      carExchangeInfoDtoList: exchangeSelected
        ? {
            exchangeType: exchangeVal.exchangeType,
            exchangeDesc: exchangeVal.exchangeDesc,
            exchangeDate: exchangeVal.exchangeDate,
          }
        : null, // 교체 정보를 선택하지 않았을 경우 null로 설정
    };

    oneApproveFormPatchApi(saleformId, newApproveData)
      .then((res) => {
        console.log(res);
        if (res.status === 200 && res.data === true) {
          // 승인이 성공적으로 완료된 경우
          SwalModals(
            'success',
            '점검 정보 승인',
            '점검 정보가 승인되었습니다. 차량 게시글을 등록으로 이동합니다.',
            false,
          );
          router.replace(`/admin/sales/register/${saleformId}`);
          return;
        }
        if (res.status === 200 && res.data === false) {
          SwalModals(
            'error',
            '점검 정보 승인 오류',
            '잘못된 요청입니다. 다시 확인해주세요.',
            false,
          );
          return;
        }
      })
      .finally(() => {
        // 요청이 완료되면 버튼 활성화
        setIsSubmitting(false);
      });
  };

  // [교체] select box func
  const handleExchangeChange = (e) => {
    setExchangeVal({
      ...exchangeVal,
      exchangeType: e.target.value,
    });
    setExchangeSelected(true); // 교체부위가 선택되었다고 표시

    // 교체부위 select box의 값이 없으면 교체사유와 date input을 비활성화
    if (!e.target.value) {
      setExchangeVal({
        ...exchangeVal,
        exchangeDesc: '', // 교체사유 초기화
        exchangeDate: '', // 교체 date 초기화
      });
    }
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
    const selectedDate = e.target.value;
    const isValid = new Date(selectedDate) <= today;

    if (!isValid) {
      // 미래 날짜가 선택되면 경고 메시지 표시
      SwalModals('error', '입력 오류', '미래 날짜는 선택할 수 없습니다.', false);

      // 날짜를 오늘 날짜로 초기화
      const formattedToday = today.toISOString().split('T')[0];
      e.target.value = formattedToday; // 선택한 날짜를 오늘 날짜로 변경
      setExchangeVal({
        ...exchangeVal,
        exchangeDate: formattedToday,
      });
    } else {
      setExchangeVal({
        ...exchangeVal,
        exchangeDate: selectedDate,
      });
      setExchangeDateValid(isValid);
    }
  };

  // [사고유형] select box func
  const handleAccidentChange = (e) => {
    setAccidentVal({
      ...accidentVal,
      accidentType: e.target.value,
    });
    setAccidentSelected(true); // 사고유형이 선택되었다고 표시

    // 사고부위 select box의 값이 없으면 사고유형 desc와 date input을 비활성화
    if (!e.target.value) {
      setAccidentVal({
        ...accidentVal,
        accidentDesc: '', // 사고유형 desc 초기화
        accidentDate: '', // 사고유형 date 초기화
      });
    }
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
    const selectedDate = e.target.value;
    const isValid = new Date(selectedDate) <= today;

    if (!isValid) {
      // 미래 날짜가 선택되면 경고 메시지 표시
      SwalModals('error', '입력 오류', '미래 날짜는 선택할 수 없습니다.', false);

      // 날짜를 오늘 날짜로 초기화
      const formattedToday = today.toISOString().split('T')[0];
      e.target.value = formattedToday; // 선택한 날짜를 오늘 날짜로 변경
      setAccidentVal({
        ...accidentVal,
        accidentDate: formattedToday,
      });
    } else {
      setAccidentVal({
        ...accidentVal,
        accidentDate: selectedDate,
      });
      setAccidentDateValid(isValid);
    }
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
      fontWeight: 'bold',
    },
    titleCard: {
      py: 7,
      boxShadow: '0 8px 32px 0 rgba(31, 38, 135, 0.15)',
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
      fontWeight: 'bold',
    },
    flexBox: {
      display: 'flex',
      alignItems: 'left',
      marginLeft: 0,
      paddingLeft: 0,
    },
    formContents: {
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'left',
      marginLeft: 0,
    },
    inputLabel: { fontSize: '1.2rem', my: 1, fontWeight: 'bold', color: '#F95700' },
    submitBtn: {
      width: '100%',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      alignContent: 'center',
      my: 10,
    },
    historyCard: {
      padding: 5,
    },
  };

  return mounted && approveSaleForm ? (
    <ThemeProvider theme={responsiveFontTheme}>
      <CssBaseline />
      <Typography
        sx={saleApproveFormCss.approveFormTitle}
        color="primary"
        component="h4"
        variant="h4"
        gutterBottom>
        판매 신청 관리
      </Typography>
      {/* subtitle card */}
      <Card sx={saleApproveFormCss.titleCard}>
        <Typography variant="h4" sx={saleApproveFormCss.colorTypo} color="primary">
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
        <Grid container sx={saleApproveFormCss.flexBox}>
          <Grid item xs={12} md={6}>
            <Box noValidate>
              <Grid container spacing={3}>
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
                    disabled={!accidentSelected || !accidentVal.accidentType}
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
                    disabled={!accidentSelected || !accidentVal.accidentType}
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
                    disabled={!exchangeSelected || !exchangeVal.exchangeType}
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
                    disabled={!exchangeSelected || !exchangeVal.exchangeType}
                  />
                </Grid>
              </Grid>
              {/* <Grid sx={saleApproveFormCss.submitBtn}>
                <Button
                  size="large"
                  type="submit"
                  variant="contained"
                  onClick={handleOpenModal}
                  disabled={
                    isSubmitting ||
                    (accidentSelected &&
                      (!accidentVal.accidentType ||
                        !accidentVal.accidentDesc ||
                        !accidentVal.accidentDate)) ||
                    (exchangeSelected &&
                      (!exchangeVal.exchangeType ||
                        !exchangeVal.exchangeDesc ||
                        !exchangeVal.exchangeDate))
                  }>
                  승인 신청
                </Button>
              </Grid> */}
            </Box>
          </Grid>

          {/* HISTORY */}

          <Grid item xs={12} md={6} justifyContent="center">
            <Grid container className={styles.historyBox} justifyContent="center">
              <div className={styles.historyCard}>
                <Grid item xs={12} py={2}>
                  <Typography variant="h5" fontWeight="bold" className={styles.strongText}>
                    {`${approveSaleForm.carNum} `}
                  </Typography>
                  <Typography variant="h5" fontWeight="bold" className={styles.text}>
                    {`차량의 기존이력`}
                  </Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold" gutterBottom>
                    사고이력 조회
                  </Typography>
                  {approveSaleForm.carAccidentInfoDtoList.map((accidentItem, idx) => {
                    return (
                      <Typography key={idx}>
                        {accidentItem.accidentType === '교통사고' ? (
                          <div className={styles.historyAccidentBox}>
                            <CarCrashIcon color="primary" />
                            <span
                              className={styles.strongText}>{`${accidentItem.accidentType}`}</span>
                            <span>{` ${accidentItem.accidentDesc}`}</span>
                            <Chip size="small" label={`${accidentItem.accidentDate}`} />
                          </div>
                        ) : (
                          <div className={styles.historyAccidentBox}>
                            <WaterDropOutlinedIcon color="primary" />
                            <span
                              className={styles.strongText}>{`${accidentItem.accidentType}`}</span>
                            <span>{` ${accidentItem.accidentDesc}`}</span>
                            <Chip size="small" label={`${accidentItem.accidentDate}`} />
                          </div>
                        )}
                      </Typography>
                    );
                  })}
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold" gutterBottom>
                    교체이력 조회
                  </Typography>
                  {approveSaleForm.carExchangeInfoDtoList.map((exchangeItem, idx) => {
                    return (
                      <div key={idx} className={styles.historyAccidentBox}>
                        <BuildIcon color="primary" />
                        <span className={styles.strongText}>{`${exchangeItem.exchangeType}`}</span>
                        <span>{` ${exchangeItem.exchangeDesc}`}</span>
                        <Chip size="small" label={`${exchangeItem.exchangeDate}`} />
                      </div>
                    );
                  })}
                </Grid>
              </div>
            </Grid>
          </Grid>
          <Grid sx={saleApproveFormCss.submitBtn}>
            <Button
              size="large"
              type="submit"
              variant="contained"
              onClick={handleOpenModal}
              disabled={
                isSubmitting ||
                (accidentSelected &&
                  (!accidentVal.accidentType ||
                    !accidentVal.accidentDesc ||
                    !accidentVal.accidentDate)) ||
                (exchangeSelected &&
                  (!exchangeVal.exchangeType ||
                    !exchangeVal.exchangeDesc ||
                    !exchangeVal.exchangeDate))
              }>
              승인 신청
            </Button>
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
    <LoadingBar />
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
