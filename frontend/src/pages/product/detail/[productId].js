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
import LocalFireDepartmentIcon from '@mui/icons-material/LocalFireDepartment'; // 열선시트
import CheckBoxIcon from '@mui/icons-material/CheckBox';

import BuildIcon from '@mui/icons-material/Build';
import ConstructionIcon from '@mui/icons-material/Construction';
import DirectionsCarIcon from '@mui/icons-material/DirectionsCar';
import GroupsIcon from '@mui/icons-material/Groups';
import styled from '@emotion/styled';
import Paper from '@mui/material/Paper';
import Avatar from '@mui/material/Avatar';

// import { Line } from 'react-bootstrap-icons';

export default function ProductDetail(props) {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const { productId, fetchData } = props;
  let responsiveFontTheme = responsiveFontSizes(theme);
  const [detailProduct, setDetailProduct] = useState();
  const [purchaseDateVal, setPurchaseDateVal] = useState(todayDate);
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);

  const gatherTownUrl = 'https://app.gather.town/app/4vEB4Q4BUbTU0ckj/wooribank';
  const wooriCardUrl = 'https://pc.wooricard.com/dcpc/yh1/crd/crd13/cardisuinqcan/H1CRD213S13.do';
  const wooriCardEvent = 'https://pc.wooricard.com/dcpc/yh1/bnf/bnf01/H1BNF201S00.do';

  const loanItem1Url =
    'https://spot.wooribank.com/pot/Dream?withyou=POLON0052&cc=c010528:c010531;c012425:c012399&PLM_PDCD=P020000031&PRD_CD=P020000031&HOST_PRD_CD=2013109171100';
  const loanItem2Url = 'https://www.wooriwoncar.com/loangoods/loanGoods?loanType=99';
  const loanItem3Url =
    'https://spot.wooribank.com/pot/Dream?withyou=POLON0058&cc=c010528:c010531;c012425:c012399&PLM_PDCD=P020006053&PRD_CD=P020006053&HOST_PRD_CD=2013109121100';
  const loanItem4Url = 'https://www.wooriwoncar.com/loangoods/loanGoods?loanType=99';

  const buttonStyle = {
    backgroundColor: '#F95700', // 원하는 색상으로 변경
    color: 'white', // 텍스트 색상을 변경하려면 지정
  };

  // 6개의 할부기간 아이템 데이터 (예: 12개월, 24개월, ...)
  const installmentPeriods = ['12개월', '24개월', '36개월', '48개월', '60개월', '72개월'];

  const [selectedInstallment, setSelectedInstallment] = useState('12');

  // 선택한 할부 기간에 따라 월 납부금 계산 함수
  const calculateMonthlyPayment = () => {
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

  const buttonStyle5 = {
    // backgroundColor: '#FE7F02',
    border: '1px solid #F95700',
    borderRadius: '25px',
    width: '160px', // 각 버튼의 너비 조절
    height: '40px', // 각 버튼의 높이 조절
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  };

  const buttonStyle6 = {
    backgroundColor: '#F95700',
    border: '1px solid #F95700',
    borderRadius: '25px',
    width: '160px', // 각 버튼의 너비 조절
    height: '40px', // 각 버튼의 높이 조절
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
    purchaseRequest(purchaseForm)
      .then((res) => {
        if (res.status === 200) {
          SwalModals('success', '구매요청 완료', '구매요청이 완료되었습니다.', false);
          router.push(`/product`);
        }
      })
      .catch((error) => {
        SwalModals('error', '구매신청 실패', '본인이 등록한 매물은 구매할 수 없습니다.', false);
        router.push(`/product/detail/${productId}`);
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
      borderRadius: 7,
      py: 0,
      px: 8,
      my: 10,
      height: '100%',
      width: '80%',
      display: 'flex',
      flexDirection: 'column',
    },
    detailBox: {
      maxWidth: 'fitContents',
      // margin: 'auto',
      backgroundColor: '#FFF',
      py: 8,
      px: 8,
      my: 10,
      height: '100%',
      width: '80%',
      display: 'flex',
      flexDirection: 'column',
    },
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
                marginLeft={3}
                color="gray">
                {`${detailProduct.productBasicInfo.branch} ㆍ`}
              </Typography>
              <Typography
                gutterBottom
                variant="h6"
                marginTop={1}
                marginBottom={2}
                marginRight={1}
                color="gray">
                {`${detailProduct.productDetailInfo.distance}km ㆍ`}
              </Typography>
              <Typography
                gutterBottom
                variant="h6"
                marginTop={1}
                marginBottom={2}
                marginRight={1}
                color="gray">
                {`${detailProduct.productDetailInfo.carType} ㆍ`}
              </Typography>
              <Typography
                gutterBottom
                variant="h6"
                marginTop={1}
                marginBottom={2}
                marginRight={1}
                color="gray">
                {`${detailProduct.productDetailInfo.fuelName} ㆍ`}
              </Typography>
              <Typography
                gutterBottom
                variant="h6"
                marginTop={1}
                marginBottom={2}
                marginRight={1}
                color="gray">
                {`${detailProduct.productDetailInfo.transmissionName} ㆍ`}
              </Typography>
              <Typography
                gutterBottom
                variant="h6"
                marginTop={1}
                marginBottom={2}
                marginRight={1}
                color="gray">
                {detailProduct.productDetailInfo.capacity}
              </Typography>

              <Typography
                gutterBottom
                variant="h4"
                component="h4"
                mr={1}
                ml={50}
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
            <Grid display="flex">
              <Grid>
                <Grid item xs={12} sm={12} md={6}>
                  <Grid
                    container
                    spacing={2}
                    alignItems="flex-start"
                    justifyContent="center"
                    mb={2}>
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

                      <Grid container spacing={2}>
                        <Grid item></Grid>
                        <Grid item></Grid>
                      </Grid>

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
                          <div style={{ marginTop: '2%' }}>
                            <div
                              style={{
                                display: 'flex',
                                alignItems: 'center',
                                flexDirection: 'column',
                              }}>
                              <div style={{ display: 'flex', alignItems: 'center', color: 'gray' }}>
                                <Typography>주행거리</Typography>
                                <DirectionsCarFilledIcon
                                  style={{ fontSize: '20px', color: 'gray' }}
                                />
                              </div>

                              <div>
                                <Typography
                                  gutterBottom
                                  variant="h6"
                                  // marginTop={1}
                                  // marginBottom={2}
                                  // marginRight={1}
                                  marginLeft={2}
                                  fontWeight="bold">
                                  {`${detailProduct.productDetailInfo.distance}km`}
                                </Typography>
                              </div>
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
                          <div style={{ marginTop: '2%', marginLeft: '5%' }}>
                            <div
                              style={{
                                display: 'flex',
                                alignItems: 'center',
                                flexDirection: 'column',
                                marginTop: '17px',
                              }}>
                              <div style={{ display: 'flex', alignItems: 'center', color: 'gray' }}>
                                <Typography>승차인원</Typography>
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
                                  marginRight={6}
                                  fontWeight="bold">
                                  {`${detailProduct.productDetailInfo.capacity}명`}
                                </Typography>
                              </div>
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
                          <div style={{ marginTop: '2%', marginLeft: '5%' }}>
                            <div
                              style={{
                                display: 'flex',
                                alignItems: 'center',
                                flexDirection: 'column',
                                marginTop: '17px',
                              }}>
                              <div style={{ display: 'flex', alignItems: 'center', color: 'gray' }}>
                                <Typography>차고지</Typography>
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
                                  fontWeight="bold">
                                  {`${detailProduct.productBasicInfo.branch}`}
                                </Typography>
                              </div>
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
                          <div style={{ marginTop: '2%' }}>
                            <div
                              style={{
                                display: 'flex',
                                alignItems: 'center',
                                flexDirection: 'column',
                                marginTop: '17px',
                              }}>
                              <div style={{ display: 'flex', alignItems: 'center', color: 'gray' }}>
                                <Typography>차종</Typography>
                                <TaskAltIcon style={{ fontSize: '20px', color: 'gray' }} />
                              </div>
                              <div>
                                <Typography
                                  gutterBottom
                                  variant="h6"
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
                          <div style={{ marginTop: '2%' }}>
                            <div
                              style={{
                                display: 'flex',
                                // alignItems: 'center',

                                flexDirection: 'column',
                                marginTop: '17px',
                              }}>
                              <div
                                style={{
                                  display: 'flex',
                                  alignItems: 'center',
                                  color: 'gray',
                                  marginLeft: '11%',
                                }}>
                                <Typography>연료</Typography>
                                <LocalGasStationIcon style={{ fontSize: '20px', color: 'gray' }} />
                              </div>
                              <div>
                                <Typography
                                  gutterBottom
                                  variant="h6"
                                  marginBottom={2}
                                  marginRight={4}
                                  marginLeft={2}
                                  fontWeight="bold">
                                  {`${detailProduct.productDetailInfo.fuelName}`}
                                </Typography>
                              </div>
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
                          <div style={{ marginTop: '2%', marginLeft: '5%' }}>
                            <div
                              style={{
                                display: 'flex',
                                // alignItems: 'center',
                                flexDirection: 'column',
                                marginTop: '17px',
                              }}>
                              <div style={{ display: 'flex', alignItems: 'center', color: 'gray' }}>
                                <Typography>변속기</Typography>
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
                      </div>
                    </Grid>

                    {/* <Grid item xs={8} sm={7} md={6} ml={20}> */}
                    {/* <Button
                  onClick={handleClickModal}
                  mt={5}
                  variant="contained"
                  style={{ ...buttonStyle, width: '30%', marginLeft: '300px' }} // width 속성 추가
                >
                  구매 신청
                </Button> */}
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
                      <Grid display="flex">
                        <Grid
                          container
                          spacing={2}
                          justifyContent="flex-start"
                          mb={2}
                          ml={4}
                          flexDirection="column"
                          marginTop="2%"
                          width="63%">
                          <div>
                            <div
                              style={{
                                display: 'flex',
                                alignItems: 'center',
                                marginTop: '6%',
                              }}>
                              <Typography variant="body2" fontWeight="bold" fontSize="19px">
                                총 할부 신청금액
                              </Typography>
                              <Typography
                                variant="body2"
                                fontWeight="bold"
                                fontSize="24px"
                                marginLeft={13}>
                                {`${Number(
                                  detailProduct.productBasicInfo.price,
                                ).toLocaleString()}만원`}
                              </Typography>
                            </div>
                          </div>
                          <div style={{ marginTop: '30px' }}>
                            <div style={{ display: 'flex', alignItems: 'center', marginTop: '3%' }}>
                              <Typography variant="body2" fontWeight="bold" fontSize="19px">
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

                        <Button
                          onClick={() => {
                            window.open(gatherTownUrl);
                          }}
                          sx={{
                            ...buttonStyle3,
                            marginTop: '8%',
                            color: 'white',
                            fontWeight: 'bold',
                            marginRight: '9%',
                          }}
                          variant="outlined"
                          fontSize="27px">
                          메타버스 금융상담 받기
                        </Button>
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
                            신용카드 결제는 <span style={{ color: '#F95700' }}>우리카드</span>로만
                            할 수 있습니다
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
                            onClick={() => {
                              window.open(wooriCardUrl);
                            }}
                            sx={{
                              ...buttonStyle4,
                              marginTop: '10px',
                              color: 'white',
                              fontWeight: 'bold',
                            }}
                            variant="outlined">
                            우리카드 신규 발급
                          </Button>
                          <Button
                            onClick={() => {
                              window.open(wooriCardEvent);
                            }}
                            sx={{
                              ...buttonStyle4,
                              marginTop: '10px',
                              color: 'white',
                              fontWeight: 'bold',
                            }}
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
                      <div
                        style={{
                          width: '100px', // 원하는 너비
                          height: '30px', // 원하는 높이
                          backgroundColor: '#FFF9F7', // 연한 회색 배경색
                          borderRadius: '25px', // 50%로 설정하여 타원형 모양 생성
                          display: 'flex',
                          justifyContent: 'center',
                          alignItems: 'center',
                          marginLeft: '5%',
                          marginTop: '6%',
                        }}>
                        <span style={{ color: '#F95700', marginBottom: '4%' }}>우리은행</span>
                      </div>
                      <Typography
                        variant="h6"
                        component="h4"
                        fontWeight="bold"
                        marginLeft={'5%'}
                        marginTop={'3%'}>
                        우리드림카대출[중고차]
                      </Typography>
                      <Typography color={'gray'} marginLeft={'5%'}>
                        중고차 구입을 위한 자동차 대출
                      </Typography>
                      <div
                        style={{
                          display: 'flex',
                          flexDirection: 'row',
                          marginTop: '10px',
                          marginTop: '5%',
                          marginLeft: '5%',
                        }}>
                        <Button
                          sx={{ ...buttonStyle5, color: '#F95700' }}
                          variant="outlined"
                          onClick={() => {
                            window.open(loanItem1Url);
                          }}>
                          상품안내
                        </Button>
                        <Button
                          sx={{ ...buttonStyle6, color: '#FFFFFF', marginLeft: '2%' }}
                          variant="outlined"
                          onClick={() => {
                            window.open(gatherTownUrl);
                          }}>
                          상담하러 가기
                        </Button>
                      </div>
                    </Box>
                  </Grid>
                  <Grid item>
                    <Box sx={boxStyle2} variant="outlined" marginTop={2}>
                      <div
                        style={{
                          width: '140px', // 원하는 너비
                          height: '30px', // 원하는 높이
                          backgroundColor: '#FFF9F7', // 연한 회색 배경색
                          borderRadius: '25px', // 50%로 설정하여 타원형 모양 생성
                          display: 'flex',
                          justifyContent: 'center',
                          alignItems: 'center',
                          marginLeft: '5%',
                          marginTop: '6%',
                        }}>
                        <span style={{ color: '#F95700', marginBottom: '4%' }}>우리금융캐피탈</span>
                      </div>
                      <Typography
                        variant="h6"
                        component="h4"
                        fontWeight="bold"
                        marginLeft={'5%'}
                        marginTop={'3%'}>
                        우리WON카 중고차대출
                      </Typography>
                      <Typography color={'gray'} marginLeft={'5%'}>
                        중고차 구입을 위한 최상의 금융 서비스
                      </Typography>
                      <div
                        style={{
                          display: 'flex',
                          flexDirection: 'row',
                          marginTop: '10px',
                          marginTop: '5%',
                          marginLeft: '5%',
                        }}>
                        <Button
                          sx={{ ...buttonStyle5, color: '#F95700' }}
                          variant="outlined"
                          onClick={() => {
                            window.open(loanItem2Url);
                          }}>
                          상품안내
                        </Button>
                        <Button
                          sx={{ ...buttonStyle6, color: '#FFFFFF', marginLeft: '2%' }}
                          variant="outlined"
                          onClick={() => {
                            window.open(gatherTownUrl);
                          }}>
                          상담하러 가기
                        </Button>
                      </div>
                    </Box>
                  </Grid>
                </Grid>
                <Grid container spacing={2}>
                  <Grid item>
                    <Box sx={boxStyle2} variant="outlined" marginTop={2}>
                      <div
                        style={{
                          width: '100px', // 원하는 너비
                          height: '30px', // 원하는 높이
                          backgroundColor: '#FFF9F7', // 연한 회색 배경색
                          borderRadius: '25px', // 50%로 설정하여 타원형 모양 생성
                          display: 'flex',
                          justifyContent: 'center',
                          alignItems: 'center',
                          marginLeft: '5%',
                          marginTop: '6%',
                        }}>
                        <span style={{ color: '#F95700', marginBottom: '4%' }}>우리은행</span>
                      </div>
                      <Typography
                        variant="h6"
                        component="h4"
                        fontWeight="bold"
                        marginLeft={'5%'}
                        marginTop={'3%'}>
                        우리드림카대출[전환대출]
                      </Typography>
                      <Typography color={'gray'} marginLeft={'5%'}>
                        기존 자동차할부를 바꾸기 위한 자동차대출
                      </Typography>
                      <div
                        style={{
                          display: 'flex',
                          flexDirection: 'row',
                          marginTop: '10px',
                          marginTop: '5%',
                          marginLeft: '5%',
                        }}>
                        <Button
                          sx={{ ...buttonStyle5, color: '#F95700' }}
                          variant="outlined"
                          onClick={() => {
                            window.open(loanItem3Url);
                          }}>
                          상품안내
                        </Button>
                        <Button
                          sx={{ ...buttonStyle6, color: '#FFFFFF', marginLeft: '2%' }}
                          variant="outlined"
                          onClick={() => {
                            window.open(gatherTownUrl);
                          }}>
                          상담하러 가기
                        </Button>
                      </div>
                    </Box>
                  </Grid>
                  <Grid item>
                    <Box sx={boxStyle2} variant="outlined" marginTop={2}>
                      <div
                        style={{
                          width: '140px', // 원하는 너비
                          height: '30px', // 원하는 높이
                          backgroundColor: '#FFF9F7', // 연한 회색 배경색
                          borderRadius: '25px', // 50%로 설정하여 타원형 모양 생성
                          display: 'flex',
                          justifyContent: 'center',
                          alignItems: 'center',
                          marginLeft: '5%',
                          marginTop: '6%',
                        }}>
                        <span style={{ color: '#F95700', marginBottom: '4%' }}>우리금융캐피탈</span>
                      </div>
                      <Typography
                        variant="h6"
                        component="h4"
                        fontWeight="bold"
                        marginLeft={'5%'}
                        marginTop={'3%'}>
                        우리WON카 대출(신용+담보 혼합)
                      </Typography>
                      <Typography color={'gray'} marginLeft={'5%'}>
                        중고차 구입을 위한 최적의 금융 상품
                      </Typography>
                      <div
                        style={{
                          display: 'flex',
                          flexDirection: 'row',
                          marginTop: '10px',
                          marginTop: '5%',
                          marginLeft: '5%',
                        }}>
                        <Button
                          sx={{ ...buttonStyle5, color: '#F95700' }}
                          variant="outlined"
                          onClick={() => {
                            window.open(loanItem4Url);
                          }}>
                          상품안내
                        </Button>
                        <Button
                          sx={{ ...buttonStyle6, color: '#FFFFFF', marginLeft: '2%' }}
                          variant="outlined"
                          onClick={() => {
                            window.open(gatherTownUrl);
                          }}>
                          상담하러 가기
                        </Button>
                      </div>
                    </Box>
                  </Grid>
                </Grid>
              </Grid>
              <div
                style={{
                  position: 'sticky', // 스크롤에 따라 고정되도록 설정
                  top: '84px', // 화면 상단에 고정
                  height: '550px', // 컨테이너의 높이를 화면 높이로 설정
                  width: '450px', // 원하는 사이드바의 너비 설정
                  backgroundColor: 'white', // 배경색
                  border: '1px solid #ccc', // 테두리 색상 및 두께 설정
                  borderRadius: '5px', // 모서리를 둥글게
                  padding: '20px', // 여백 설정
                  marginLeft: '1%',
                  // marginRight: '1%',
                  display: 'flex',
                  flexDirection: 'column',
                }}>
                <div>
                  <Typography
                    sx={productDetailCss.productTitle}
                    variant="h5"
                    component="h5"
                    fontWeight="bold">
                    {detailProduct.productBasicInfo.title}
                  </Typography>
                  <div
                    style={{
                      backgroundColor: '#FFF9F7',
                      borderRadius: '5px',
                      padding: '16px',
                    }}>
                    <Typography gutterBottom variant="h6" mr={2} fontWeight="bold">
                      차량 사고 내역
                    </Typography>

                    {
                      <Typography
                        gutterBottom
                        variant="body2"
                        style={{ borderBottom: '1px solid gray' }}
                        fontWeight="bold">
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
                        style={{
                          borderBottom: '1px solid gray',
                          marginTop: '2em',
                          position: 'relative',
                        }}
                        fontWeight="bold">
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
                  </div>
                  <div
                    style={{
                      backgroundColor: '#FFF9F7',
                      borderRadius: '5px',
                      padding: '16px',
                      marginTop: '2%',
                    }}>
                    <Box>
                      <Typography gutterBottom variant="h6" mr={2} fontWeight="bold">
                        차량 옵션 정보
                      </Typography>
                      {detailProduct.productOptionInfo.map((optionList, idx) => {
                        return (
                          <Typography key={idx} gutterBottom variant="body2">
                            {/* <CheckBoxIcon
                                style={{ fontSize: '20px', color: 'gray', marginLeft: '5px' }}
                              /> */}
                            {`${optionList.whether === 1 ? `${optionList.option}` : ''}`}
                          </Typography>
                        );
                      })}
                    </Box>
                  </div>
                </div>

                <Button
                  onClick={handleClickModal}
                  mt={5}
                  variant="contained"
                  style={{
                    ...buttonStyle,
                    width: '90%',
                    marginLeft: '5%',
                    marginTop: '3%',
                    fontWeight: 'bold',
                  }} // width 속성 추가
                >
                  구매 신청
                </Button>
              </div>
            </Grid>
          </Box>

          {/* 금융정보 & 광고 item box */}
          {/* <Box sx={productDetailCss.detailBox}>
            <Grid
              container
              display="flex"
              // alignItems="center"
              // justifyContent="space-between"
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
          </Box> */}

          {/* 차량 상세 정보 item (전체 정보 & option) box */}
          {/* <Box sx={productDetailCss.detailBox}>
            <Grid container spacing={2} alignItems="flex-start" justifyContent="center" mb={2}>
              <Grid item xs={12} sm={12} md={4}>
                <Card sx={productDetailCss.card}></Card>
              </Grid>
              <Grid item xs={12} sm={12} md={4}></Grid>
              <Grid item xs={12} sm={12} md={4}>
                <Card sx={productDetailCss.card}></Card>
              </Grid>
            </Grid>
          </Box> */}
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
