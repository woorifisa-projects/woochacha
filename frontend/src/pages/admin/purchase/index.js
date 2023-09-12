import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import { Box, Grid, TextField, Typography } from '@mui/material';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import withAdminAuth from '@/hooks/withAdminAuth';
import PurchaseTable from '@/components/admin/PurchaseTable';
import OneButtonModal from '@/components/common/OneButtonModal';
import { todayDate } from '@/utils/date';
import { ADMIN_PURCHASE_CONFIRM_MODAL, ADMIN_PURCHASE_MODAL } from '@/constants/string';
import {
  allPurchaseFormGetApi,
  onePurchaseConfirmFormGetApi,
  onePurchaseConfirmFormPatchApi,
  onePurchaseTransactionFormPostApi,
} from '@/services/adminpageApi';
import { SwalModals } from '@/utils/modal';

function AdminPurchaseList() {
  const [mounted, setMounted] = useState(false);
  const [purchaseDateVal, setPurchaseDateVal] = useState(todayDate);
  const [confirmFlag, setConfirmFlag] = useState(false);
  const [confirmData, setConfirmData] = useState(); // 검토여부 관련 get data
  const [allPurchaseFormInfo, setAllPurchaseFormInfo] = useState();
  const [currentPurchaseId, setCurrentPurchaseId] = useState();
  const router = useRouter();

  // Modal 버튼 클릭 유무
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);

  // pagination
  const [page, setPage] = useState(0); // 현재 페이지
  const [size, setSize] = useState(10); // 기본 사이즈

  /**
   * 구매요청 관련 함수
   */
  const handlePurchaseRequest = () => {
    const purchaseForm = {
      purchaseId: currentPurchaseId,
      meetingDate: purchaseDateVal, // 날짜는 일단 넣어놨습니다.
    };
    onePurchaseConfirmFormPatchApi(currentPurchaseId, purchaseForm).then((res) => {
      console.log(res);
      if (res.status === 200) {
        SwalModals('success', '수정 요청 완료', res.data, false);
        router.push('/admin/purchase');
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

  /**
   * (거래관리 - 성사) 관련 함수
   */
  const handleTransactionApprove = () => {
    onePurchaseTransactionFormPostApi(currentPurchaseId).then((res) => {
      console.log(res);
      if (res.status === 200) {
        SwalModals('success', '거래 성사 완료', res.data, false);
        router.push('/admin/purchase');
      }
    });
  };

  /**
   * 검토여부 관련 정보 get 함수
   */
  const getConfirmData = (purchaseId) => {
    onePurchaseConfirmFormGetApi(purchaseId).then((res) => {
      if (res.status === 200) {
        console.log(res);
        console.log(res.data);
        setConfirmData(res.data);
      }
    });
  };

  /**
   * 거래관리 요청 목록 조회
   */
  useEffect(() => {
    if (!mounted) {
      allPurchaseFormGetApi(0, 10).then((res) => {
        if (res.status === 200) {
          setAllPurchaseFormInfo(res.data);
        }
      });
      setMounted(true);
    } else {
      allPurchaseFormGetApi(page, size).then((res) => {
        if (res.status === 200) {
          setAllPurchaseFormInfo(res.data);
        }
      });
    }
  }, [page, size]);

  const table_cell_data = [
    {
      headerLabel: '게시글',
      contentCell: 'carNum',
    },
    {
      headerLabel: '구매자',
      contentCell: 'buyerName',
    },
    {
      headerLabel: '판매자',
      contentCell: 'sellerName',
    },
    {
      headerLabel: '검토여부',
      contentCell: 'purchaseStatus',
    },
    {
      headerLabel: '거래여부',
      contentCell: 'transactionStatus',
    },
  ];

  return (
    mounted &&
    allPurchaseFormInfo && (
      <>
        <PurchaseTable
          headerData={table_cell_data}
          contentData={allPurchaseFormInfo}
          callbackFunc={handleClickModal}
          getConfirmData={getConfirmData}
          setConfirmFlag={setConfirmFlag}
          moveDetailUrl={`/product/detail/`}
          setCurrentPurchaseId={setCurrentPurchaseId}
          page={page}
          size={size}
          setPage={setPage}
          setSize={setSize}
        />
        {showModal && (
          <OneButtonModal
            onClickModal={handleClickModal}
            isOpen={showModal}
            modalContent={
              confirmFlag ? ADMIN_PURCHASE_CONFIRM_MODAL.CONTENTS : ADMIN_PURCHASE_MODAL.CONTENTS
            }
            callBackFunc={confirmFlag ? handlePurchaseRequest : handleTransactionApprove}>
            {/* 모달 children */}
            {confirmFlag && confirmData ? (
              // 검토여부 modal children
              <Box>
                <Grid container spacing={2} noValidate justifyContent="center" alignItems="center">
                  <Grid item xs={12} md={6}>
                    <Typography variant="h6" component="h6" textAlign="left" fontWeight="bold">
                      판매자
                    </Typography>
                    <Typography textAlign="left">{`${confirmData.sellerName}`}</Typography>
                    <Typography textAlign="left">{`${confirmData.sellerPhone}`}</Typography>
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Grid>
                      <Typography variant="h6" component="h6" textAlign="left" fontWeight="bold">
                        구매자
                      </Typography>
                      <Typography textAlign="left">{`${confirmData.buyerName}`}</Typography>
                      <Typography textAlign="left">{`${confirmData.buyerPhone}`}</Typography>
                    </Grid>
                  </Grid>
                </Grid>
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
              </Box>
            ) : (
              // 거래여부 modal children
              <></>
            )}
          </OneButtonModal>
        )}
      </>
    )
  );
}

// side menu 레이아웃
AdminPurchaseList.Layout = withAdminAuth(AdminPageLayout);
export default AdminPurchaseList;
