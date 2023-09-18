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
  List,
  ListItem,
  ListItemText,
  Stack,
  TextField,
  ThemeProvider,
  Typography,
  responsiveFontSizes,
} from '@mui/material';
import { useRouter } from 'next/router';
import theme from '@/styles/theme';
import withAdminAuth from '@/hooks/withAdminAuth';
import { ADMIN_REGISTER_MODAL } from '@/constants/string';
import { handleMultipleFileUpload } from '@/components/common/MultipleFileUpload';
import OneButtonModal from '@/components/common/OneButtonModal';
import { oneRegisterFormGetApi, oneRegisterFormPostApi } from '@/services/adminpageApi';
import { SwalModals } from '@/utils/modal';
import LoadingBar from '@/components/common/LoadingBar';
import styles from './register.module.css';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import AddIcon from '@mui/icons-material/Add';
import CarCrashIcon from '@mui/icons-material/CarCrash';
import WaterDropOutlinedIcon from '@mui/icons-material/WaterDropOutlined';
import BuildIcon from '@mui/icons-material/Build';
import DetailImageSlider from '@/components/product/DetailImageSlider';

function AdminSalesRegisterForm(props) {
  let responsiveFontTheme = responsiveFontSizes(theme);
  const [mounted, setMounted] = useState(false);
  const [uploadFileValue, setUploadFileValue] = useState(null);
  const [previewImageList, setPreviewImageList] = useState([]);
  const [priceVal, setPriceVal] = useState();
  const [registerInfo, setRegisterInfo] = useState();
  const formDataVal = new FormData();

  // Modal 버튼 클릭 유무
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);

  const router = useRouter();
  const { saleformId } = props;
  console.log(props);

  const [isSubmitting, setIsSubmitting] = useState(false); // 중복 요청 방지
  const [isSubmitButtonDisabled, setIsSubmitButtonDisabled] = useState(true); // 버튼 비활성화 상태 추가

  // [주행거리] 주행거리 수정
  const handleChangePrice = (e) => {
    e.target.value = e.target.value.replace(/[^0-9]/g, '');
    setPriceVal(e.target.value);
  };

  useEffect(() => {
    if (priceVal && uploadFileValue && uploadFileValue.length === 4) {
      setIsSubmitButtonDisabled(false);
    } else {
      setIsSubmitButtonDisabled(true);
    }
  }, [priceVal, uploadFileValue]);

  /**
   * 게시글 등록하기
   */
  const handleSubmit = () => {
    if (isSubmitting) {
      return; // 이미 제출 중인 경우 중복 제출 방지
    }

    setIsSubmitting(true); // 중복 제출 방지 상태 업데이트
    console.log(uploadFileValue.length);

    // 이미지 업로드 유효성 검사 - 유효성 X
    if (!uploadFileValue || uploadFileValue.length !== 4) {
      setIsSubmitting(false);
      SwalModals('error', '사진 업로드 실패', '1장에서 4장 사이의 사진을 등록해주세요!', false);
      return;
    }

    // 판매 가격 유효성 검사
    if (!priceVal || priceVal.trim().length === 0) {
      setIsSubmitting(false);
      SwalModals('error', '판매 가격 미등록', '판매가격을 입력해주세요!', false);
      return;
    }

    formDataVal.append('carNum', registerInfo.registerProductBasicInfo.carNum);
    formDataVal.append('price', priceVal);
    uploadFileValue.forEach((image) => formDataVal.append('imageUrls', image));

    // FormData의 value 확인용 콘솔
    for (let value of formDataVal.values()) {
      console.log(value);
    }

    oneRegisterFormPostApi(saleformId, formDataVal).then((res) => {
      if (res.status === 200 && res.data === 'Success') {
        setIsSubmitting(false); // 중복 제출 방지 상태 해제
        SwalModals(
          'success',
          '차량게시글 등록 완료',
          '차량게시글 등록이 완료되었습니다! 게시글을 확인해주세요.',
          false,
        );
        router.replace(`/admin/sales`);
        return;
      }
      if (res.status === 200 && res.data !== 'Success') {
        setIsSubmitting(false); // 중복 제출 방지 상태 해제
        SwalModals(
          'error',
          '차량게시글 등록 미완료',
          '차량게시글 등록이 완료되지 않았습니다. 다시 시도해주세요.',
          false,
        );
        return;
      }
    });
  };

  // 화면 초기 데이터
  useEffect(() => {
    saleformId &&
      oneRegisterFormGetApi(saleformId).then((res) => {
        if (res.status === 200) {
          setRegisterInfo(res.data);
        }
      });
    setMounted(true);
  }, []);

  const saleRegisterFormCss = {
    registerFormTitle: {
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
    formContents: {
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'left',
      marginLeft: 0,
    },
    submitBtn: {
      width: '100%',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      alignContent: 'center',
      my: 10,
    },
    imageBox: {
      display: 'inline',
    },
    cardMedia: {
      width: '180px',
      height: '180px',
      borderRadius: '15px',
    },
    uploadBtn: {
      mt: 5,
    },
  };

  return mounted && registerInfo ? (
    <ThemeProvider theme={responsiveFontTheme}>
      <CssBaseline />
      <Typography
        sx={saleRegisterFormCss.registerFormTitle}
        color="primary"
        component="h4"
        variant="h4"
        gutterBottom>
        차량 게시글 등록
      </Typography>
      {/* subtitle card */}
      <Card sx={saleRegisterFormCss.titleCard}>
        <Typography variant="h4" sx={saleRegisterFormCss.subTitleTypo}>
          점검 정보 승인
        </Typography>
        <ArrowForwardIcon fontSize="large" />
        <Typography variant="h4" sx={saleRegisterFormCss.colorTypo} color="primary">
          차량 게시글 등록
        </Typography>
      </Card>

      {/* form contents */}
      <Container sx={saleRegisterFormCss.formContents} maxWidth="md">
        <CssBaseline />
        <Box>
          <Typography
            component="h1"
            variant="h4"
            sx={saleRegisterFormCss.registerFormTitle}
            color="primary">
            차량 게시글 등록
          </Typography>
          <Box noValidate>
            <Grid container spacing={2}>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold">
                  게시글명
                </Typography>
                <Typography variant="body1">
                  {registerInfo.registerProductBasicInfo.title}
                </Typography>
              </Grid>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold">
                  차량번호
                </Typography>
                <Typography variant="body1">
                  {registerInfo.registerProductBasicInfo.carNum}
                </Typography>
              </Grid>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold">
                  지점명
                </Typography>
                <Typography variant="body1">
                  {registerInfo.registerProductBasicInfo.branch}
                </Typography>
              </Grid>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold">
                  주행거리
                </Typography>
                <Typography variant="body1">
                  {`${registerInfo.registerProductDetailInfo.distance.toLocaleString()} km`}
                </Typography>
              </Grid>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold">
                  연식
                </Typography>
                <Typography variant="body1">
                  {`${registerInfo.registerProductDetailInfo.year} 년도`}
                </Typography>
              </Grid>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold">
                  승차정원
                </Typography>
                <Typography variant="body1">
                  {`${registerInfo.registerProductDetailInfo.capacity} 인`}
                </Typography>
              </Grid>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold">
                  차종
                </Typography>
                <Typography variant="body1">
                  {registerInfo.registerProductDetailInfo.carType}
                </Typography>
              </Grid>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold">
                  브랜드
                </Typography>
                <Typography variant="body1">
                  {registerInfo.registerProductDetailInfo.model}
                </Typography>
              </Grid>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold">
                  연료
                </Typography>
                <Typography variant="body1">
                  {registerInfo.registerProductDetailInfo.fuelName}
                </Typography>
              </Grid>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold">
                  차량 색상
                </Typography>
                <Typography variant="body1">
                  {registerInfo.registerProductDetailInfo.color}
                </Typography>
              </Grid>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold">
                  변속기
                </Typography>
                <Typography variant="body1">
                  {registerInfo.registerProductDetailInfo.transmissionName}
                </Typography>
              </Grid>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold" gutterBottom>
                  사고이력
                </Typography>
                {registerInfo.registerProductDetailInfo.produdctAccidentInfoList.map(
                  (item, idx) => {
                    return (
                      <Typography key={idx}>
                        {item.type === '교통사고' ? (
                          <div key={idx} className={styles.historyAccidentBox}>
                            <CarCrashIcon color="primary" />
                            <span className={styles.strongText}>{`${item.type}`}</span>
                            <Chip size="small" label={`${item.date}`} />
                          </div>
                        ) : (
                          <div key={idx} className={styles.historyAccidentBox}>
                            <WaterDropOutlinedIcon color="primary" />
                            <span className={styles.strongText}>{`${item.type}`}</span>
                            <Chip size="small" label={`${item.date}`} />
                          </div>
                        )}
                      </Typography>
                    );
                  },
                )}
              </Grid>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold" gutterBottom>
                  교체부위
                </Typography>
                {registerInfo.registerProductDetailInfo.productExchangeInfoList.map((item, idx) => {
                  return (
                    <div key={idx} className={styles.historyAccidentBox}>
                      <BuildIcon color="primary" />
                      <span className={styles.strongText}>{`${item.type}`}</span>
                      <Chip size="small" label={`${item.date}`} />
                    </div>
                  );
                })}
              </Grid>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold">
                  옵션 정보
                </Typography>
                <List>
                  {registerInfo.registerProductOptionInfos.map((item, idx) => {
                    return item.whether === 1 ? (
                      <ListItem key={idx} variant="body1">
                        <AddIcon />
                        <ListItemText primary={`${item.option}`} />
                      </ListItem>
                    ) : (
                      ''
                    );
                  })}
                </List>
              </Grid>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold" gutterBottom>
                  판매가격
                </Typography>
                <TextField
                  fullWidth
                  id="priceVal"
                  label="판매가격을 입력해주세요 (만원)"
                  name="priceVal"
                  value={priceVal || ''}
                  onInput={handleChangePrice}
                  type="text"
                />
              </Grid>
              <Grid item xs={12} py={2}>
                <Typography variant="h6" fontWeight="bold">
                  사진 등록 (최대 4장)
                </Typography>
                <Stack direction="column" alignItems="center" spacing={2} mb={5}>
                  {previewImageList.length > 0 ? (
                    <DetailImageSlider image={previewImageList} />
                  ) : (
                    ''
                  )}
                  <label htmlFor="upload-image">
                    <input
                      id="upload-image"
                      hidden
                      accept="image/*"
                      type="file"
                      multiple={true}
                      onChange={(event) =>
                        handleMultipleFileUpload(
                          event,
                          setUploadFileValue,
                          previewImageList,
                          setPreviewImageList,
                        )
                      }
                    />
                    <Button
                      size="large"
                      variant="outlined"
                      component="span"
                      sx={saleRegisterFormCss.uploadBtn}>
                      사진 업로드하기
                    </Button>
                  </label>
                </Stack>
              </Grid>
            </Grid>

            <Grid sx={saleRegisterFormCss.submitBtn}>
              <Button
                size="large"
                variant="contained"
                onClick={handleSubmit}
                disabled={isSubmitButtonDisabled} // 버튼 활성/비활성 상태 추가
              >
                등록하기
              </Button>
            </Grid>
          </Box>
        </Box>
      </Container>
      {showModal && (
        <OneButtonModal
          onClickModal={handleClickModal}
          isOpen={showModal}
          modalContent={ADMIN_REGISTER_MODAL.CONTENTS}
          callBackFunc={handleSubmit}
        />
      )}
    </ThemeProvider>
  ) : (
    <LoadingBar />
  );
}

// side menu 레이아웃
AdminSalesRegisterForm.Layout = withAdminAuth(AdminPageLayout);
export default AdminSalesRegisterForm;

export async function getServerSideProps(context) {
  const saleformId = context.params.saleformId;
  return {
    props: {
      saleformId,
    },
  };
}
