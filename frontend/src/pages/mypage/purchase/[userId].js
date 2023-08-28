import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Button, Grid, Pagination, Typography } from '@mui/material';
import MypageCard from '@/components/mypage/MypageCard';
import { useRouter } from 'next/router';

function Purchase() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const userId = 1; // DUMMY_DATA

  const handleMove = (url) => {
    router.push(url);
  };

  const mypageCss = {
    mypageTitle: {
      my: 10,
      color: '#1490ef',
      fontWeight: 'bold',
    },
    pagination: { display: 'flex', justifyContent: 'center', my: 8 },
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    mounted && (
      <>
        <Typography sx={mypageCss.mypageTitle} component="h4" variant="h4" gutterBottom>
          마이페이지 - 구매이력
        </Typography>
        <Button
          onClick={() => {
            handleMove(`/mypage/purchase-request/${userId}`);
          }}>
          구매요청이력
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
Purchase.Layout = withAuth(UserMyPageLayout);
export default Purchase;
