import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Grid, Pagination } from '@mui/material';
import MypageCardEdit from '@/components/mypage/MypageCardEdit';
import { mypageRegisteredProductsGetApi } from '@/services/mypageApi';
import { useRecoilState } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import LoadingBar from '@/components/common/LoadingBar';

function RegisteredItem() {
  const [mounted, setMounted] = useState(false);
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);
  const [mypageRegisteredProducts, setMypageRegisteredProducts] = useState();

  const memberId = userLoginState.userId;

  /**
   * 페이지네이션
   */
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(5);

  const handleChange = (event, value) => {
    setPage(value - 1);
  };

  /**
   * - 첫 렌더링시, 전체 목록 get
   * - page 변경 시, 재렌더링
   */
  useEffect(() => {
    if (!mounted) {
      mypageRegisteredProductsGetApi(memberId, 0, 5).then((res) => {
        if (res.status === 200) {
          setMypageRegisteredProducts(res.data);
        }
      });
      setMounted(true);
    } else {
      mypageRegisteredProductsGetApi(memberId, page, pageSize).then((res) => {
        if (res.status === 200) {
          setMypageRegisteredProducts(res.data);
        }
      });
    }
  }, [page]);

  const registeredCss = {
    pagination: { display: 'flex', justifyContent: 'center', my: 8 },
  };

  return mounted && mypageRegisteredProducts ? (
    <>
      <MypageCardEdit content={mypageRegisteredProducts.content} memberId={memberId} />

      {/* pagination */}
      <Grid sx={registeredCss.pagination}>
        {mypageRegisteredProducts.totalPages === 0 ? (
          ''
        ) : (
          <Pagination
            count={mypageRegisteredProducts.totalPages}
            page={page + 1}
            onChange={handleChange}
          />
        )}
      </Grid>
    </>
  ) : (
    <LoadingBar />
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
