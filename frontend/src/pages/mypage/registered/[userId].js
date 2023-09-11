import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Grid, Pagination } from '@mui/material';
import MypageCardEdit from '@/components/mypage/MypageCardEdit';
import { mypageRegisteredProductsGetApi } from '@/services/mypageApi';
import { useRouter } from 'next/router';
import { useRecoilState } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';

function RegisteredItem(props) {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);
  const [mypageRegisteredProducts, setMypageRegisteredProducts] = useState();

  const memberId = userLoginState.userId;
  const { userId } = props;

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
    mypageRegisteredProductsGetApi(memberId).then((res) => {
      if (res.status === 200) {
        setMypageRegisteredProducts(res.data);
      }
    });
    setMounted(true);
  }, []);

  return (
    mounted &&
    mypageRegisteredProducts && (
      <>
        <MypageCardEdit content={mypageRegisteredProducts.content} memberId={memberId} />
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

export async function getServerSideProps(context) {
  const userId = context.params.userId;
  return {
    props: {
      userId,
    },
  };
}
