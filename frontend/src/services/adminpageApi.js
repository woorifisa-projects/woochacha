import { authInstance, jsonInstance } from '@/utils/api';

/**
 * [관리자 - members] 모든 사용자 목록 조회 (GET)
 */
export const allUserGetApi = async () => {
  try {
    const response = await authInstance.get('/admin/members');
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - members] 특정 사용자 상세정보 조회 (GET)
 */
export const oneUserGetApi = async (memberId) => {
  try {
    const response = await authInstance.get(`/admin/members/${memberId}`);
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - members] 특정 사용자 정보 수정 요청 (PATCH)
 */
export const oneUserEditPatchApi = async (editProfileValue, memberId) => {
  try {
    const patchData = {
      isChecked: editProfileValue.isChecked ? 1 : 0,
      isAvailable: editProfileValue.status,
    };
    console.log(patchData);
    const response = await authInstance.patch(`/admin/members/edit/${memberId}`, patchData);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자] 특정 사용자 정보 수정 (POST)
 */
export const submitEditRegistered = async (memberId, productId, editedData) => {
  try {
    const response = await jsonInstance.patch(`/mypage/registered/edit`, {
      params: {
        // query string
        memberId: memberId,
        productId: productId,
      },
      data: editedData, // 데이터 전달
    });

    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};
