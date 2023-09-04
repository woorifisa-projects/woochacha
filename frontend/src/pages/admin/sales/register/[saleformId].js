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
  ThemeProvider,
  Typography,
  responsiveFontSizes,
} from '@mui/material';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import AddIcon from '@mui/icons-material/Add';
import theme from '@/styles/theme';
import withAdminAuth from '@/hooks/withAdminAuth';
import { ADMIN_REGISTER_MODAL } from '@/constants/string';
import { handleMultipleFileUpload } from '@/components/common/MultipleFileUpload';
import ImageSlider from '@/components/product/ImageSlider';
import OneButtonModal from '@/components/common/OneButtonModal';

function AdminSalesRegisterForm() {
  let responsiveFontTheme = responsiveFontSizes(theme);
  const [mounted, setMounted] = useState(false);
  const [uploadFileValue, setUploadFileValue] = useState(null);
  const [previewImageList, setPreviewImageList] = useState([]);

  const handleSaveregisterVal = (e) => {
    e.preventDefault();
    console.log('!');
    handleClickModal();
  };

  const handleSubmit = () => {
    console.log('제출');
  };

  // Modal 버튼 클릭 유무
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);

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

  // TODO: AXIOS
  const get_dummy_data = {
    registerProductBasicInfo: {
      title: '현대 아반떼AD 2021',
      carName: '아반떼AD',
      carNum: '00가0000',
      branch: '서울',
    },
    registerProductDetailInfo: {
      model: '현대',
      color: '흰색',
      year: 2021,
      capacity: 4,
      distance: 350000,
      carType: 'SUV',
      fuelType: '가솔린',
      transmissionName: '수동',
      productAccidentInfoList: [
        {
          type: '침수사고',
          date: '2023-08-05',
        },
      ],
      productExchangeInfoList: [
        {
          type: '앞문',
          date: '2023-12-23',
        },
      ],
    },
    registerProductOptionInfos: [
      {
        option: '열선시트',
        whether: 1,
      },
      {
        option: '스마트키',
        whether: 1,
      },
      {
        option: '블랙박스',
        whether: 1,
      },
      {
        option: '네비게이션',
        whether: 1,
      },
      {
        option: '에어백',
        whether: 1,
      },
      {
        option: '썬루프',
        whether: 1,
      },
      {
        option: '하이패스',
        whether: 1,
      },
      {
        option: '후방카메라',
        whether: 1,
      },
    ],
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    mounted && (
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
            <Box component="form" noValidate onSubmit={handleSaveregisterVal}>
              <Grid container spacing={2}>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    게시글명
                  </Typography>
                  <Typography variant="body1">
                    {get_dummy_data.registerProductBasicInfo.title}
                  </Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    차량번호
                  </Typography>
                  <Typography variant="body1">
                    {get_dummy_data.registerProductBasicInfo.carNum}
                  </Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    차량 소유주 이름
                  </Typography>
                  <Typography variant="body1">{'소유주 이름 데이터는 어디서?'}</Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    차량 소유주 전화번호
                  </Typography>
                  <Typography variant="body1">{'소유주 전화번호 데이터는 어디서?'}</Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    지점명
                  </Typography>
                  <Typography variant="body1">
                    {get_dummy_data.registerProductBasicInfo.branch}
                  </Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    주행거리
                  </Typography>
                  <Typography variant="body1">
                    {get_dummy_data.registerProductDetailInfo.distance}
                  </Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    연식
                  </Typography>
                  <Typography variant="body1">
                    {get_dummy_data.registerProductDetailInfo.year}
                  </Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    승차정원
                  </Typography>
                  <Typography variant="body1">
                    {get_dummy_data.registerProductDetailInfo.capacity}
                  </Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    차종
                  </Typography>
                  <Typography variant="body1">
                    {get_dummy_data.registerProductDetailInfo.carType}
                  </Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    브랜드
                  </Typography>
                  <Typography variant="body1">
                    {get_dummy_data.registerProductDetailInfo.model}
                  </Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    연료
                  </Typography>
                  <Typography variant="body1">
                    {get_dummy_data.registerProductDetailInfo.fuelType}
                  </Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    차량 색상
                  </Typography>
                  <Typography variant="body1">
                    {get_dummy_data.registerProductDetailInfo.color}
                  </Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    변속기
                  </Typography>
                  <Typography variant="body1">
                    {get_dummy_data.registerProductDetailInfo.transmissionName}
                  </Typography>
                </Grid>
                <Grid item xs={12} py={2}>
                  <Typography variant="h6" fontWeight="bold">
                    사고이력
                  </Typography>
                  {get_dummy_data.registerProductDetailInfo.productAccidentInfoList.map(
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
                  {get_dummy_data.registerProductDetailInfo.productExchangeInfoList.map(
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
                    {get_dummy_data.registerProductOptionInfos.map((item, idx) => {
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
                <Button
                  size="large"
                  type="submit"
                  variant="contained"
                  onClick={handleSaveregisterVal}>
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
