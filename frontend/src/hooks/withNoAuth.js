import { useRouter } from 'next/router';
import { useRecoilValue } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';

const withNoAuth = (WrappedComponent) => {
  return (props) => {
    // 클라이언트(브라우저) 또는 서버에 있는지 확인
    if (typeof window !== 'undefined') {
      const Router = useRouter();
      const userLoginState = useRecoilValue(userLoggedInState);

      const loginToken = localStorage.getItem('loginToken');

      // 토큰 있는 경우 or 로그인 status가 true인 경우, 메인 페이지로 이동
      if (loginToken || userLoginState.loginStatus) {
        // TODO: 만약, 토큰이 만료되었다면, 로그인 페이지 그대로 보여주기

        // 만약, 토큰이 유효하다면, 홈으로 이동
        Router.replace('/');
        return null;
      }

      // 토큰 O 경우
      return <WrappedComponent {...props} />;
    }

    // 서버에 있는 경우 null
    return null;
  };
};

export default withNoAuth;
