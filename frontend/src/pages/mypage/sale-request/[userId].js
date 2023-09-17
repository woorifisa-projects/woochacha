import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Grid, Pagination, Tab } from '@mui/material';
import { mypageSaleRequestListGetApi } from '@/services/mypageApi';
import { useRouter } from 'next/router';
import { useRecoilState } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import MypageCardPurchase from '@/components/mypage/MypageCardPurchase';
import SubTabMenu from '@/components/common/SubTabMenu';
import { SUB_SALE_TAB_MENU } from '@/constants/string';
import LoadingBar from '@/components/common/LoadingBar';

function SaleRequest() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);
  const [mypageSaleRequestList, setMypageSaleRequestList] = useState();
  const [subMemuVal, setSubMenuVal] = useState('two');

  const memberId = userLoginState.userId;

  /**
   * 페이지네이션
   */
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(5);

  const handleChange = (event, value) => {
    setPage(value - 1);
  };

  const handleMove = (url) => {
    router.push(`${url}${memberId}`);
  };

  const mypageCss = {
    pagination: { display: 'flex', justifyContent: 'center', my: 8 },
  };

  /**
   * - 첫 렌더링시, 전체 목록 get
   * - page 변경 시, 재렌더링
   */
  useEffect(() => {
    if (!mounted) {
      mypageSaleRequestListGetApi(memberId, 0, 5).then((res) => {
        if (res.status === 200) {
          setMypageSaleRequestList(res.data);
        }
      });
      setMounted(true);
    } else {
      mypageSaleRequestListGetApi(memberId, page, pageSize).then((res) => {
        if (res.status === 200) {
          setMypageSaleRequestList(res.data);
        }
      });
    }
  }, [page]);

  return mounted && mypageSaleRequestList ? (
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

      <MypageCardPurchase content={mypageSaleRequestList.content} />

      {/* pagination */}
      <Grid sx={mypageCss.pagination}>
        {mypageSaleRequestList.totalPages === 0 ? (
          ''
        ) : (
          <Pagination
            count={mypageSaleRequestList.totalPages}
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
SaleRequest.Layout = withAuth(UserMyPageLayout);
export default SaleRequest;

export async function getServerSideProps(context) {
  const userId = context.params.userId;
  return {
    props: {
      userId,
    },
  };
}
