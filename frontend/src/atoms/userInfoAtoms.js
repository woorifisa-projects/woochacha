const { atom } = require('recoil');
import { recoilPersist } from 'recoil-persist';

// TODO: login 한 유저의 정보를 받아오기
const userInfoState = atom({
  key: 'userInfoState',
  default: {},
});

const { persistAtom } = recoilPersist({
  key: 'recoil-persist',
  storage: typeof window === 'undefined' ? undefined : sessionStorage,
});

const userLoggedInState = atom({
  key: 'userLoggedInState',
  default: false,
  // effects_UNSTABLE: [persistAtom],
});

export { userInfoState, userLoggedInState };
