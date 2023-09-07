import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { CssBaseline, ThemeProvider, Typography, responsiveFontSizes } from '@mui/material';
import theme from '@/styles/theme';
import withAdminAuth from '@/hooks/withAdminAuth';
import { ADMIN_DENY_MODAL } from '@/constants/string';
import SaleTable from '@/components/admin/SaleTable';
import BasicModal from '@/components/common/BasicModal';
import { allSaleFormGetApi, denySaleFormPatchApi } from '@/services/adminpageApi';

function AdminSalesList() {
  const [mounted, setMounted] = useState(false);
  const [allSaleFormInfo, setAllSaleFormInfo] = useState();
  const [currentSaleFormId, setCurrentSaleFormId] = useState();
  let responsiveFontTheme = responsiveFontSizes(theme);

  // Modal 버튼 클릭 유무
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);

  /**
   * 판매 신청 form 반려하기
   */
  const handleDeny = async () => {
    console.log('반려');
    console.log(currentSaleFormId);
    // try {
    //   await denySaleFormPatchApi(currentSaleFormId).then((res) => {
    //     if (res.status === 200) {
    //       alert('반려 완료');
    //       router.push(`/admin/sales`);
    //     }
    //   });
    // } catch (error) {
    //   console.log('실패');
    // }
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
      contentCell: 'name',
    },
    {
      headerLabel: '차량번호',
      contentCell: 'carNum',
    },
    {
      headerLabel: '상태',
      contentCell: 'status',
    },
    {
      headerLabel: '심사',
      contentCell: 'saleConfirm',
    },
  ];

  /**
   * 전체 saleform의 정보를 GET
   */
  useEffect(() => {
    allSaleFormGetApi().then((data) => {
      console.log(data);
      setAllSaleFormInfo(data);
    });
    setMounted(true);
  }, []);

  return (
    mounted &&
    allSaleFormInfo && (
      <ThemeProvider theme={responsiveFontTheme}>
        <CssBaseline />
        <Typography sx={mypageCss.mypageTitle} component="h4" variant="h4" gutterBottom>
          관리자 페이지 - 판매 신청 목록
        </Typography>
        <SaleTable
          headerData={table_cell_data}
          contentData={allSaleFormInfo.content}
          moveUrl={`/admin/sales/approve/`}
          callbackFunc={handleClickModal}
          setCurrentSaleFormId={setCurrentSaleFormId}
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
