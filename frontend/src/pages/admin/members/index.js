import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import withAdminAuth from '@/hooks/withAdminAuth';
import { allUserGetApi } from '@/services/adminpageApi';
import MemberTable from '@/components/admin/MemberTable';

function AdminUserList() {
  const [mounted, setMounted] = useState(false);
  const [allUserInfo, setAllUserInfo] = useState();

  // pagination
  const [page, setPage] = useState(0); // 현재 페이지
  const [size, setSize] = useState(10); // 기본 사이즈

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

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    if (!mounted) {
      allUserGetApi(0, 10).then((res) => {
        if (res.status === 200) {
          setAllUserInfo(res.data);
        }
      });
      setMounted(true);
    } else {
      allUserGetApi(page, size).then((res) => {
        if (res.status === 200) {
          setAllUserInfo(res.data);
        }
      });
    }
  }, [page, size]);

  return (
    mounted &&
    allUserInfo && (
      <>
        <MemberTable
          headerData={table_cell_data}
          contentData={allUserInfo}
          moveUrl={`/admin/members/`}
          page={page}
          size={size}
          setPage={setPage}
          setSize={setSize}
        />
      </>
    )
  );
}

// side menu 레이아웃
AdminUserList.Layout = withAdminAuth(AdminPageLayout);
export default AdminUserList;
