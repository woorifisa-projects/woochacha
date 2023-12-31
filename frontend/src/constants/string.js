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
      page: 'mypage',
    },
    {
      pageName: '구매이력',
      pageUrl: `/mypage/purchase`,
      page: 'purchase',
    },
    {
      pageName: '구매요청이력',
      pageUrl: `/mypage/purchase-request`,
      page: 'purchase-request',
    },
    {
      pageName: '판매이력',
      pageUrl: `/mypage/sale`,
      page: 'sale',
    },
    {
      pageName: '판매요청이력',
      pageUrl: `/mypage/sale-request`,
      page: 'sale-request',
    },
    {
      pageName: '등록한 매물정보',
      pageUrl: `/mypage/registered`,
      page: 'registered',
    },
  ],
};

export const HEADER_ADMIN_MINI_MENU = {
  CONTENTS: [
    {
      pageName: '사용자 관리',
      pageUrl: `/admin/members`,
      page: 'members',
    },
    {
      pageName: '매물관리',
      pageUrl: `/admin/product`,
      page: 'product',
    },
    {
      pageName: '판매 신청 관리',
      pageUrl: `/admin/sales`,
      page: 'sales',
    },
    {
      pageName: '거래 관리',
      pageUrl: `/admin/purchase`,
      page: 'purchase',
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
      pageName: '로그아웃',
      pageUrl: `/users/logout`,
    },
    {
      pageName: '탈퇴하기',
      pageUrl: `/users/signout`,
    },
  ],
};

export const HEADER_LOGIN_ADMIN_MENU = {
  CONTENTS: [
    {
      pageName: '로그아웃',
      pageUrl: `/users/logout`,
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
    miniCardShadow: 3,
    miniCardMarginY: 15,
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
    miniCardMarginY: 15,
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
    miniCardMarginY: 15,
    capitalTitle: '중고차 금융상품',
    capitalImgUrl: 'https://www.wooriwoncar.com/webassets/img/pc/sd-visual-new-bg.png',
    capitalSubTitle: '중고차 금융상품이 궁금하다면?',
    capitalSubContent: `우리 WON카에서 제공하는 최대한도, 최저금리 상품을 추천해드립니다.`,
    wonCarUrl: 'https://www.wooriwoncar.com/loangoods/loanGoods?loanType=20',
  },
  {
    miniCardColor: '#FFF',
    miniCardShadow: 0,
    miniCardMarginY: 15,
    capitalTitle: '메타버스로 실시간 상담하기',
    capitalImgUrl: 'metabusImg',
    capitalSubTitle: '실시간으로 메타버스에서 상담하기',
    capitalSubContent: `실시간으로 메타버스에서 상담하고 자동차 모임에도 참여하세요!`,
    wonCarUrl: 'https://app.gather.town/app/4vEB4Q4BUbTU0ckj/wooribank',
  },
];

// MODAL
export const EDIT_MODAL = {
  CONTENTS: {
    modalTitle: '수정하시겠습니까?',
    modalYesBtn: '네',
    modalNoBtn: '아니요',
    modalContents: '수정을 등록하시려면 버튼을 눌러주세요.',
  },
};

// MODAL
export const DELETE_MODAL = {
  CONTENTS: {
    modalTitle: '삭제하시겠습니까?',
    modalYesBtn: '네',
    modalNoBtn: '아니요',
    modalContents: '삭제 요청은 취소할 수 없습니다.',
  },
};

// MODAL
export const PURCHASE_MODAL = {
  CONTENTS: {
    modalTitle: '구매를 요청하시겠습니까?',
    modalYesBtn: '네',
    modalNoBtn: '아니요',
  },
};

// MODAL
export const ADMIN_DENY_MODAL = {
  CONTENTS: {
    modalTitle: '승인을 반려하시겠습니까?',
    modalYesBtn: '네',
    modalNoBtn: '아니요',
    modalContents: '승인 반려는 취소할 수 없습니다.',
  },
};

// MODAL
export const ADMIN_APPROVE_MODAL = {
  CONTENTS: {
    modalTitle: '승인하시겠습니까?',
    modalYesBtn: '등록하기',
    modalContents: '점검 정보를 승인합니다. 승인 후 게시글을 등록해주세요.',
  },
};

// MODAL
export const ADMIN_REGISTER_MODAL = {
  CONTENTS: {
    modalTitle: '등록이 완료되었습니다',
    modalYesBtn: '닫기',
    modalContents: '등록이 완료되었습니다. 게시글을 확인해주세요.',
  },
};

// MODAL
export const ADMIN_EDIT_MODAL = {
  CONTENTS: {
    modalTitle: '수정을 승인하시겠습니까?',
    modalYesBtn: '승인하기',
    modalNoBtn: '반려하기',
    modalContents: '',
  },
};

// MODAL
export const ADMIN_PURCHASE_CONFIRM_MODAL = {
  CONTENTS: {
    modalTitle: '구매 약속일을 선택하시겠습니까?',
    modalYesBtn: '판매자 및 구매자에게 문자 전송',
    modalNoBtn: '신청폼 무효화',
    modalContents: '구매 약속일을 선택해주세요.',
  },
};

// MODAL
export const ADMIN_PURCHASE_MODAL = {
  CONTENTS: {
    modalTitle: '거래를 성사를 완료하겠습니까?',
    modalYesBtn: '거래 성사',
    modalNoBtn: '거래 미성사',
    modalContents: '',
  },
};

// MODAL
export const MEMBER_DELETE_MODAL = {
  CONTENTS: {
    modalTitle: '회원을 삭제하시겠습니까?',
    modalYesBtn: '네',
    modalNoBtn: '아니요',
    modalContents: '정말로 회원을 삭제하시겠습니까?',
  },
};

// MYPAGE - SUB TABS
export const SUB_PURCHASE_TAB_MENU = [
  {
    value: 'one',
    label: '구매이력',
    url: `/mypage/purchase/`,
  },
  {
    value: 'two',
    label: '구매요청이력',
    url: `/mypage/purchase-request/`,
  },
];

export const SUB_SALE_TAB_MENU = [
  {
    value: 'one',
    label: '판매이력',
    url: `/mypage/sale/`,
  },
  {
    value: 'two',
    label: '판매요청이력',
    url: `/mypage/sale-request/`,
  },
];
