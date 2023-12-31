import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import { Box, CardMedia, Grid, Stack, Typography } from '@mui/material';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import withAdminAuth from '@/hooks/withAdminAuth';
import BasicButtonTable from '@/components/admin/ProductTable';
import { ADMIN_EDIT_MODAL, DELETE_MODAL } from '@/constants/string';
import {
  allProductApplicationsGetApi,
  deleteProductApplicationsPatchApi,
  editApproveProductApplicationsPatchApi,
  editDenyProductApplicationsPatchApi,
  editProductApplicationsGetApi,
} from '@/services/adminpageApi';
import ApproveModal from '@/components/common/ApproveModal';
import { SwalModals } from '@/utils/modal';
import LoadingBar from '@/components/common/LoadingBar';

function AdminProductList() {
  const [mounted, setMounted] = useState(false);
  const [allProductApplications, setAllProductApplications] = useState();
  const [editProductApplication, setEditProductApplication] = useState();
  const [currentProductId, setCurrentProductId] = useState();
  const router = useRouter();

  // Modal 버튼 클릭 유무
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);
  const [editFlag, setEditFlag] = useState();

  // pagination
  const [page, setPage] = useState(0); // 현재 페이지
  const [size, setSize] = useState(10); // 기본 사이즈

  /**
   * 삭제요청 삭제
   */
  const handleDelete = async () => {
    try {
      await deleteProductApplicationsPatchApi(currentProductId).then((res) => {
        if (res.status === 200) {
          SwalModals('success', '삭제 요청 완료', '삭제 요청이 완료되었습니다!', false);
          router.push('/admin/product');
        }
      });
    } catch (error) {
      console.log('실패');
    }
  };

  /**
   * 수정 승인
   */
  const handleApproveEdit = async () => {
    try {
      await editApproveProductApplicationsPatchApi(currentProductId).then((res) => {
        if (res.status === 200) {
          SwalModals('success', '수정 승인 완료', res.data, false);
          router.push('/admin/product');
        }
      });
    } catch (error) {
      console.log('실패');
    }
  };

  /**
   * 수정 반려
   */
  const handleDenyEdit = async () => {
    try {
      await editDenyProductApplicationsPatchApi(currentProductId).then((res) => {
        if (res.status === 200) {
          SwalModals('success', '수정 반려 완료', res.data, false);
          router.push('/admin/product');
        }
      });
    } catch (error) {
      console.log('실패');
    }
  };

  /**
   * 수정 요청 시, 수정 modal의 내부 content GET 함수
   */
  const handleGetEditData = (id) => {
    editProductApplicationsGetApi(id).then((res) => {
      if (res.status === 200) {
        setEditProductApplication(res.data);
      }
    });
  };

  const adminProductCss = {
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

  /**
   * 렌더링 시, 모든 수정, 삭제 신청 목록 조회
   */
  useEffect(() => {
    if (!mounted) {
      allProductApplicationsGetApi(0, 10).then((res) => {
        if (res.status === 200) {
          setAllProductApplications(res.data);
        }
      });
      setMounted(true);
    } else {
      allProductApplicationsGetApi(page, size).then((res) => {
        if (res.status === 200) {
          setAllProductApplications(res.data);
        }
      });
    }
  }, [page, size]);

  return mounted && allProductApplications ? (
    <>
      <BasicButtonTable
        headerData={table_cell_data}
        contentData={allProductApplications}
        deleteFunc={handleClickModal}
        editFunc={handleClickModal}
        getEditFunc={handleGetEditData}
        setEditFlag={setEditFlag}
        moveUrl={`/product/detail/`}
        setCurrentProductId={setCurrentProductId}
        currentProductId={currentProductId}
        page={page}
        size={size}
        setPage={setPage}
        setSize={setSize}
      />
      {showModal && (
        <ApproveModal
          onClickModal={handleClickModal}
          isOpen={showModal}
          modalContent={editFlag ? ADMIN_EDIT_MODAL.CONTENTS : DELETE_MODAL.CONTENTS}
          callBackOneFunc={editFlag ? () => handleApproveEdit() : () => handleDelete()}
          callBackTwoFunc={
            editFlag
              ? () => handleDenyEdit()
              : () => {
                  console.log();
                }
          }>
          {/* modal 자식 요소 - edit 의 경우, 가격 승인을 띄워주기 */}
          {editFlag && editProductApplication ? (
            <Grid container spacing={2} noValidate justifyContent="center" alignItems="center">
              <Grid item xs={12} md={6}>
                <Typography variant="h5" sx={adminProductCss.modalTitle}>
                  {editProductApplication.title}
                </Typography>
                <Stack direction="column" alignItems="center" spacing={2} mb={5}>
                  <CardMedia
                    sx={adminProductCss.modalCardMedia}
                    image={editProductApplication.imageUrl}
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
                    <Typography textAlign="left">{`${editProductApplication.price} 만원`}</Typography>
                  </Grid>
                  <Grid>
                    <Typography variant="h6" component="h6" textAlign="left" fontWeight="bold">
                      수정 신청한 가격
                    </Typography>
                    <Typography textAlign="left">
                      {editProductApplication.updatePrice !== null
                        ? editProductApplication.updatePrice
                        : '수정 신청한 가격이 없습니다!'}
                    </Typography>
                  </Grid>
                </Box>
              </Grid>
            </Grid>
          ) : (
            ''
          )}
        </ApproveModal>
      )}
    </>
  ) : (
    <LoadingBar />
  );
}

// side menu 레이아웃
AdminProductList.Layout = withAdminAuth(AdminPageLayout);
export default AdminProductList;
