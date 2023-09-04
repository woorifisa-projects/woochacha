import { useRouter } from 'next/router';
import { useRecoilValue } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';

const withAuth = (WrappedComponent) => {
  return (props) => {
    // 클라이언트(브라우저) 또는 서버에 있는지 확인
    if (typeof window !== 'undefined') {
      const Router = useRouter();
      const userLoginState = useRecoilValue(userLoggedInState);

      const loginToken = localStorage.getItem('loginToken');

      // 토큰 없는 경우, 로그인 페이지로 이동
      if (!loginToken || !userLoginState.loginStatus) {
        Router.replace('/users/login');
        return null;
      }

      // TODO: 토큰은 있는데, 검증이 안된 경우, 로컬 스토리지 삭제 후, 로그인 페이지로 이동
      // authInstance

      // 토큰 O 경우
      return <WrappedComponent {...props} />;
    }

    // 서버에 있는 경우 null
    return null;
  };
};

export default withAuth;
