const { atom } = require('recoil');
import { recoilPersist } from 'recoil-persist';

// 새로고침해도 상태정보 유지
const { persistAtom } = recoilPersist({
  key: 'recoil-persist',
  storage: typeof window === 'undefined' ? undefined : localStorage,
});

// TODO: login 한 유저의 정보를 받아오기(userid)
const userInfoState = atom({
  key: 'userInfoState',
  default: {},
  effects_UNSTABLE: [persistAtom],
});

// TODO: login 상태
const userLoggedInState = atom({
  key: 'userLoggedInState',
  default: {
    loginStatus: false,
    userId: null,
    userName: null,
  },
  effects_UNSTABLE: [persistAtom],
});

export { userInfoState, userLoggedInState };
