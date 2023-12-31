import { useEffect, useState } from 'react';
import withAuth from '@/hooks/withAuth';
import UserMyPageLayout from '@/layouts/user/UserMyPageLayout';
import MypageProfileEdit from '@/components/mypage/MypageProfileEdit';
import LoadingBar from '@/components/common/LoadingBar';

function ProfileEdit(props) {
  const [mounted, setMounted] = useState(false);
  const { userId } = props;

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  return mounted ? (
    <>
      <MypageProfileEdit />
    </>
  ) : (
    <LoadingBar />
  );
}

// side menu 레이아웃
ProfileEdit.Layout = withAuth(UserMyPageLayout);
export default ProfileEdit;

export async function getServerSideProps(context) {
  const userId = context.params.userId;
  return {
    props: {
      userId,
    },
  };
}
