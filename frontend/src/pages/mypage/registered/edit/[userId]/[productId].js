import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Typography } from '@mui/material';
import MypageCard from '@/components/mypage/MypageCard';

function RegisteredEdit() {
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
          마이페이지 - 차량게시글 수정
        </Typography>
        <MypageCard />
      </>
    )
  );
}

// side menu 레이아웃
RegisteredEdit.Layout = withAuth(UserMyPageLayout);
export default RegisteredEdit;
