import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import { useRouter } from 'next/router';
import { useRecoilState } from 'recoil';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import { Button, Grid, Pagination, Tab, Typography } from '@mui/material';
import MypageCard from '@/components/mypage/MypageCard';
import SubTabMenu from '@/components/common/SubTabMenu';
import { mypageSoldProductsGetApi } from '@/services/mypageApi';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import { SUB_SALE_TAB_MENU } from '@/constants/string';

function Sale(props) {
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
    mypageTitle: {
      my: 10,
      color: '#1490ef',
      fontWeight: 'bold',
    },
    pagination: { display: 'flex', justifyContent: 'center', my: 8 },
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    mypageSoldProductsGetApi(memberId).then((res) => {
      if (res.status === 200) {
        console.log(res.data);
        setMypageSoldProducts(res.data);
      }
    });
    setMounted(true);
  }, []);

  return (
    mounted &&
    mypageSoldProducts && (
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
          <Pagination count={10} />
        </Grid>
      </>
    )
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
