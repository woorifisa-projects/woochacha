import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Grid, Pagination, Typography } from '@mui/material';
import MypageCard from '@/components/mypage/MypageCard';
import { mypageSaleRequestListGetApi } from '@/services/mypageApi';
import { useRouter } from 'next/router';
import { useRecoilState } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';

function SaleRequest() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);
  const [mypageSaleRequestList, setMypageSaleRequestList] = useState();

  const memberId = userLoginState.userId;

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
    mypageSaleRequestListGetApi(memberId).then((data) => {
      setMypageSaleRequestList(data);
    });
    setMounted(true);
  }, []);

  return (
    mounted &&
    mypageSaleRequestList && (
      <>
        <Typography sx={mypageCss.mypageTitle} component="h4" variant="h4" gutterBottom>
          마이페이지 - 판매요청이력
        </Typography>
        <MypageCard content={mypageSaleRequestList.content} />
        {/* pagination */}
        <Grid sx={mypageCss.pagination}>
          <Pagination count={10} />
        </Grid>
      </>
    )
  );
}

// side menu 레이아웃
SaleRequest.Layout = withAuth(UserMyPageLayout);
export default SaleRequest;
