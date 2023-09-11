import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { Typography } from '@mui/material';
import withAdminAuth from '@/hooks/withAdminAuth';
import { allUserGetApi } from '@/services/adminpageApi';
import MemberTable from '@/components/admin/MemberTable';

function AdminUserList() {
  const [mounted, setMounted] = useState(false);
  const [allUserInfo, setAllUserInfo] = useState();
  const [page, setPage] = useState(0); // 페이지 번호 초기값 설정
  const [pageSize, setPageSize] = useState(10); // 페이지 크기 초기값 설정

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
    {
      headerLabel: '로그',
      contentCell: 'logLink',
    },
  ];

  useEffect(() => {
    allUserGetApi(page, pageSize).then((res) => {
      if (res.status === 200) {
        setAllUserInfo(res.data);
      }
    });
    setMounted(true);
  }, [page, pageSize]);

  return (
    mounted &&
    allUserInfo && (
      <>
        <MemberTable
          headerData={table_cell_data}
          contentData={allUserInfo}
          moveUrl={`/admin/members/`}
          page={page} // 페이지 번호 전달
          pageSize={pageSize} // 페이지 크기 전달
          onPageChange={(newPage) => setPage(newPage)} // 페이지 번호 변경 핸들러 전달
          onPageSizeChange={(newSize) => setPageSize(newSize)} // 페이지 크기 변경 핸들러 전달
        />
      </>
    )
  );
}

// side menu 레이아웃
AdminUserList.Layout = withAdminAuth(AdminPageLayout);
export default AdminUserList;
