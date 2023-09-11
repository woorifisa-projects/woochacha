import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import MypageProfile from '@/components/mypage/MypageProfile';
import { useRecoilState } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';

function Mypage(props) {
  const [mounted, setMounted] = useState(false);
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);

  const memberId = userLoginState.userId;

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    mounted &&
    memberId && (
      <>
        <MypageProfile />
      </>
    )
  );
}

// side menu 레이아웃
Mypage.Layout = withAuth(UserMyPageLayout);
export default Mypage;

export async function getServerSideProps(context) {
  const userId = context.params.userId;
  return {
    props: {
      userId,
    },
  };
}
