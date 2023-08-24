export const HEADER_MENU = {
  LOGO: '우차차',
  CONTENTS: [
    {
      pageName: '구매',
      pageUrl: `/products`,
    },
    {
      pageName: '판매',
      pageUrl: `/products/sale`,
    },
    {
      pageName: '대출',
      pageUrl: `/capitals`,
    },
  ],
};

export const HEADER_MINI_MENU = {
  CONTENTS: [
    {
      pageName: '내 프로필',
      pageUrl: `/mypage`,
    },
    {
      pageName: '구매정보',
      pageUrl: `/mypage/purchase`,
    },
    {
      pageName: '판매정보',
      pageUrl: `/mypage/sale`,
    },
    {
      pageName: '등록한 매물정보',
      pageUrl: `/mypage/registered`,
    },
  ],
};

export const HEADER_UNLOGIN_USER_MENU = {
  CONTENTS: [
    {
      pageName: '로그인',
      pageUrl: `/users/login`,
    },
    {
      pageName: '회원가입',
      pageUrl: `/users/signup`,
    },
  ],
};

export const HEADER_LOGIN_USER_MENU = {
  CONTENTS: [
    {
      pageName: '마이페이지',
      pageUrl: `/mypage`,
    },
    {
      pageName: '로그아웃',
      pageUrl: `/users/logout`,
    },
    {
      pageName: '탈퇴하기',
      pageUrl: `/users/signout`,
    },
  ],
};
// 파일명 변경을 위한 임시 주석 추가
