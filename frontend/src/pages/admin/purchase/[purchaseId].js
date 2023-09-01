import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { Typography } from '@mui/material';
import MypageProfileEdit from '@/components/mypage/MypageProfileEdit';

function AdminPurchaseManage() {
  const [mounted, setMounted] = useState(false);

  const mypageCss = {
    mypageTitle: {
      my: 10,
      color: '#1490ef',
      fontWeight: 'bold',
    },
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    mounted && (
      <>
        <Typography sx={mypageCss.mypageTitle} component="h4" variant="h4" gutterBottom>
          관리자 페이지 - [거래관리] 구매 요청 상세보기
        </Typography>
        <MypageProfileEdit />
      </>
    )
  );
}

// side menu 레이아웃
AdminPurchaseManage.Layout = withAuth(AdminPageLayout);
export default AdminPurchaseManage;
