import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import {
  Box,
  Button,
  Card,
  CardContent,
  CssBaseline,
  Grid,
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
import { productDetailGetApi } from '@/services/productApi';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import { useRecoilState } from 'recoil';
import { SwalModals } from '@/utils/modal';
import DirectionsCarFilledIcon from '@mui/icons-material/DirectionsCarFilled';
import LocalGasStationIcon from '@mui/icons-material/LocalGasStation';
import SettingsIcon from '@mui/icons-material/Settings';
import PeopleOutlineIcon from '@mui/icons-material/PeopleOutline';
import TaskAltIcon from '@mui/icons-material/TaskAlt';
import WarehouseIcon from '@mui/icons-material/Warehouse';

import BuildIcon from '@mui/icons-material/Build';
import ConstructionIcon from '@mui/icons-material/Construction';
import DirectionsCarIcon from '@mui/icons-material/DirectionsCar';
import GroupsIcon from '@mui/icons-material/Groups';
import styled from '@emotion/styled';
import Paper from '@mui/material/Paper';
import Avatar from '@mui/material/Avatar';

// import DetailProduct from '@/components/product/DetailProduct';

export default function ProductDetail(props) {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const { productId, fetchData } = props;
  let responsiveFontTheme = responsiveFontSizes(theme);
  const [detailProduct, setDetailProduct] = useState();
  const [purchaseDateVal, setPurchaseDateVal] = useState(todayDate);
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);
  const buttonStyle = {
    backgroundColor: '#F95700', // 원하는 색상으로 변경
    color: 'white', // 텍스트 색상을 변경하려면 지정
  };

  // const carInspectionIcon = styled(BsClipboardCheckFill);

  // userid 조회
  const memberId = userLoginState.userId;

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
      memberId: memberId,
      productId: productId,
    };
    purchaseRequest(purchaseForm).then((res) => {
      if (res.status === 200) {
        SwalModals('success', '구매요청 완료', '구매요청이 완료되었습니다.', false);
        router.push(`/product`);
      }
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
    setDetailProduct(fetchData);
    console.log(fetchData);
    setMounted(true);
  }, []);

  const productDetailCss = {
    detailHeaderBox: {
      maxWidth: 'fitContents',
      margin: 'auto',
      // backgroundColor: '#DEF2FF',
      // boxShadow: 3,
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
    // , borderBottom: '1px solid #313131' 
    productTitle: { color: '#313131', pb: 1},
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
    detailProduct &&
    mounted && (
      <ThemeProvider theme={responsiveFontTheme}>
        {/* <CssBaseline /> */}
        {/* main page */}
        <main>
          {/* Detail header box */}
          <Box sx={productDetailCss.detailHeaderBox}>
            <Typography 
              sx={productDetailCss.productTitle}
              variant="h4"
              component="h4"
              fontWeight="bold">
              {detailProduct.productBasicInfo.title}
            </Typography>
            <Typography gutterBottom variant="h6" fontWeight="bold" marginTop={1}>
                      {detailProduct.productBasicInfo.carNum}
                    </Typography>
            {/* <Grid container spacing={2} alignItems="flex-start" justifyContent="center" mb={2}> */}
            <Grid
              item
              xs={12}
              sm={12}
              md={6}
              container
              spacing={2}
              alignItems="flex-start"
              justifyContent="center"
              mb={2}
              marginLeft={4}>
              <ImageSlider image={detailProduct.carImageList} />
            </Grid>
            
            <Grid item xs={12} sm={12} md={6}>
              <Grid container spacing={2} alignItems="flex-start" justifyContent="center" mb={2}>
                <Grid item xs={12} sm={12} md={12}>
                  <div
                    style={{
                      display: 'flex',
                      alignItems: 'center',
                      borderBottom: '1px solid gray',
                    }}>
                    <Typography gutterBottom variant="h4" fontWeight="bold">
                      {detailProduct.productBasicInfo.carNum}
                    </Typography>
                    <Typography
                      gutterBottom
                      variant="h4"
                      component="h4"
                      mr={2}
                      ml={30}
                      color="#F95700"
                      fontWeight="bold">
                      {`${detailProduct.productBasicInfo.price}`}
                    </Typography>
                    <Typography
                      gutterBottom
                      variant="h4"
                      component="h4"
                      mr={1}
                      ml={1}
                      color="black"
                      fontWeight="bold">
                      {'만원'}
                    </Typography>
                  </div>
                  <div style={{ display: 'flex', flexDirection: 'row' }}>
                    <div>
                      <Avatar
                        style={{
                          backgroundColor: 'lightgray', // 동그라미의 배경색
                          width: '70px', // 아이콘 전체 크기
                          height: '70px', // 아이콘 전체 크기
                          display: 'flex',
                          justifyContent: 'center',
                          alignItems: 'center',
                          marginBottom: '10px',
                          marginTop: '60px',
                          marginLeft: '50px',
                        }}>
                        <DirectionsCarFilledIcon style={{ fontSize: '40px', color: '#F95700' }} />
                      </Avatar>

                      <Avatar
                        style={{
                          backgroundColor: 'lightgray', // 동그라미의 배경색
                          width: '70px', // 아이콘 전체 크기
                          height: '70px', // 아이콘 전체 크기
                          display: 'flex',
                          justifyContent: 'center',
                          alignItems: 'center',
                          marginBottom: '10px',
                          marginTop: '40px',
                          marginLeft: '50px',
                        }}>
                        <LocalGasStationIcon style={{ fontSize: '40px', color: '#F95700' }} />
                      </Avatar>

                      <Avatar
                        style={{
                          backgroundColor: 'lightgray', // 동그라미의 배경색
                          width: '70px', // 아이콘 전체 크기
                          height: '70px', // 아이콘 전체 크기
                          display: 'flex',
                          justifyContent: 'center',
                          alignItems: 'center',
                          marginBottom: '10px',
                          marginTop: '40px',
                          marginLeft: '50px',
                        }}>
                        <SettingsIcon style={{ fontSize: '40px', color: '#F95700' }} />
                      </Avatar>
                    </div>
                    <div>
                      <Avatar
                        style={{
                          backgroundColor: 'lightgray', // 동그라미의 배경색
                          width: '70px', // 아이콘 전체 크기
                          height: '70px', // 아이콘 전체 크기
                          display: 'flex',
                          justifyContent: 'center',
                          alignItems: 'center',
                          marginBottom: '10px',
                          marginTop: '60px',
                          marginLeft: '160px',
                        }}>
                        <PeopleOutlineIcon style={{ fontSize: '40px', color: '#F95700' }} />
                      </Avatar>

                      <Avatar
                        style={{
                          backgroundColor: 'lightgray', // 동그라미의 배경색
                          width: '70px', // 아이콘 전체 크기
                          height: '70px', // 아이콘 전체 크기
                          display: 'flex',
                          justifyContent: 'center',
                          alignItems: 'center',
                          marginBottom: '10px',
                          marginTop: '40px',
                          marginLeft: '160px',
                        }}>
                        <TaskAltIcon style={{ fontSize: '40px', color: '#F95700' }} />
                      </Avatar>

                      <Avatar
                        style={{
                          backgroundColor: 'lightgray', // 동그라미의 배경색
                          width: '70px', // 아이콘 전체 크기
                          height: '70px', // 아이콘 전체 크기
                          display: 'flex',
                          justifyContent: 'center',
                          alignItems: 'center',
                          marginBottom: '10px',
                          marginTop: '40px',
                          marginLeft: '160px',
                        }}>
                        <WarehouseIcon style={{ fontSize: '40px', color: '#F95700' }} />
                      </Avatar>
                    </div>
                  </div>
                  {/* 
                    <div>
                      <DirectionsCarFilledIcon size="70" color="#F95700" />
                    </div> */}
                  {/* <Typography gutterBottom variant="body1">
                      {detailProduct.productBasicInfo.title}
                    </Typography> */}

                  <Typography gutterBottom variant="body1">
                    {detailProduct.productBasicInfo.branch}
                  </Typography>
                </Grid>
                <Grid item xs={12} sm={12} md={12}>
                  <Typography gutterBottom variant="h6" mr={2}>
                    {/* 차량 상세 정보 */}
                  </Typography>
                  <Typography gutterBottom variant="body2">
                    {`승차인원 : ${detailProduct.productDetailInfo.capacity}`}
                  </Typography>
                  <Typography gutterBottom variant="body2">
                    {`주행거리 : ${detailProduct.productDetailInfo.distance}`}
                  </Typography>
                  <Typography gutterBottom variant="body2">
                    {`차종 : ${detailProduct.productDetailInfo.carType}`}
                  </Typography>
                  <Typography gutterBottom variant="body2">
                    {`연료 : ${detailProduct.productDetailInfo.fuelName}`}
                  </Typography>
                  <Typography gutterBottom variant="body2">
                    {`변속기 : ${detailProduct.productDetailInfo.transmissionName}`}
                  </Typography>
                </Grid>
                <Grid item xs={12} sm={12} md={12}>
                  <Button
                    onClick={handleClickModal}
                    mt={5}
                    fullWidth
                    variant="contained"
                    style={buttonStyle}>
                    구매 신청
                  </Button>
                </Grid>
              </Grid>
            </Grid>
            {/* </Grid> */}
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
                  {`${detailProduct.productBasicInfo.title}의 가격에 맞는 금융상품이 궁금하다면?`}
                </Typography>
                <Typography gutterBottom variant="h4" component="h4" style={{ color: '#F95700' }}>
                  {`${detailProduct.productBasicInfo.price}`}
                </Typography>
                <Typography gutterBottom variant="h4" component="h4" style={{ color: 'black' }}>
                  {`만원`}
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
                        {`승차인원 : ${detailProduct.productDetailInfo.capacity}`}
                      </Typography>
                      <Typography gutterBottom variant="body2">
                        {`주행거리 : ${detailProduct.productDetailInfo.distance}`}
                      </Typography>
                      <Typography gutterBottom variant="body2">
                        {`차종 : ${detailProduct.productDetailInfo.carType}`}
                      </Typography>
                      <Typography gutterBottom variant="body2">
                        {`연료 : ${detailProduct.productDetailInfo.fuelName}`}
                      </Typography>
                      <Typography gutterBottom variant="body2">
                        {`변속기 : ${detailProduct.productDetailInfo.transmissionName}`}
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
                      {
                        <Typography
                          gutterBottom
                          variant="body2"
                          style={{ borderBottom: '1px solid black' }}>
                          사고 이력
                        </Typography>
                      }
                      {detailProduct.productDetailInfo.productAccidentInfoList &&
                      detailProduct.productDetailInfo.productAccidentInfoList.length > 0 ? (
                        detailProduct.productDetailInfo.productAccidentInfoList.map(
                          (accidentItem, idx) => (
                            <Typography key={idx} gutterBottom variant="body2">
                              {`${accidentItem.type ? `${accidentItem.type} :` : ''} ${
                                accidentItem.count === 0 ? '' : `${accidentItem.count}번`
                              }`}
                            </Typography>
                          ),
                        )
                      ) : (
                        <Typography gutterBottom variant="body2">
                          사고 이력 없음
                        </Typography>
                      )}
                      {
                        <Typography
                          gutterBottom
                          variant="body2"
                          style={{ borderBottom: '1px solid black', marginTop: '2em' }}>
                          교체 이력
                        </Typography>
                      }
                      {detailProduct.productDetailInfo.productExchangeInfoList &&
                      detailProduct.productDetailInfo.productExchangeInfoList.length > 0 ? (
                        detailProduct.productDetailInfo.productExchangeInfoList.map(
                          (exchangeItem, idx) => (
                            <Typography key={idx} gutterBottom variant="body2">
                              {`${exchangeItem.type} : ${exchangeItem.count} 번`}
                            </Typography>
                          ),
                        )
                      ) : (
                        <Typography gutterBottom variant="body2">
                          교체 이력 없음
                        </Typography>
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
                      {detailProduct.productOptionInfo.map((optionList, idx) => {
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
              callBackFunc={handlePurchaseRequest}></BasicModal>
          )}
        </main>
      </ThemeProvider>
    )
  );
}

export async function getServerSideProps(context) {
  const productId = context.params.productId;
  console.log(productId);
  const res = await productDetailGetApi(productId).then((res) => res.data);

  return {
    props: {
      productId: productId,
      fetchData: res,
    },
  };
}
