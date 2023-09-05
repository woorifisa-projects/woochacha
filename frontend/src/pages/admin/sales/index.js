import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { CssBaseline, ThemeProvider, Typography, responsiveFontSizes } from '@mui/material';
import theme from '@/styles/theme';
import withAdminAuth from '@/hooks/withAdminAuth';
import { useRouter } from 'next/router';
import { ADMIN_DENY_MODAL } from '@/constants/string';
import SaleTable from '@/components/admin/SaleTable';
import BasicModal from '@/components/common/BasicModal';

function AdminSalesList() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  let responsiveFontTheme = responsiveFontSizes(theme);

  // Modal 버튼 클릭 유무
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);

  /**
   * 수정 form 제출
   */
  const handleDeny = async () => {
    try {
      // await adminDeleteProduct(memberId, productId, updateData);
      alert('반려 완료');
      router.push(`/admin/sales`);
    } catch (error) {
      console.log('실패');
    }
  };

  const mypageCss = {
    mypageTitle: {
      my: 10,
      color: '#1490ef',
      fontWeight: 'bold',
    },
  };

  const table_cell_data = [
    {
      headerLabel: '판매요청자',
      contentCell: 'seller',
    },
    {
      headerLabel: '차량번호',
      contentCell: 'carNum',
    },
    {
      headerLabel: '차고지',
      contentCell: 'branch',
    },
    {
      headerLabel: '상태',
      contentCell: 'status',
    },
    {
      headerLabel: '등록여부',
      contentCell: 'isApproved',
    },
  ];

  // TODO: axios로 data 받아온 데이터라고 가정
  const dummy_content_data = {
    content: [
      {
        id: 1,
        seller: '홍속초',
        carNum: '11가1234',
        branch: '서울',
        status: '상태내용',
        isApproved: 1,
      },
      {
        id: 2,
        seller: '김속초',
        carNum: '11가1234',
        branch: '서울',
        status: '상태내용',
        isApproved: 0,
      },
      {
        id: 3,
        seller: '박속초',
        carNum: '11가1234',
        branch: '서울',
        status: '상태내용',
        isApproved: 1,
      },
    ],
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    mounted && (
      <ThemeProvider theme={responsiveFontTheme}>
        <CssBaseline />
        <Typography sx={mypageCss.mypageTitle} component="h4" variant="h4" gutterBottom>
          관리자 페이지 - 판매 신청 목록
        </Typography>
        <SaleTable
          headerData={table_cell_data}
          contentData={dummy_content_data.content}
          moveUrl={`/admin/sales/approve/`}
          callbackFunc={handleClickModal}
        />
        {showModal && (
          <BasicModal
            onClickModal={handleClickModal}
            isOpen={showModal}
            modalContent={ADMIN_DENY_MODAL.CONTENTS}
            callBackFunc={handleDeny}
          />
        )}
      </ThemeProvider>
    )
  );
}

// side menu 레이아웃
AdminSalesList.Layout = withAdminAuth(AdminPageLayout);
export default AdminSalesList;
