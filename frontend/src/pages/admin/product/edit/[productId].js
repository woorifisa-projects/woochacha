import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { Box, Button, Card, CardMedia, Grid, Stack, TextField, Typography } from '@mui/material';
import withAdminAuth from '@/hooks/withAdminAuth';
import { useRouter } from 'next/router';
import { EDIT_MODAL } from '@/constants/string';
import BasicModal from '@/components/common/BasicModal';
import { SwalModals } from '@/utils/modal';

function AdminProductEdit(props) {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const { productId } = props;

  // Modal 버튼 클릭 유무
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);

  /**
   * 수정 form 제출
   */
  const handleSubmit = async () => {
    try {
      // await submitEditRegistered(memberId, productId, updateData);
      SwalModals('success', '수정 반려 완료', '가격 수정 요청이 완료되었습니다!', false).then(() =>
        router.push('/admin/product'),
      );
    } catch (error) {
      console.log('실패');
    }
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  // DUMMY_DATA
  const dummyData = {
    title: '기아 모닝 2010년형',
    imageURL: 'https://woochacha.s3.ap-northeast-2.amazonaws.com/product/22%EB%82%982222/1',
    currentPrice: '3200',
  };

  const adminProductEditCss = {
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

  return (
    mounted && (
      <>
        <Card sx={adminProductEditCss.card}>
          <Typography
            sx={adminProductEditCss.mypagesubTitle}
            component="h5"
            variant="h5"
            gutterBottom>
            {dummyData.title}
          </Typography>
          <Grid container spacing={2} noValidate justifyContent="center">
            <Grid item xs={12} md={6}>
              <Stack direction="column" alignItems="center" spacing={2} mb={5}>
                <CardMedia
                  sx={adminProductEditCss.cardMedia}
                  image={dummyData.imageURL}
                  title="게시글 이미지"
                />
              </Stack>
            </Grid>
            <Grid item xs={12} md={6}>
              <Box sx={adminProductEditCss.priceBox}>
                <Grid sx={adminProductEditCss.gridBox}>
                  <Typography variant="h6" component="h6" textAlign="left" fontWeight="bold">
                    현재 가격
                  </Typography>
                  <Typography textAlign="left">{`${dummyData.currentPrice} 만원`}</Typography>
                </Grid>
                <Grid>
                  <Typography variant="h6" component="h6" textAlign="left" fontWeight="bold">
                    수정 신청한 가격
                  </Typography>
                  <Typography textAlign="left">{`${dummyData.currentPrice} 만원`}</Typography>
                </Grid>
              </Box>
              <Grid item xs={12} md={6}>
                <Button
                  onClick={handleClickModal}
                  // type="submit"
                  size="large"
                  variant="contained"
                  sx={adminProductEditCss.button}>
                  가격 수정 승인하기
                </Button>
                <Button
                  onClick={handleClickModal}
                  // type="submit"
                  size="large"
                  variant="contained"
                  sx={adminProductEditCss.button}>
                  가격 수정 승인하기
                </Button>
              </Grid>
            </Grid>
          </Grid>
        </Card>
        {showModal && (
          <BasicModal
            onClickModal={handleClickModal}
            isOpen={showModal}
            modalContent={EDIT_MODAL.CONTENTS}
            callBackFunc={handleSubmit}
          />
        )}
      </>
    )
  );
}

// side menu 레이아웃
AdminProductEdit.Layout = withAdminAuth(AdminPageLayout);
export default AdminProductEdit;

export async function getServerSideProps(context) {
  const productId = context.params.productId;
  return {
    props: {
      productId,
    },
  };
}
