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

export const HEADER_USER_MENU = {
  CONTENTS: [
    {
      pageName: '로그인',
      pageUrl: `/users/login`,
    },
    {
      pageName: '회원가입',
      pageUrl: `/users/signup`,
    },
    {
      pageName: '탈퇴하기',
      pageUrl: `/users/signout`,
    },
  ],
};
