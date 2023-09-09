import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Grid, Pagination, Tab, Typography } from '@mui/material';
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

  const handleMove = (url) => {
    router.push(`${url}${memberId}`);
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
    mypagePurchaseRequestListGetApi(memberId).then((data) => {
      setMypagePurchaseRequestList(data);
    });
    setMounted(true);
  }, []);

  return (
    mounted &&
    mypagePurchaseRequestList && (
      <>
        <Typography sx={mypageCss.mypageTitle} component="h4" variant="h4" gutterBottom>
          마이페이지 - 구매요청이력
        </Typography>
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
          <Pagination count={10} />
        </Grid>
      </>
    )
  );
}

// side menu 레이아웃
PurchaseRequest.Layout = withAuth(UserMyPageLayout);
export default PurchaseRequest;
