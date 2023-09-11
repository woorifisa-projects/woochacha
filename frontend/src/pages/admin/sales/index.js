import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { CssBaseline, ThemeProvider, Typography, responsiveFontSizes } from '@mui/material';
import theme from '@/styles/theme';
import withAdminAuth from '@/hooks/withAdminAuth';
import { ADMIN_DENY_MODAL } from '@/constants/string';
import SaleTable from '@/components/admin/SaleTable';
import BasicModal from '@/components/common/BasicModal';
import { allSaleFormGetApi, denySaleFormPatchApi } from '@/services/adminpageApi';
import { SwalModals } from '@/utils/modal';

function AdminSalesList() {
  const [mounted, setMounted] = useState(false);
  const [allSaleFormInfo, setAllSaleFormInfo] = useState();
  const [currentSaleFormId, setCurrentSaleFormId] = useState();
  let responsiveFontTheme = responsiveFontSizes(theme);
  const router = useRouter();

  // Modal 버튼 클릭 유무
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);

  // pagination
  const [page, setPage] = useState(0); // 현재 페이지
  const [size, setSize] = useState(10); // 기본 사이즈

  /**
   * 판매 신청 form 반려하기
   */
  const handleDeny = async () => {
    console.log('반려');
    console.log(currentSaleFormId);
    try {
      await denySaleFormPatchApi(currentSaleFormId).then((res) => {
        if (res.status === 200) {
          SwalModals('success', '반려 완료', '반려가 완료되었습니다!', false).then(() =>
            router.push('/admin/sales'),
          );
        }
      });
    } catch (error) {
      console.log('실패');
    }
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
    if (!mounted) {
      allSaleFormGetApi(0, 10).then((res) => {
        if (res.status === 200) {
          setAllSaleFormInfo(res.data);
        }
      });
      setMounted(true);
    } else {
      allSaleFormGetApi(page, size).then((res) => {
        if (res.status === 200) {
          setAllSaleFormInfo(res.data);
        }
      });
    }
  }, [page, size]);

  return (
    mounted &&
    allSaleFormInfo && (
      <ThemeProvider theme={responsiveFontTheme}>
        <CssBaseline />
        <SaleTable
          headerData={table_cell_data}
          contentData={allSaleFormInfo}
          moveUrl={`/admin/sales/approve/`}
          callbackFunc={handleClickModal}
          setCurrentSaleFormId={setCurrentSaleFormId}
          page={page}
          size={size}
          setPage={setPage}
          setSize={setSize}
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
