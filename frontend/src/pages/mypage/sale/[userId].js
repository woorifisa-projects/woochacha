import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import { useRouter } from 'next/router';
import { useRecoilState } from 'recoil';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Grid, Pagination, Tab } from '@mui/material';
import MypageCard from '@/components/mypage/MypageCard';
import SubTabMenu from '@/components/common/SubTabMenu';
import { mypageSoldProductsGetApi } from '@/services/mypageApi';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import { SUB_SALE_TAB_MENU } from '@/constants/string';
import LoadingBar from '@/components/common/LoadingBar';

function Sale() {
  const router = useRouter();
  const [mounted, setMounted] = useState(false);
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);
  const [mypageSoldProducts, setMypageSoldProducts] = useState();
  const [subMemuVal, setSubMenuVal] = useState('one');

  const memberId = userLoginState.userId;

  const handleMove = (url) => {
    router.push(`${url}${memberId}`);
  };

  const mypageCss = {
    pagination: { display: 'flex', justifyContent: 'center', my: 8 },
  };

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
      mypageSoldProductsGetApi(memberId, 0, 5).then((res) => {
        if (res.status === 200) {
          setMypageSoldProducts(res.data);
        }
      });
      setMounted(true);
    } else {
      mypageSoldProductsGetApi(memberId, page, pageSize).then((res) => {
        if (res.status === 200) {
          setMypageSoldProducts(res.data);
        }
      });
    }
  }, [page]);

  return mounted && mypageSoldProducts ? (
    <>
      <SubTabMenu currentVal={subMemuVal}>
        {SUB_SALE_TAB_MENU.map((item, idx) => {
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
      <MypageCard content={mypageSoldProducts.content} />

      {/* pagination */}
      <Grid sx={mypageCss.pagination}>
        {mypageSoldProducts.totalPages === 0 ? (
          ''
        ) : (
          <Pagination
            count={mypageSoldProducts.totalPages}
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
Sale.Layout = withAuth(UserMyPageLayout);
export default Sale;

export async function getServerSideProps(context) {
  const userId = context.params.userId;
  return {
    props: {
      userId,
    },
  };
}
