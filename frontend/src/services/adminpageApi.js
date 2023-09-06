import { authInstance } from '@/utils/api';

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
    const response = await authInstance.patch(`/admin/members/edit/${memberId}`, patchData);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - product] 매물 관리자 페이지에서 매물 수정, 삭제 신청 요청 조회 (GET)
 */
export const allProductApplicationsGetApi = async () => {
  try {
    const response = await authInstance.get('/admin/product');
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - product] 매물 관리자 페이지에서 매물 수정 팝업 페이지 조회 (GET)
 */
export const editProductApplicationsGetApi = async (productId) => {
  try {
    const response = await authInstance.get(`/admin/product/edit/${productId}`);
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - product] 매물 관리자 페이지에서 매물 수정 승인 (PATCH)
 */
export const editApproveProductApplicationsPatchApi = async (productId) => {
  try {
    const response = await authInstance.patch(`/admin/product/edit/${productId}`);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - product] 매물 관리자 페이지에서 매물 수정 반려 (PATCH)
 */
export const editDenyProductApplicationsPatchApi = async (productId) => {
  try {
    const response = await authInstance.patch(`admin/product/edit/deny/${productId}`);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - product] 매물 관리자 페이지에서 매물 수정 삭제 (PATCH)
 */
export const deleteProductApplicationsPatchApi = async (productId) => {
  try {
    const response = await authInstance.patch(`admin/product/delete/${productId}`);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};
