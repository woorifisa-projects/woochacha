import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import { Box, CardMedia, Grid, Stack, Typography } from '@mui/material';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import withAdminAuth from '@/hooks/withAdminAuth';
import BasicModal from '@/components/common/BasicModal';
import BasicButtonTable from '@/components/admin/ProductTable';
import { ADMIN_EDIT_MODAL, DELETE_MODAL } from '@/constants/string';

function AdminProductList() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();

  // Modal 버튼 클릭 유무
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);
  const [editFlag, setEditFlag] = useState();

  /**
   * 삭제
   */
  const handleDelete = async () => {
    try {
      // await adminDeleteProduct(memberId, productId, updateData);
      alert('삭제 완료');
      router.push(`/admin/product`);
    } catch (error) {
      console.log('실패');
    }
  };

  /**
   * 수정
   */
  const handleEdit = async () => {
    try {
      // await adminEditProduct(memberId, productId, updateData);
      alert('수정 완료');
      router.push(`/admin/product`);
    } catch (error) {
      console.log('실패');
    }
  };

  // TODO: DUMMY_DATA - edit 요청 시, get해와서 보여주는 data
  const dummyData = {
    title: '기아 모닝 2010년형',
    imageURL: 'https://woochacha.s3.ap-northeast-2.amazonaws.com/product/22%EB%82%982222/1',
    price: 2690,
    updatePrice: 3200,
  };

  const adminProductCss = {
    mainTitle: {
      my: 10,
      color: '#1490ef',
      fontWeight: 'bold',
    },
    modalTitle: {
      fontWeight: 'bold',
      mb: 3,
    },
    modalCardMedia: {
      width: '15rem',
      height: '15rem',
      borderRadius: '1rem',
    },
    modalGridBox: {
      mb: 7,
    },
    modalPriceBox: {
      display: 'flex',
      px: 5,
      pl: 5,
      pr: 5,
      flexDirection: 'column',
      justifyContent: 'center',
    },
  };

  const table_cell_data = [
    {
      headerLabel: '게시글',
      contentCell: 'title',
    },
    {
      headerLabel: '판매자',
      contentCell: 'sellerName',
    },
    {
      headerLabel: '판매자 이메일',
      contentCell: 'sellerEmail',
    },
    {
      headerLabel: '관리',
      contentCell: 'manageType',
    },
  ];

  // TODO: axios로 data 받아온 데이터라고 가정
  const dummy_content_data = {
    content: [
      {
        productId: '14',
        title: '기아 올 뉴 카니발 2018년형',
        sellerName: '홍길길',
        sellerEmail: 'fisafisa@naver.com',
        manageType: 0,
      },
      {
        productId: '2',
        title: '기아 올 뉴 카니발 2018년형',
        sellerName: '홍길길',
        sellerEmail: 'fisafisa@naver.com',
        manageType: 0,
      },
      {
        productId: '3',
        title: '기아 올 뉴 카니발 2018년형',
        sellerName: '홍길길',
        sellerEmail: 'fisafisa@naver.com',
        manageType: 1,
      },
    ],
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    mounted && (
      <>
        <Typography sx={adminProductCss.mainTitle} component="h4" variant="h4" gutterBottom>
          관리자 페이지 - 매물 관리 리스트
        </Typography>
        <BasicButtonTable
          headerData={table_cell_data}
          contentData={dummy_content_data.content}
          deleteFunc={handleClickModal}
          editFunc={handleClickModal}
          setEditFlag={setEditFlag}
          moveUrl={`/product/detail/`}
        />
        {showModal && (
          <BasicModal
            onClickModal={handleClickModal}
            isOpen={showModal}
            modalContent={editFlag ? ADMIN_EDIT_MODAL.CONTENTS : DELETE_MODAL.CONTENTS}
            callBackFunc={editFlag ? handleEdit : handleDelete}>
            {/* modal 자식 요소 - edit 의 경우, 가격 승인을 띄워줘야함 */}
            {editFlag ? (
              <Grid container spacing={2} noValidate justifyContent="center" alignItems="center">
                <Grid item xs={12} md={6}>
                  <Typography variant="h5" sx={adminProductCss.modalTitle}>
                    {dummyData.title}
                  </Typography>
                  <Stack direction="column" alignItems="center" spacing={2} mb={5}>
                    <CardMedia
                      sx={adminProductCss.modalCardMedia}
                      image={dummyData.imageURL}
                      title="게시글 이미지"
                    />
                  </Stack>
                </Grid>
                <Grid item xs={12} md={6}>
                  <Box sx={adminProductCss.modalPriceBox}>
                    <Grid sx={adminProductCss.modalGridBox}>
                      <Typography variant="h6" component="h6" textAlign="left" fontWeight="bold">
                        현재 가격
                      </Typography>
                      <Typography textAlign="left">{`${dummyData.price} 만원`}</Typography>
                    </Grid>
                    <Grid>
                      <Typography variant="h6" component="h6" textAlign="left" fontWeight="bold">
                        수정 신청한 가격
                      </Typography>
                      <Typography textAlign="left">{`${dummyData.updatePrice} 만원`}</Typography>
                    </Grid>
                  </Box>
                </Grid>
              </Grid>
            ) : (
              ''
            )}
          </BasicModal>
        )}
      </>
    )
  );
}

// side menu 레이아웃
AdminProductList.Layout = withAdminAuth(AdminPageLayout);
export default AdminProductList;
