import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Grid, Pagination, Tab } from '@mui/material';
import MypageCard from '@/components/mypage/MypageCard';
import { mypagePurchaseRequestListGetApi } from '@/services/mypageApi';
import { useRouter } from 'next/router';
import { useRecoilState } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import SubTabMenu from '@/components/common/SubTabMenu';
import { SUB_PURCHASE_TAB_MENU } from '@/constants/string';

function PurchaseRequest() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);
  const [mypagePurchaseRequestList, setMypagePurchaseRequestList] = useState();
  const [subMemuVal, setSubMenuVal] = useState('two');

  const memberId = userLoginState.userId;
  // const { userId } = props;

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
      mypagePurchaseRequestListGetApi(memberId, 0, 5).then((res) => {
        if (res.status === 200) {
          setMypagePurchaseRequestList(res.data);
        }
      });
      setMounted(true);
    } else {
      mypagePurchaseRequestListGetApi(memberId, page, pageSize).then((res) => {
        if (res.status === 200) {
          setMypagePurchaseRequestList(res.data);
        }
      });
    }
  }, [page]);

  return (
    mounted &&
    mypagePurchaseRequestList && (
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
        <MypageCard content={mypagePurchaseRequestList.content} />

        {/* pagination */}
        <Grid sx={mypageCss.pagination}>
          {mypagePurchaseRequestList.totalPages === 0 ? (
            ''
          ) : (
            <Pagination
              count={mypagePurchaseRequestList.totalPages}
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
PurchaseRequest.Layout = withAuth(UserMyPageLayout);
export default PurchaseRequest;

export async function getServerSideProps(context) {
  const userId = context.params.userId;
  return {
    props: {
      userId,
    },
  };
}
