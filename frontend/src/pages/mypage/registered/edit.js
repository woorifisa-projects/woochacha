import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Box, Button, Card, CardMedia, Grid, Stack, TextField, Typography } from '@mui/material';
import {
  mypageProductEditRequestGetApi,
  mypageProductEditRequestPatchApi,
} from '@/services/mypageApi';
import { SwalModals } from '@/utils/modal';
import Swal from 'sweetalert2';
import { debounce } from 'lodash';
function RegisteredEdit(props) {
  const router = useRouter();
  const { memberId, productId } = props; // query string
  const [mounted, setMounted] = useState(false);
  const [priceValue, setPriceValue] = useState('');
  const [mypageProductEditRequest, setMypageProductEditRequest] = useState({
    title: '',
    price: '',
    carImage: '',
  });
  const [modifyPriceButton, setModifyPriceButton] = useState(false);
  const [verificationNum, setVerificationNum] = useState('');

  const handleVerificationNumChange = (event) => {
    const number = event.target.value.replace(/[^0-9]/g, '');
    setVerificationNum(number);
  };

  const registeredEditCss = {
    mypageTitle: {
      my: 10,
      color: '#1490ef',
      fontWeight: 'bold',
    },
    mypagesubTitle: {
      fontWeight: 'bold',
      mb: 3,
    },
    card: {
      maxWidth: '50rem',
      maxHeight: '45rem',
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'center',
      alignItems: 'center',
      marginBottom: 10,
      paddingY: 8,
      textAlign: 'center',
      boxShadow: 7,
      borderRadius: '3rem',
    },
    cardMedia: {
      width: '15rem',
      height: '15rem',
      borderRadius: '1rem',
    },
    gridBox: {
      mb: 7,
    },
    priceBox: {
      display: 'flex',
      px: 5,
      pl: 5,
      pr: 5,
      flexDirection: 'column',
      justifyContent: 'center',
    },
    button: { mt: 3, mb: 2 },
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    mypageProductEditRequestGetApi(memberId, productId).then((res) => {
      if (res.status === 200) {
        setMypageProductEditRequest({
          title: res.data.title,
          price: res.data.price,
          carImage: res.data.carImage,
        });
      }
    });
    setMounted(true);
  }, []);

  const handleChangePrice = (e) => {
    setPriceValue(e.target.value);
  };

  /**
   * 수정 form 제출
   */
  const handleSubmit = async () => {
    setModifyPriceButton(true);
    console.log(mypageProductEditRequest.price);
    if (priceValue === '') {
      Swal.fire({
        icon: 'error',
        title: '가격을 설정해주셔야합니다',
        showConfirmButton: true,
        // timer: 1500,
      });
      setModifyPriceButton(false);
      return;
    } else if (priceValue > mypageProductEditRequest.price) {
      Swal.fire({
        icon: 'error',
        title: '기존 차량 가격보다 비싸게 판매할 수 없습니다.',
        showConfirmButton: true,
        // timer: 1500,
      });
      setModifyPriceButton(false);
      return;
    }
    const updatePrice = {
      updatePrice: priceValue,
    };
    try {
      await mypageProductEditRequestPatchApi(updatePrice, memberId, productId).then((response) => {
        console.log(response);
        console.log('res');
        if (response.status === 200) {
          SwalModals('success', '수정 제출', response.data, false);
          router.push(`/mypage/registered/${memberId}`);
          return;
        }
      });
    } catch (error) {
      console.log(error);
    }
  };

  return (
    mounted && (
      <>
        <Card sx={registeredEditCss.card}>
          <Typography
            sx={registeredEditCss.mypagesubTitle}
            component="h5"
            variant="h5"
            gutterBottom>
            {mypageProductEditRequest.title}
          </Typography>
          <Grid container spacing={2} noValidate justifyContent="center">
            <Grid item xs={12} md={6}>
              <Stack direction="column" alignItems="center" spacing={2} mb={5}>
                <CardMedia
                  sx={registeredEditCss.cardMedia}
                  image={mypageProductEditRequest.carImage}
                  title="게시글 이미지"
                />
              </Stack>
            </Grid>
            <Grid item xs={12} md={6}>
              <Box sx={registeredEditCss.priceBox}>
                <Grid sx={registeredEditCss.gridBox}>
                  <Typography variant="h6" component="h6" textAlign="left" fontWeight="bold">
                    현재 가격
                  </Typography>
                  <Typography textAlign="left">{`${mypageProductEditRequest.price} 만원`}</Typography>
                </Grid>
                <Grid>
                  <Typography variant="h6" component="h6" textAlign="left" fontWeight="bold">
                    수정할 가격
                  </Typography>
                  <TextField
                    type="text"
                    margin="normal"
                    required
                    name="updatePrice"
                    label="가격 수정"
                    value={verificationNum}
                    fullWidth
                    onChange={handleVerificationNumChange}
                    InputProps={{
                      endAdornment: (
                        <Typography style={{ color: 'gray', whiteSpace: 'nowrap' }}>
                          만원
                        </Typography>
                      ),
                      style: { display: 'flex', alignItems: 'center' },
                    }}
                  />
                </Grid>
                <Button
                  onClick={handleSubmit}
                  type="submit"
                  size="large"
                  variant="contained"
                  sx={registeredEditCss.button}
                  disabled={modifyPriceButton}>
                  가격 수정하기
                </Button>
              </Box>
            </Grid>
          </Grid>
        </Card>
      </>
    )
  );
}

// side menu 레이아웃
RegisteredEdit.Layout = withAuth(UserMyPageLayout);
export default RegisteredEdit;

export async function getServerSideProps(context) {
  const query = context.query;
  return {
    props: {
      memberId: query.memberId,
      productId: query.productId,
    },
  };
}
