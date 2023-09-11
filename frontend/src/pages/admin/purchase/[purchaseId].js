import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { Typography } from '@mui/material';
import withAdminAuth from '@/hooks/withAdminAuth';

function AdminPurchaseManage() {
  const [mounted, setMounted] = useState(false);

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  return mounted && <></>;
}

// side menu 레이아웃
AdminPurchaseManage.Layout = withAdminAuth(AdminPageLayout);
export default AdminPurchaseManage;
