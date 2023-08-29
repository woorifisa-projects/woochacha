export const HEADER_MENU = {
  LOGO: 'LOGO',
  CONTENTS: [
    {
      pageName: '구매',
      pageUrl: `/product`,
    },
    {
      pageName: '판매',
      pageUrl: `/product/sale`,
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

// 대출
export const CAPITAL_MAIN_CARD = [
  {
    title: `합리적인 자동차 금융상품을 추천해드립니다.`,
    content: `우차차가 추천하는 최대한도, 최저금리 상품을 소개합니다!`,
    titleColor: '#000',
  },
];

export const CAPITAL_CONTENTS = [
  {
    miniCardColor: '#DEF2FF',
    miniCardShadow: 0,
    miniCardMarginY: 25,
    capitalTitle: '리스/렌트 견적 비교',
    capitalImgUrl:
      'https://s7d1.scene7.com/is/image/hyundai/2023-ioniq-6-limited-rwd-transmission-blue-pearl-profile:Vehicle-Carousel?fmt=webp-alpha',
    capitalSubTitle: '리스와 렌트의 견적이 궁금하다면?',
    capitalSubContent: `내가 원하는 차량의 리스와 렌트의 견적을 비교해드립니다.`,
    wonCarUrl: 'https://www.wooriwoncar.com/myPage/myCarCompare',
  },
  {
    miniCardColor: '#EFFBFF',
    miniCardShadow: 0,
    miniCardMarginY: 25,
    capitalTitle: '나의 대출한도 확인하기',
    capitalImgUrl: 'https://www.wooriwoncar.com/webassets/img/pc/main-loan-img1.png',
    capitalSubTitle: '나의 대출한도를 \n 확인하고 싶다면?',
    capitalSubContent: `우리 WON카에서는 우리은행, 카드, 캐피탈 3사의
    최대 대출한도 및 최저금리를 모아서 알려드립니다.`,
    wonCarUrl: 'https://www.wooriwoncar.com/loanlimit/loanLimitInfo?cpcrGdDivCd=20',
  },
  {
    miniCardColor: '#FFF',
    miniCardShadow: 0,
    miniCardMarginY: 25,
    capitalTitle: '중고차 금융상품',
    capitalImgUrl: 'https://www.wooriwoncar.com/webassets/img/pc/sd-visual-new-bg.png',
    capitalSubTitle: '중고차 금융상품이 궁금하다면?',
    capitalSubContent: `우리 WON카에서 제공하는 최대한도, 최저금리 상품을 추천해드립니다.`,
    wonCarUrl: 'https://www.wooriwoncar.com/loangoods/loanGoods?loanType=20',
  },
];
