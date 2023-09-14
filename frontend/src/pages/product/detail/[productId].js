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
import { Line } from 'react-bootstrap-icons';
import { Link } from 'react-router-dom';

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

  // 6개의 할부기간 아이템 데이터 (예: 12개월, 24개월, ...)
  const installmentPeriods = ['12개월', '24개월', '36개월', '48개월', '60개월', '72개월'];

  const [selectedInstallment, setSelectedInstallment] = useState('12');

  // 선택한 할부 기간에 따라 월 납부금 계산 함수
  const calculateMonthlyPayment = () => {
    // detailProduct.productBasicInfo.price: 상품 가격
    // parseInt(selectedInstallment): 선택한 할부 기간
    if (selectedInstallment !== '') {
      const price = parseInt(detailProduct.productBasicInfo.price);
      const installment = parseInt(selectedInstallment);
      const monthlyPayment = parseInt(price / installment);
      console.log(monthlyPayment);
      return `${monthlyPayment}만원`;
    }
    return ''; // 할부 기간이 선택되지 않았을 때는 빈 문자열 반환
  };

  // 할부 기간을 선택했을 때 실행되는 핸들러
  const handleInstallmentSelect = (installmentText) => {
    // 정규식을 사용하여 "개월" 텍스트를 제외하고 숫자 부분만 추출
    const extractedInstallment = installmentText.match(/\d+/);
    if (extractedInstallment) {
      setSelectedInstallment(extractedInstallment[0]);
    }
    console.log(selectedInstallment);
  };

  const buttonStyle2 = {
    backgroundColor: 'transparent',
    border: '1px solid #ccc',
    borderRadius: '5px',
    width: '120px', // 각 버튼의 너비 조절
    height: '40px', // 각 버튼의 높이 조절
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  };

  const buttonStyle3 = {
    backgroundColor: '#FE7F02',
    border: '1px solid #FE7F02',
    borderRadius: '5px',
    width: '190px', // 각 버튼의 너비 조절
    height: '70px', // 각 버튼의 높이 조절
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  };

  const buttonStyle4 = {
    backgroundColor: '#FE7F02',
    border: '1px solid #FE7F02',
    borderRadius: '5px',
    width: '190px', // 각 버튼의 너비 조절
    height: '50px', // 각 버튼의 높이 조절
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  };

  const boxStyle = {
    backgroundColor: '#FFF9F7',
    borderRadius: '5px',
    width: '760px',
    height: '200px',
  };

  const boxStyle2 = {
    // backgroundColor: '#FFF9F7',
    borderRadius: '5px',
    border: '1px solid #F95700',
    width: '370px',
    height: '200px',
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
    productTitle: { color: '#313131', pb: 1 },
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
        <main>
          <Box sx={productDetailCss.detailHeaderBox}>
            <Typography
              sx={productDetailCss.productTitle}
              variant="h4"
              component="h4"
              fontWeight="bold">
              {detailProduct.productBasicInfo.title}
            </Typography>
            <div style={{ display: 'flex', flexDirection: 'row', flexWrap: 'wrap' }}>
              <Typography
                gutterBottom
                variant="h6"
                fontWeight="bold"
                marginTop={1}
                marginBottom={2}
                marginRight={2}>
                {detailProduct.productBasicInfo.carNum}
              </Typography>

              <Typography
                gutterBottom
                variant="h6"
                marginTop={1}
                marginBottom={2}
                marginRight={1}
                marginLeft={3}>
                {`${detailProduct.productBasicInfo.branch} ㆍ`}
              </Typography>
              <Typography gutterBottom variant="h6" marginTop={1} marginBottom={2} marginRight={1}>
                {`${detailProduct.productDetailInfo.distance}km ㆍ`}
              </Typography>
              <Typography gutterBottom variant="h6" marginTop={1} marginBottom={2} marginRight={1}>
                {`${detailProduct.productDetailInfo.carType} ㆍ`}
              </Typography>
              <Typography gutterBottom variant="h6" marginTop={1} marginBottom={2} marginRight={1}>
                {`${detailProduct.productDetailInfo.fuelName} ㆍ`}
              </Typography>
              <Typography gutterBottom variant="h6" marginTop={1} marginBottom={2} marginRight={1}>
                {`${detailProduct.productDetailInfo.transmissionName} ㆍ`}
              </Typography>
              <Typography gutterBottom variant="h6" marginTop={1} marginBottom={2} marginRight={1}>
                {detailProduct.productDetailInfo.capacity}
              </Typography>

              <Typography
                gutterBottom
                variant="h4"
                component="h4"
                mr={1}
                ml={55}
                color="#F95700"
                fontWeight="bold">
                {`${Number(detailProduct.productBasicInfo.price).toLocaleString()}`}
              </Typography>
              <Typography
                gutterBottom
                variant="h4"
                component="h4"
                ml={1}
                color="black"
                fontWeight="bold">
                {'만원'}
              </Typography>
            </div>
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
                      // borderBottom: '1px solid gray',
                    }}>
                    <Typography
                      gutterBottom
                      variant="h4"
                      component="h4"
                      mr={1}
                      ml={1}
                      color="#F95700"
                      fontWeight="bold">
                      {`${Number(detailProduct.productBasicInfo.price).toLocaleString()}`}
                    </Typography>
                    <Typography
                      gutterBottom
                      variant="h4"
                      component="h4"
                      mr={1}
                      ml={0}
                      color="#F95700"
                      fontWeight="bold">
                      {'만원'}
                    </Typography>
                  </div>

                  <div style={{ display: 'flex', flexDirection: 'row' }}>
                    <div
                      style={{
                        width: '370px', // 가로 크기
                        height: '80px', // 세로 크기
                        borderRadius: '5px', // 모서리를 둥글게 만듭니다
                        border: '1px solid #ccc', // 테두리 색상 및 두께
                        display: 'flex',
                        // justifyContent: 'center',
                        alignItems: 'center',
                        backgroundColor: 'transparent', // 배경색을 투명으로 설정
                        marginTop: '20px',
                      }}>
                      <div
                        style={{
                          display: 'flex',
                          alignItems: 'center',
                          flexDirection: 'column',
                          marginTop: '17px',
                        }}>
                        <div style={{ display: 'flex', alignItems: 'center', color: 'gray' }}>
                          <Typography marginLeft={1}>주행거리</Typography>
                          <DirectionsCarFilledIcon
                            style={{ fontSize: '20px', color: 'gray', marginLeft: '5px' }}
                          />
                        </div>

                        <div>
                          <Typography
                            gutterBottom
                            variant="h6"
                            // marginTop={1}
                            marginBottom={2}
                            marginRight={1}
                            marginLeft={3}
                            fontWeight="bold">
                            {`${detailProduct.productDetailInfo.distance}km`}
                          </Typography>
                        </div>
                      </div>
                    </div>

                    <div
                      style={{
                        width: '370px', // 가로 크기
                        height: '80px', // 세로 크기
                        borderRadius: '5px', // 모서리를 둥글게 만듭니다
                        border: '1px solid #ccc', // 테두리 색상 및 두께
                        display: 'flex',
                        // justifyContent: 'center',
                        alignItems: 'center',
                        backgroundColor: 'transparent', // 배경색을 투명으로 설정
                        marginTop: '20px',
                        marginLeft: '20px',
                      }}>
                      <div
                        style={{
                          display: 'flex',
                          alignItems: 'center',
                          flexDirection: 'column',
                          marginTop: '17px',
                        }}>
                        <div style={{ display: 'flex', alignItems: 'center', color: 'gray' }}>
                          <Typography marginLeft={3}>승차인원</Typography>
                          <PeopleOutlineIcon
                            style={{ fontSize: '20px', color: 'gray', marginLeft: '5px' }}
                          />
                        </div>
                        <div>
                          <Typography
                            gutterBottom
                            variant="h6"
                            // marginTop={1}
                            marginBottom={2}
                            marginRight={4}
                            fontWeight="bold">
                            {`${detailProduct.productDetailInfo.capacity}명`}
                          </Typography>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div style={{ display: 'flex', flexDirection: 'row' }}>
                    <div
                      style={{
                        width: '370px', // 가로 크기
                        height: '80px', // 세로 크기
                        borderRadius: '5px', // 모서리를 둥글게 만듭니다
                        border: '1px solid #ccc', // 테두리 색상 및 두께
                        display: 'flex',
                        // justifyContent: 'center',
                        alignItems: 'center',
                        backgroundColor: 'transparent', // 배경색을 투명으로 설정
                        marginTop: '20px',
                      }}>
                      <div
                        style={{
                          display: 'flex',
                          alignItems: 'center',
                          flexDirection: 'column',
                          marginTop: '17px',
                        }}>
                        <div style={{ display: 'flex', alignItems: 'center', color: 'gray' }}>
                          <Typography marginLeft={3}>차고지</Typography>
                          <WarehouseIcon
                            style={{ fontSize: '20px', color: 'gray', marginLeft: '5px' }}
                          />
                        </div>
                        <div>
                          <Typography
                            gutterBottom
                            variant="h6"
                            // marginTop={1}
                            marginBottom={2}
                            marginRight={4}
                            marginLeft={2}
                            fontWeight="bold">
                            {`${detailProduct.productBasicInfo.branch}`}
                          </Typography>
                        </div>
                      </div>
                    </div>

                    <div
                      style={{
                        width: '370px', // 가로 크기
                        height: '80px', // 세로 크기
                        borderRadius: '5px', // 모서리를 둥글게 만듭니다
                        border: '1px solid #ccc', // 테두리 색상 및 두께
                        display: 'flex',
                        // justifyContent: 'center',
                        alignItems: 'center',
                        backgroundColor: 'transparent', // 배경색을 투명으로 설정
                        marginTop: '20px',
                        marginLeft: '20px',
                      }}>
                      <div
                        style={{
                          display: 'flex',
                          alignItems: 'center',
                          flexDirection: 'column',
                          marginTop: '17px',
                        }}>
                        <div style={{ display: 'flex', alignItems: 'center', color: 'gray' }}>
                          <Typography marginLeft={3}>차종</Typography>
                          <TaskAltIcon
                            style={{ fontSize: '20px', color: 'gray', marginLeft: '5px' }}
                          />
                        </div>
                        <div>
                          <Typography
                            gutterBottom
                            variant="h6"
                            // marginTop={1}
                            marginBottom={2}
                            marginRight={4}
                            marginLeft={2}
                            fontWeight="bold">
                            {`${detailProduct.productDetailInfo.carType}`}
                          </Typography>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div style={{ display: 'flex', flexDirection: 'row' }}>
                    <div
                      style={{
                        width: '370px', // 가로 크기
                        height: '80px', // 세로 크기
                        borderRadius: '5px', // 모서리를 둥글게 만듭니다
                        border: '1px solid #ccc', // 테두리 색상 및 두께
                        display: 'flex',
                        // justifyContent: 'center',
                        alignItems: 'center',
                        backgroundColor: 'transparent', // 배경색을 투명으로 설정
                        marginTop: '20px',
                      }}>
                      <div
                        style={{
                          display: 'flex',
                          alignItems: 'center',
                          flexDirection: 'column',
                          marginTop: '17px',
                        }}>
                        <div style={{ display: 'flex', alignItems: 'center', color: 'gray' }}>
                          <Typography marginLeft={4}>연료</Typography>
                          <LocalGasStationIcon
                            style={{ fontSize: '20px', color: 'gray', marginLeft: '5px' }}
                          />
                        </div>

                        <div>
                          <Typography
                            gutterBottom
                            variant="h6"
                            // marginTop={1}
                            marginBottom={2}
                            marginRight={1}
                            marginLeft={3}
                            fontWeight="bold">
                            {`${detailProduct.productDetailInfo.fuelName}`}
                          </Typography>
                        </div>
                      </div>
                    </div>

                    <div
                      style={{
                        width: '370px', // 가로 크기
                        height: '80px', // 세로 크기
                        borderRadius: '5px', // 모서리를 둥글게 만듭니다
                        border: '1px solid #ccc', // 테두리 색상 및 두께
                        display: 'flex',
                        // justifyContent: 'center',
                        alignItems: 'center',
                        backgroundColor: 'transparent', // 배경색을 투명으로 설정
                        marginTop: '20px',
                        marginLeft: '20px',
                      }}>
                      <div
                        style={{
                          display: 'flex',
                          alignItems: 'center',
                          flexDirection: 'column',
                          marginTop: '17px',
                        }}>
                        <div style={{ display: 'flex', alignItems: 'center', color: 'gray' }}>
                          <Typography marginLeft={3}>변속기</Typography>
                          <SettingsIcon
                            style={{ fontSize: '20px', color: 'gray', marginLeft: '5px' }}
                          />
                        </div>
                        <div>
                          <Typography
                            gutterBottom
                            variant="h6"
                            // marginTop={1}
                            marginBottom={2}
                            marginRight={4}
                            fontWeight="bold">
                            {`${detailProduct.productDetailInfo.transmissionName}`}
                          </Typography>
                        </div>
                      </div>
                    </div>
                  </div>
                </Grid>

                {/* <Grid item xs={8} sm={7} md={6} ml={20}> */}
                <Button
                  onClick={handleClickModal}
                  mt={5}
                  variant="contained"
                  style={{ ...buttonStyle, width: '30%', marginLeft: '300px' }} // width 속성 추가
                >
                  구매 신청
                </Button>
                {/* </Grid> */}
              </Grid>
            </Grid>
            {/* </Grid> */}

            <Typography fontWeight="bold" fontSize="18px">
              할부기간
            </Typography>
            <Grid
              container
              spacing={1}
              alignItems="center"
              justifyContent="flex-start"
              mb={2}
              mt={1}>
              {installmentPeriods.map((period, index) => (
                <Grid item key={index}>
                  <Button
                    sx={buttonStyle2}
                    variant="outlined"
                    onClick={() => handleInstallmentSelect(period)} // 버튼 클릭 시 handleInstallmentSelect 호출
                  >
                    <Typography variant="body2">{period}</Typography>
                  </Button>
                </Grid>
              ))}
            </Grid>

            <Grid
              container
              spacing={1}
              alignItems="center"
              justifyContent="flex-start"
              mb={2}
              mt={1}>
              <Grid item>
                <Box sx={boxStyle} variant="outlined">
                  <Grid
                    container
                    spacing={2}
                    justifyContent="flex-start"
                    mb={2}
                    ml={4}
                    flexDirection="column">
                    <div>
                      <div style={{ display: 'flex', alignItems: 'center', marginTop: '30px' }}>
                        <Typography variant="body2" fontWeight="bold" fontSize="19px">
                          총 할부 신청금액
                        </Typography>
                        <Typography
                          variant="body2"
                          fontWeight="bold"
                          fontSize="24px"
                          marginLeft={13}
                          marginRight={10}
                          marginTop={2}>
                          {`${Number(detailProduct.productBasicInfo.price).toLocaleString()}만원`}
                        </Typography>
                        <Link to="https://app.gather.town/app/4vEB4Q4BUbTU0ckj/wooribank">
                          <Button
                            sx={{ ...buttonStyle3, marginTop: '10px', color: 'white' }}
                            variant="outlined">
                            메타버스 금융상담 받기
                          </Button>
                        </Link>
                      </div>
                    </div>
                    <div style={{ marginTop: '30px' }} width={10}>
                      <div style={{ display: 'flex', alignItems: 'center' }}>
                        <Typography variant="body2" fontWeight="bold" fontSize="19px" marginTop={1}>
                          월 납부금
                        </Typography>

                        <Typography
                          variant="body2"
                          fontWeight="bold"
                          fontSize="24px"
                          marginLeft={24}
                          color={'#F95700'}>
                          {calculateMonthlyPayment()}
                        </Typography>
                      </div>
                    </div>
                  </Grid>
                </Box>

                {/* <Box sx={boxStyle} variant="outlined" marginTop={2}>
                  <Typography>
                    신용카드 결제는
                  </Typography>
                </Box> */}
                <Box sx={boxStyle} variant="outlined" marginTop={2}>
                  <Grid container>
                    <div>
                      <Typography fontWeight="bold" marginLeft={5} marginTop={7}>
                        신용카드 결제는 <span style={{ color: '#F95700' }}>우리카드</span>로만 할 수
                        있습니다
                      </Typography>
                      <Typography marginLeft={5} fontSize="13px" marginTop={1}>
                        * 발급 문의: 우리카드 고객센터(1599-9955)
                      </Typography>
                      <Typography marginLeft={5} fontSize="13px">
                        * 혜택 문의: 자동차 금융상담센터(1577-9000)
                      </Typography>
                    </div>
                    <Grid marginLeft={19} marginTop={4}>
                      <Button
                        sx={{ ...buttonStyle4, marginTop: '10px', color: 'white' }}
                        variant="outlined">
                        우리카드 신규 발급
                      </Button>
                      <Button
                        sx={{ ...buttonStyle4, marginTop: '10px', color: 'white' }}
                        variant="outlined">
                        우리카드 혜택 확인
                      </Button>
                    </Grid>
                  </Grid>
                </Box>
              </Grid>
            </Grid>
            <Typography fontWeight="bold" fontSize="18px">
              우리WON카 금융상품
            </Typography>
            {/* <Grid>
            <Box sx={boxStyle2} variant="outlined" marginTop={2}></Box>
            <Box sx={boxStyle2} variant="outlined" marginTop={2}></Box>
            </Grid> */}
            <Grid container spacing={2}>
              <Grid item>
                <Box sx={boxStyle2} variant="outlined" marginTop={2}>
                  <Typography>내용을 추가하세요</Typography>
                  <Button variant="contained" color="primary">
                    버튼 1
                  </Button>
                  <Typography>추가 텍스트 1</Typography>
                </Box>
              </Grid>
              <Grid item>
                <Box sx={boxStyle2} variant="outlined" marginTop={2}></Box>
              </Grid>
            </Grid>
            <Grid container spacing={2}>
              <Grid item>
                <Box sx={boxStyle2} variant="outlined" marginTop={2}></Box>
              </Grid>
              <Grid item>
                <Box sx={boxStyle2} variant="outlined" marginTop={2}></Box>
              </Grid>
            </Grid>
          </Box>

          {/* 금융정보 & 광고 item box */}
          <Box sx={productDetailCss.detailBox}>
            <Grid
              container
              display="flex"
              alignItems="center"
              justifyContent="space-between"
              mb={2}>
              <Grid item xs={12} sm={12} md={6}></Grid>
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
