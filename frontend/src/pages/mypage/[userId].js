import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Button, Typography } from '@mui/material';
import MypageProfile from '@/components/mypage/MypageProfile';
import { useRouter } from 'next/router';

function Mypage() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const userId = 1; // DUMMY_DATA

  const mypageCss = {
    mypageTitle: {
      my: 10,
      color: '#1490ef',
      fontWeight: 'bold',
    },
  };

  const handleMove = (url) => {
    router.push(url);
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    mounted && (
      <>
        <Typography sx={mypageCss.mypageTitle} component="h4" variant="h4" gutterBottom>
          마이페이지 - 내 프로필
        </Typography>
        <Button
          onClick={() => {
            handleMove(`/mypage/profile/edit/${userId}`);
          }}>
          수정
        </Button>
        <MypageProfile />
      </>
    )
  );
}

// side menu 레이아웃
Mypage.Layout = withAuth(UserMyPageLayout);
export default Mypage;
