import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Button, Grid, Pagination, Typography } from '@mui/material';
import MypageCard from '@/components/mypage/MypageCard';
import { useRouter } from 'next/router';

function RegisteredItem() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const userId = 1; // DUMMY_DATA
  const productId = 1; // DUMMY_DATA

  const mypageCss = {
    mypageTitle: {
      my: 10,
      color: '#1490ef',
      fontWeight: 'bold',
    },
    pagination: { display: 'flex', justifyContent: 'center', my: 8 },
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
          마이페이지 - 등록한 게시글(매물)정보 조회
        </Typography>
        <Button
          onClick={() => {
            handleMove(`/mypage/registered/edit/${userId}/${productId}`);
          }}>
          등록한 게시글 수정
        </Button>
        <MypageCard />
        {/* pagination */}
        <Grid sx={mypageCss.pagination}>
          <Pagination count={10} />
        </Grid>
      </>
    )
  );
}

// side menu 레이아웃
RegisteredItem.Layout = withAuth(UserMyPageLayout);
export default RegisteredItem;
