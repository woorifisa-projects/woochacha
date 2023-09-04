import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { Typography } from '@mui/material';
import withAdminAuth from '@/hooks/withAdminAuth';
import BasicTable from '@/components/admin/MemberTable';

function AdminUserList() {
  const [mounted, setMounted] = useState(false);

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

  // TODO: axios로 data 받아온 데이터라고 가정
  const dummy_content_data = {
    content: [
      {
        id: '1',
        email: 'aaa@naver.com',
        name: '김김김',
        phone: '01011113333',
        isAvailable: 1,
      },
      {
        id: '2',
        email: 'aaa@naver.com',
        name: '김김김',
        phone: '01011113333',
        isAvailable: 1,
      },
      {
        id: '3',
        email: 'aaa@naver.com',
        name: '김김김',
        phone: '01011113333',
        isAvailable: 1,
      },
    ],
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    mounted && (
      <>
        <Typography sx={mypageCss.mypageTitle} component="h4" variant="h4" gutterBottom>
          관리자 페이지 - 유저 리스트
        </Typography>
        <BasicTable
          headerData={table_cell_data}
          contentData={dummy_content_data.content}
          moveUrl={`/admin/members/`}
        />
      </>
    )
  );
}

// side menu 레이아웃
AdminUserList.Layout = withAdminAuth(AdminPageLayout);
export default AdminUserList;
