import { authInstance } from '@/utils/api';

// 마이페이지 프로필 조회
export const memberProfileGetApi = async (memberId) => {
  try {
    const response = await authInstance.get(`/mypage/${memberId}`);
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

// 마이페이지 프로필 수정 GET
export const memberProfileEditGetApi = async (memberId) => {
  try {
    const response = await authInstance.get(`/mypage/profile/edit/${memberId}`);
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패: ', error);
    throw error;
  }
};
