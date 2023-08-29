import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Typography } from '@mui/material';
import MypageProfileEdit from '@/components/mypage/MypageProfileEdit';

function ProfileEdit() {
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
          마이페이지 - 내 프로필 수정
        </Typography>
        <MypageProfileEdit />
      </>
    )
  );
}

// side menu 레이아웃
ProfileEdit.Layout = withAuth(UserMyPageLayout);
export default ProfileEdit;
