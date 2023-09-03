import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { Typography } from '@mui/material';
import withAdminAuth from '@/hooks/withAdminAuth';

function AdminSalesRegisterForm() {
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
          관리자 페이지 - 차량 게시글 등록
        </Typography>
      </>
    )
  );
}

// side menu 레이아웃
AdminSalesRegisterForm.Layout = withAdminAuth(AdminPageLayout);
export default AdminSalesRegisterForm;
