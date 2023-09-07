import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Box, Button, Card, CardMedia, Grid, Stack, TextField, Typography } from '@mui/material';
import {
  mypageProductEditRequestGetApi,
  mypageProductEditRequestPatchApi,
} from '@/services/mypageApi';

function RegisteredEdit() {
  const router = useRouter();
  const { memberId, productId } = router.query; // query string
  const [mounted, setMounted] = useState(false);
  const [priceValue, setPriceValue] = useState('');
  const [mypageProductEditRequest, setMypageProductEditRequest] = useState({
    title: '',
    price: '',
    carImage: '',
  });

  // PATCH
  // const [editPriceValue, setEditPriceValue] = useState({
  //   updatePrice: null,
  // });
  // const [updatePrice, setUpdatePrice] = useState(null);

  // Modal 버튼 클릭 유무
  // const [showModal, setShowModal] = useState(false);
  // const handleClickModal = () => setShowModal(showModal);

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
    memberId &&
      mypageProductEditRequestGetApi(memberId, productId).then((data) => {
        setMypageProductEditRequest({
          title: data.title,
          price: data.price,
          carImage: data.carImage,
        });
      });
    setMounted(true);
  }, []);

  const handleChangePrice = (e) => {
    setPriceValue(e.target.value);
  };

  // /**
  //    * 수정 승인
  //    */
  // const handleApproveEdit = async () => {
  //   try {
  //     await editApproveProductApplicationsPatchApi(currentProductId).then((res) => {
  //       if (res.status === 200) {
  //         alert(res.data);
  //         router.push(`/admin/product`);
  //       }
  //     });
  //   } catch (error) {
  //     console.log('실패');
  //   }
  // };

  /**
   * 수정 form 제출
   */
  const handleSubmit = async () => {
    const updatePrice = {
      updatePrice: priceValue,
    };
    try {
      await mypageProductEditRequestPatchApi(updatePrice, memberId, productId).then((response) => {
        console.log(response);
        console.log('res');
        if (response.status === 200) {
          alert(response.data);
          router.push(`/mypage/registered/${memberId}`);
        }
      });
    } catch (error) {
      console.log(error);
    }
  };

  return (
    mounted &&
    memberId && (
      <>
        <Typography sx={registeredEditCss.mypageTitle} component="h4" variant="h4" gutterBottom>
          마이페이지 - 등록된 게시글 가격 수정요청
        </Typography>
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
                    type="number"
                    margin="normal"
                    required
                    name="updatePrice"
                    label="가격 수정"
                    value={priceValue || ''}
                    fullWidth
                    onChange={(e) => handleChangePrice(e)}
                  />
                </Grid>
                <Button
                  onClick={handleSubmit}
                  type="submit"
                  size="large"
                  variant="contained"
                  sx={registeredEditCss.button}>
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
