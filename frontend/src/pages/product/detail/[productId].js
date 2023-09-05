import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import {
  Box,
  Button,
  Card,
  CardContent,
  CssBaseline,
  Grid,
  TextField,
  ThemeProvider,
  Typography,
  responsiveFontSizes,
} from '@mui/material';
import BasicModal from '@/components/common/BasicModal';
import ImageSlider from '@/components/product/ImageSlider';
import theme from '@/styles/theme';
import { CAPITAL_CONTENTS, PURCHASE_MODAL } from '@/constants/string';
import { purchaseRequest } from '@/services/productApi';
import { todayDate } from '@/utils/date';
// import DetailProduct from '@/components/product/DetailProduct';

export default function ProductDetail() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const { productId } = router.query;
  let responsiveFontTheme = responsiveFontSizes(theme);
  const [detailProduct, setDetailProduct] = useState();
  const [purchaseDateVal, setPurchaseDateVal] = useState(todayDate);

  // Modal 버튼 클릭 유무
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);

  const handleMovePage = (url) => {
    router.push(url);
  };

  /**
   * 구매요청 관련 함수
   */
  const handlePurchaseRequest = () => {
    const purchaseForm = {
      memberId: '1',
      productId: '15',
      purchaseDateVal: purchaseDateVal, // 날짜는 일단 넣어놨습니다.
    };
    purchaseRequest(purchaseForm).then((data) => {
      console.log(data);
      alert('요청이 완료!');
      router.push(`/product`);
    });
  };

  /**
   * 날짜 관련 함수
   */
  const handleChangeDate = (e) => {
    setPurchaseDateVal(e.target.value);
    console.log(purchaseDateVal);
  };

  useEffect(() => {
    //   productId &&
    //     productDetailGetApi(productId).then((data) => {
    //       setDetailProduct(data);
    //     });
    setMounted(true);
  }, []);

  // TODO: axios 통신 후 받을 데이터 (DUMMY_DATA)
  const dummy_data = {
    productBasicInfo: {
      title: '기아 올 뉴 카니발 2018년형',
      carNum: '22나2222',
      branch: '서울',
      price: 2690,
    },
    productDetailInfo: {
      capacity: 9,
      distance: 110000,
      carType: 'RV',
      fuelName: '디젤',
      transmissionName: '오토',
      produdctAccidentInfoList: [
        {
          type: '침수사고',
          count: 1,
        },
        {
          type: '교통사고',
          count: 2,
        },
      ],
      productExchangeInfoList: [
        {
          type: '본네트',
          count: 1,
        },
        {
          type: '뒷문',
          count: 1,
        },
      ],
    },
    productOptionInfo: [
      {
        option: '열선시트',
        whether: 1,
      },
      {
        option: '스마트키',
        whether: 0,
      },
      {
        option: '블랙박스',
        whether: 1,
      },
      {
        option: '네비게이션',
        whether: 0,
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
        whether: 0,
      },
      {
        option: '후방카메라',
        whether: 1,
      },
    ],
    productOwnerInfo: null,
    carImageList: [
      'https://woochacha.s3.ap-northeast-2.amazonaws.com/product/00%EA%B0%800000/1',
      'https://woochacha.s3.ap-northeast-2.amazonaws.com/product/00%EA%B0%800000/1',
      'https://woochacha.s3.ap-northeast-2.amazonaws.com/product/00%EA%B0%800000/1',
    ],
  };

  const productDetailCss = {
    detailHeaderBox: {
      maxWidth: 'fitContents',
      margin: 'auto',
      backgroundColor: '#DEF2FF',
      boxShadow: 3,
      borderRadius: 7,
      py: 8,
      px: 8,
      my: 10,
      height: '100%',
      width: '80%',
      display: 'flex',
      flexDirection: 'column',
    },
    detailBox: {
      maxWidth: 'fitContents',
      margin: 'auto',
      backgroundColor: '#FFF',
      py: 8,
      px: 8,
      my: 10,
      height: '100%',
      width: '80%',
      display: 'flex',
      flexDirection: 'column',
    },
    productTitle: { mb: 5, color: '#1691ef', pb: 2, borderBottom: '1px solid #1691ef' },
    card: {
      height: '50%',
      maxHeight: '300px',
      display: 'flex',
      flexDirection: 'column',
      mb: 3,
    },
    cardMedia: { pt: '56.25%', borderRadius: '1rem', minWidth: '150px', maxWidth: '700px' },
    cardContent: { flexGrow: 1 },
  };

  return (
    // detailProduct &&
    mounted && (
      <ThemeProvider theme={responsiveFontTheme}>
        <CssBaseline />
        {/* main page */}
        <main>
          {/* Detail header box */}
          <Box sx={productDetailCss.detailHeaderBox}>
            <Typography
              sx={productDetailCss.productTitle}
              variant="h4"
              component="h4"
              fontWeight="bold">
              {dummy_data.productBasicInfo.title}
            </Typography>
            <Grid container spacing={2} alignItems="flex-start" justifyContent="center" mb={2}>
              <Grid item xs={12} sm={12} md={6}>
                <ImageSlider image={dummy_data.carImageList} />
              </Grid>
              <Grid item xs={12} sm={12} md={6}>
                <Grid container spacing={2} alignItems="flex-start" justifyContent="center" mb={2}>
                  <Grid item xs={12} sm={12} md={12}>
                    <Card sx={productDetailCss.card}>
                      <CardContent>
                        <Box>
                          <Typography gutterBottom variant="h6" mr={2}>
                            차량 상세 정보
                          </Typography>
                          <Typography gutterBottom variant="body1">
                            {dummy_data.productBasicInfo.title}
                          </Typography>
                          <Typography gutterBottom variant="body1">
                            {dummy_data.productBasicInfo.carNum}
                          </Typography>
                          <Typography gutterBottom variant="body1">
                            {dummy_data.productBasicInfo.branch}
                          </Typography>
                          <Typography gutterBottom variant="body1">
                            {`${dummy_data.productBasicInfo.price} 만원`}
                          </Typography>
                        </Box>
                      </CardContent>
                    </Card>
                  </Grid>
                  <Grid item xs={12} sm={12} md={12}>
                    <Card sx={productDetailCss.card}>
                      <CardContent>
                        <Box>
                          <Typography gutterBottom variant="h6" mr={2}>
                            차량 상세 정보
                          </Typography>
                          <Typography gutterBottom variant="body2">
                            {`승차인원 : ${dummy_data.productDetailInfo.capacity}`}
                          </Typography>
                          <Typography gutterBottom variant="body2">
                            {`주행거리 : ${dummy_data.productDetailInfo.distance}`}
                          </Typography>
                          <Typography gutterBottom variant="body2">
                            {`차종 : ${dummy_data.productDetailInfo.carType}`}
                          </Typography>
                          <Typography gutterBottom variant="body2">
                            {`연료 : ${dummy_data.productDetailInfo.fuelName}`}
                          </Typography>
                          <Typography gutterBottom variant="body2">
                            {`변속기 : ${dummy_data.productDetailInfo.transmissionName}`}
                          </Typography>
                        </Box>
                      </CardContent>
                    </Card>
                  </Grid>
                  <Grid item xs={12} sm={12} md={12}>
                    <Card sx={productDetailCss.card}>
                      <CardContent>
                        <Box>
                          <Typography gutterBottom variant="body1">
                            {dummy_data.productBasicInfo.title}
                          </Typography>
                          <Typography gutterBottom variant="body1">
                            {dummy_data.productBasicInfo.carNum}
                          </Typography>
                          <Typography gutterBottom variant="body1">
                            {dummy_data.productBasicInfo.branch}
                          </Typography>
                          <Typography gutterBottom variant="body1">
                            {dummy_data.productBasicInfo.price}
                          </Typography>
                        </Box>
                      </CardContent>
                    </Card>
                    <Button onClick={handleClickModal} mt={5} fullWidth variant="contained">
                      구매 신청
                    </Button>
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
          </Box>

          {/* 금융정보 & 광고 item box */}
          <Box sx={productDetailCss.detailBox}>
            <Typography sx={productDetailCss.productTitle} variant="h4" component="h4">
              금융 정보
            </Typography>
            <Grid
              container
              display="flex"
              alignItems="center"
              justifyContent="space-between"
              mb={2}>
              <Grid item xs={12} sm={12} md={6}>
                <Typography gutterBottom variant="body1">
                  {`${dummy_data.productBasicInfo.title}의 가격에 맞는 금융상품이 궁금하다면?`}
                </Typography>
                <Typography gutterBottom variant="h4" component="h4">
                  {`${dummy_data.productBasicInfo.price} 만원`}
                </Typography>
              </Grid>
              <Grid item xs={12} sm={12} md={6}>
                {CAPITAL_CONTENTS.map((woncarItem, idx) => {
                  return (
                    <Box mb={3} key={idx}>
                      <Typography gutterBottom variant="h5">
                        {woncarItem.capitalSubTitle}
                      </Typography>
                      <Button
                        onClick={() => {
                          handleMovePage(woncarItem.wonCarUrl);
                        }}
                        variant="contained">
                        {woncarItem.capitalTitle}
                      </Button>
                    </Box>
                  );
                })}
              </Grid>
            </Grid>
          </Box>

          {/* 차량 상세 정보 item (전체 정보 & option) box */}
          <Box sx={productDetailCss.detailBox}>
            <Typography sx={productDetailCss.productTitle} variant="h4" component="h4">
              매물 상세 페이지
            </Typography>
            <Grid container spacing={2} alignItems="flex-start" justifyContent="center" mb={2}>
              <Grid item xs={12} sm={12} md={4}>
                <Card sx={productDetailCss.card}>
                  <CardContent>
                    <Box>
                      <Typography gutterBottom variant="h6" mr={2}>
                        차량 상세 정보
                      </Typography>
                      <Typography gutterBottom variant="body2">
                        {`승차인원 : ${dummy_data.productDetailInfo.capacity}`}
                      </Typography>
                      <Typography gutterBottom variant="body2">
                        {`주행거리 : ${dummy_data.productDetailInfo.distance}`}
                      </Typography>
                      <Typography gutterBottom variant="body2">
                        {`차종 : ${dummy_data.productDetailInfo.carType}`}
                      </Typography>
                      <Typography gutterBottom variant="body2">
                        {`연료 : ${dummy_data.productDetailInfo.fuelName}`}
                      </Typography>
                      <Typography gutterBottom variant="body2">
                        {`변속기 : ${dummy_data.productDetailInfo.transmissionName}`}
                      </Typography>
                    </Box>
                  </CardContent>
                </Card>
              </Grid>
              <Grid item xs={12} sm={12} md={4}>
                <Card sx={productDetailCss.card}>
                  <CardContent>
                    <Box>
                      <Typography gutterBottom variant="h6" mr={2}>
                        차량 사고 내역 조회
                      </Typography>
                      {dummy_data.productDetailInfo.produdctAccidentInfoList.map(
                        (accidentItem, idx) => {
                          return (
                            <Typography key={idx} gutterBottom variant="body2">
                              {`${accidentItem.type} : ${accidentItem.count} 번`}
                            </Typography>
                          );
                        },
                      )}
                    </Box>
                  </CardContent>
                </Card>
              </Grid>
              <Grid item xs={12} sm={12} md={4}>
                <Card sx={productDetailCss.card}>
                  <CardContent>
                    <Box>
                      <Typography gutterBottom variant="h6" mr={2}>
                        차량 옵션 정보
                      </Typography>
                      {dummy_data.productOptionInfo.map((optionList, idx) => {
                        return (
                          <Typography key={idx} gutterBottom variant="body2">
                            {`${optionList.whether === 1 ? `${optionList.option}` : ''}`}
                          </Typography>
                        );
                      })}
                    </Box>
                  </CardContent>
                </Card>
              </Grid>
            </Grid>
          </Box>
          {showModal && (
            <BasicModal
              onClickModal={handleClickModal}
              isOpen={showModal}
              modalContent={PURCHASE_MODAL.CONTENTS}
              callBackFunc={handlePurchaseRequest}>
              <TextField
                value={purchaseDateVal}
                onChange={handleChangeDate}
                margin="normal"
                required
                fullWidth
                id="date"
                label="날짜를 선택해주세요"
                name="date"
                autoComplete="date"
                type="date"
                autoFocus
              />
            </BasicModal>
          )}
        </main>
      </ThemeProvider>
    )
  );
}
