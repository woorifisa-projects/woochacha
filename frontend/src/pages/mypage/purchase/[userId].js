import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Grid, Pagination, Tab } from '@mui/material';
import MypageCard from '@/components/mypage/MypageCard';
import { useRouter } from 'next/router';
import { useRecoilState } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import { mypagePurchasedProductsGetApi } from '@/services/mypageApi';
import SubTabMenu from '@/components/common/SubTabMenu';
import { SUB_PURCHASE_TAB_MENU } from '@/constants/string';

function Purchase(props) {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);
  const [mypagePurchasedProducts, setMypagePurchasedProducts] = useState();
  const [subMemuVal, setSubMenuVal] = useState('one');

  const memberId = userLoginState.userId;
  // const { userId } = props;

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
      mypagePurchasedProductsGetApi(memberId, 0, 5).then((res) => {
        if (res.status === 200) {
          setMypagePurchasedProducts(res.data);
        }
      });
      setMounted(true);
    } else {
      mypagePurchasedProductsGetApi(memberId, page, pageSize).then((res) => {
        if (res.status === 200) {
          setMypagePurchasedProducts(res.data);
        }
      });
    }
  }, [page]);

  const handleMove = (url) => {
    router.push(`${url}${memberId}`);
  };

  const mypageCss = {
    pagination: { display: 'flex', justifyContent: 'center', my: 8 },
  };

  return (
    mounted &&
    mypagePurchasedProducts && (
      <>
        <SubTabMenu currentVal={subMemuVal}>
          {SUB_PURCHASE_TAB_MENU.map((item, idx) => {
            return (
              <Tab
                key={idx}
                value={item.value}
                label={item.label}
                onClick={() => handleMove(item.url)}
              />
            );
          })}
        </SubTabMenu>
        <MypageCard content={mypagePurchasedProducts.content} />

        {/* pagination */}
        <Grid sx={mypageCss.pagination}>
          {mypagePurchasedProducts.totalPages === 0 ? (
            ''
          ) : (
            <Pagination
              count={mypagePurchasedProducts.totalPages}
              page={page + 1}
              onChange={handleChange}
            />
          )}
        </Grid>
      </>
    )
  );
}

// side menu 레이아웃
Purchase.Layout = withAuth(UserMyPageLayout);
export default Purchase;

export async function getServerSideProps(context) {
  const userId = context.params.userId;
  return {
    props: {
      userId,
    },
  };
}
