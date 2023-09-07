import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { Typography } from '@mui/material';
import withAdminAuth from '@/hooks/withAdminAuth';
import { allUserGetApi } from '@/services/adminpageApi';
import MemberTable from '@/components/admin/MemberTable';

function AdminUserList() {
  const [mounted, setMounted] = useState(false);
  const [allUserInfo, setAllUserInfo] = useState();

  const mypageCss = {
    mypageTitle: {
      my: 10,
      color: '#1490ef',
      fontWeight: 'bold',
    },
  };

  const table_cell_data = [
    {
      headerLabel: '이메일',
      contentCell: 'email',
    },
    {
      headerLabel: '이름',
      contentCell: 'name',
    },
    {
      headerLabel: '전화번호',
      contentCell: 'phone',
    },
    {
      headerLabel: '상태',
      contentCell: 'isAvailable',
    },
  ];

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    allUserGetApi().then((data) => {
      setAllUserInfo(data);
    });
    setMounted(true);
  }, []);

  return (
    mounted &&
    allUserInfo && (
      <>
        <Typography sx={mypageCss.mypageTitle} component="h4" variant="h4" gutterBottom>
          관리자 페이지 - 유저 리스트
        </Typography>
        <MemberTable
          headerData={table_cell_data}
          contentData={allUserInfo}
          moveUrl={`/admin/members/`}
        />
      </>
    )
  );
}

// side menu 레이아웃
AdminUserList.Layout = withAdminAuth(AdminPageLayout);
export default AdminUserList;
