import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import {
  Box,
  Button,
  Card,
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
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import AddIcon from '@mui/icons-material/Add';
import theme from '@/styles/theme';
import withAdminAuth from '@/hooks/withAdminAuth';
import { ADMIN_REGISTER_MODAL } from '@/constants/string';
import { handleMultipleFileUpload } from '@/components/common/MultipleFileUpload';
import ImageSlider from '@/components/product/ImageSlider';
import OneButtonModal from '@/components/common/OneButtonModal';
import { oneRegisterFormGetApi, oneRegisterFormPostApi } from '@/services/adminpageApi';
import { SwalModals } from '@/utils/modal';

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

  /**
   * 승인 버튼 클릭 시, modal popup
   */
  const handleSaveregisterVal = (e) => {
    handleClickModal();
  };

  // [주행거리] 주행거리 수정
  const handleChangePrice = (e) => {
    e.target.value = e.target.value.replace(/[^0-9]/g, '');
    setPriceVal(e.target.value);
  };

  /**
   * 게시글 등록하기
   */
  const handleSubmit = () => {
    if (uploadFileValue == null) {
      SwalModals('error', '사진 업로드 실패', '4장의 사진을 등록해주세요!', false);
      return;
    } else if (uploadFileValue.length !== 4) {
      SwalModals('error', '사진 업로드 실패', '4장의 사진을 등록해주세요!', false);
      return;
    }

    if (!priceVal || priceVal.trim().length === 0) {
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
    formContents: {
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'left',
      marginLeft: 0,
    },
    submitBtn: {
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      my: 6,
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

  return (
    mounted &&
    registerInfo && (
      <ThemeProvider theme={responsiveFontTheme}>
        <CssBaseline />
        <Typography
          sx={saleRegisterFormCss.registerFormTitle}
          component="h4"
          variant="h4"
          gutterBottom>
          관리자 페이지 - 차량 게시글 등록
        </Typography>
        {/* subtitle card */}
        <Card sx={saleRegisterFormCss.titleCard}>
          <Typography variant="h4" sx={saleRegisterFormCss.subTitleTypo}>
            점검 정보 승인
          </Typography>
          <ArrowForwardIcon fontSize="large" />
          <Typography variant="h4" sx={saleRegisterFormCss.colorTypo}>
            차량 게시글 등록
          </Typography>
        </Card>

        {/* form contents */}
        <Container sx={saleRegisterFormCss.formContents} maxWidth="xs">
          <CssBaseline />
          <Box>
            <Typography component="h1" variant="h4" sx={saleRegisterFormCss.registerFormTitle}>
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
                    {`${registerInfo.registerProductDetailInfo.distance} km`}
                  </Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    연식
                  </Typography>
                  <Typography variant="body1">
                    {registerInfo.registerProductDetailInfo.year}
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
                  <Typography variant="h6" fontWeight="bold">
                    사고이력
                  </Typography>
                  {registerInfo.registerProductDetailInfo.produdctAccidentInfoList.map(
                    (item, idx) => {
                      return (
                        <Typography
                          key={idx}
                          variant="body1">{`${item.type} - ${item.date}`}</Typography>
                      );
                    },
                  )}
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    교체부위
                  </Typography>
                  {registerInfo.registerProductDetailInfo.productExchangeInfoList.map(
                    (item, idx) => {
                      return (
                        <Typography
                          key={idx}
                          variant="body1">{`${item.type} - ${item.date}`}</Typography>
                      );
                    },
                  )}
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
                  <Typography variant="h6" fontWeight="bold">
                    판매가격
                  </Typography>
                  <TextField
                    fullWidth
                    id="priceVal"
                    label="판매가격을 입력해주세요"
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
                    {previewImageList.length > 0 ? <ImageSlider image={previewImageList} /> : ''}
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
                <Button size="large" variant="contained" onClick={handleSubmit}>
                  승인 신청
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
    )
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
