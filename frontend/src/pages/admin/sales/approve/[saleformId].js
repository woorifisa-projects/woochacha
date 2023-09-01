import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { Typography } from '@mui/material';

function AdminSalesApproveForm() {
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
          관리자 페이지 - 점검 정보 입력 폼
        </Typography>
      </>
    )
  );
}

// side menu 레이아웃
AdminSalesApproveForm.Layout = withAuth(AdminPageLayout);
export default AdminSalesApproveForm;
