import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Grid, Pagination, Typography } from '@mui/material';
import MypageCardEdit from '@/components/mypage/MypageCardEdit';
import { mypageRegisteredProductsGetApi } from '@/services/mypageApi';
import { useRouter } from 'next/router';
import { useRecoilState } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';

function RegisteredItem() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);
  const [mypageRegisteredProducts, setMypageRegisteredProducts] = useState();

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
    mypageRegisteredProductsGetApi(memberId).then((data) => {
      console.log(data);
      setMypageRegisteredProducts(data);
    });
    setMounted(true);
  }, []);

  return (
    mounted &&
    mypageRegisteredProducts && (
      <>
        <Typography sx={mypageCss.mypageTitle} component="h4" variant="h4" gutterBottom>
          마이페이지 - 등록한 게시글(매물)정보 조회
        </Typography>
        <MypageCardEdit content={mypageRegisteredProducts.content} />
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
