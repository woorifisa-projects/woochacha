import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import { Box, Grid, TextField, Typography } from '@mui/material';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import withAdminAuth from '@/hooks/withAdminAuth';
import PurchaseTable from '@/components/admin/PurchaseTable';
import BasicModal from '@/components/common/BasicModal';
import { todayDate } from '@/utils/date';
import { ADMIN_PURCHASE_CONFIRM_MODAL, ADMIN_PURCHASE_MODAL } from '@/constants/string';
import { purchaseRequest } from '@/services/productApi';

function AdminPurchaseList() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const [purchaseDateVal, setPurchaseDateVal] = useState(todayDate);
  const [confirmFlag, setConfirmFlag] = useState(false);
  const [confirmData, setConfirmData] = useState();
  const [purchaseData, setPurchaseData] = useState();

  // Modal 버튼 클릭 유무
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);

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
      router.push(`/admin/purchase`);
    });
  };

  /**
   * 날짜 관련 함수
   */
  const handleChangeDate = (e) => {
    setPurchaseDateVal(e.target.value);
    console.log(purchaseDateVal);
  };

  const adminPurchaseCss = {
    mainTitle: {
      my: 10,
      color: '#1490ef',
      fontWeight: 'bold',
    },
  };

  const table_cell_data = [
    {
      headerLabel: '게시글',
      contentCell: 'productId',
    },
    {
      headerLabel: '구매자',
      contentCell: 'buyer',
    },
    {
      headerLabel: '판매자',
      contentCell: 'seller',
    },
    {
      headerLabel: '검토여부',
      contentCell: 'isConfirm',
    },
    {
      headerLabel: '거래여부',
      contentCell: 'transactionStatus',
    },
  ];

  // TODO: 검토여부 get 하는 함수
  const getConfirmData = () => {
    // setConfirmData();
    // dummy_confirm_data 로 대체
  };

  // TODO: 거래여부 get 하는 함수
  const getPurchaseData = () => {
    // setPurchaseData();
    // dummy_purchase_data 로 대체
  };

  // TODO: axios로 data 받아온 데이터라고 가정
  const dummy_content_data = {
    content: [
      {
        productId: '12',
        buyer: '김구매',
        seller: '김판매',
        isConfirm: 0,
        transactionStatus: 0,
      },
      {
        productId: '13',
        buyer: '박구매',
        seller: '박판매',
        isConfirm: 1,
        transactionStatus: 1,
      },
      {
        productId: '14',
        buyer: '이구매',
        seller: '이판매',
        isConfirm: 0,
        transactionStatus: 0,
      },
    ],
  };

  // TODO: dummy
  const dummy_confirm_data = {
    sellerName: '김김김',
    sellerPhone: '01022223333',
    buyerName: '인인인',
    buyerPhone: '01012123333',
  };

  // TODO: dummy
  const dummy_purchase_data = {
    sellerName: '김김김',
    sellerPhone: '01022223333',
    buyerName: '인인인',
    buyerPhone: '01012123333',
    meetingDate: '2099-12-12',
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    mounted && (
      <>
        <Typography sx={adminPurchaseCss.mainTitle} component="h4" variant="h4" gutterBottom>
          관리자 페이지 - [거래관리] 구매 요청 리스트
        </Typography>
        <PurchaseTable
          headerData={table_cell_data}
          contentData={dummy_content_data.content}
          callbackFunc={handleClickModal}
          getConfirmData={getConfirmData}
          getPurchaseData={getPurchaseData}
          setConfirmFlag={setConfirmFlag}
          moveDetailUrl={`/product/detail/`}
        />
        {showModal && (
          <BasicModal
            onClickModal={handleClickModal}
            isOpen={showModal}
            modalContent={
              confirmFlag ? ADMIN_PURCHASE_CONFIRM_MODAL.CONTENTS : ADMIN_PURCHASE_MODAL.CONTENTS
            }
            callBackFunc={handlePurchaseRequest}>
            {/* 모달 children */}
            {confirmFlag ? (
              // 검토여부 modal children
              <Box>
                <Grid container spacing={2} noValidate justifyContent="center" alignItems="center">
                  <Grid item xs={12} md={6}>
                    <Typography variant="h6" component="h6" textAlign="left" fontWeight="bold">
                      판매자
                    </Typography>
                    <Typography textAlign="left">{`${dummy_confirm_data.sellerName}`}</Typography>
                    <Typography textAlign="left">{`${dummy_confirm_data.sellerPhone}`}</Typography>
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Grid>
                      <Typography variant="h6" component="h6" textAlign="left" fontWeight="bold">
                        구매자
                      </Typography>
                      <Typography textAlign="left">{`${dummy_confirm_data.buyerName}`}</Typography>
                      <Typography textAlign="left">{`${dummy_confirm_data.buyerPhone}`}</Typography>
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
              <Box>
                <Grid container spacing={2} noValidate justifyContent="center" alignItems="center">
                  <Grid item xs={12} md={6}>
                    <Typography variant="h6" component="h6" textAlign="left" fontWeight="bold">
                      판매자
                    </Typography>
                    <Typography textAlign="left">{`${dummy_purchase_data.sellerName}`}</Typography>
                    <Typography textAlign="left">{`${dummy_purchase_data.sellerPhone}`}</Typography>
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Grid>
                      <Typography variant="h6" component="h6" textAlign="left" fontWeight="bold">
                        구매자
                      </Typography>
                      <Typography textAlign="left">{`${dummy_purchase_data.buyerName}`}</Typography>
                      <Typography textAlign="left">{`${dummy_purchase_data.buyerPhone}`}</Typography>
                    </Grid>
                  </Grid>
                </Grid>
                <Typography pt={3} variant="h6" component="h6" textAlign="left" fontWeight="bold">
                  거래날짜
                </Typography>
                <Typography textAlign="left">{`${dummy_purchase_data.meetingDate}`}</Typography>
              </Box>
            )}
          </BasicModal>
        )}
      </>
    )
  );
}

// side menu 레이아웃
AdminPurchaseList.Layout = withAdminAuth(AdminPageLayout);
export default AdminPurchaseList;
