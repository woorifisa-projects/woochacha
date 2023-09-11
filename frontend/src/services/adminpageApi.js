import { authFormInstance, authInstance } from '@/utils/api';

/**
 * [관리자 - members] 모든 사용자 목록 조회 (GET)
 */
export const allUserGetApi = async () => {
  try {
    const response = await authInstance.get('/admin/members');
    return response;
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
    return response;
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
    return response;
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
    return response;
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
    const response = await authInstance.patch(`/admin/product/edit/deny/${productId}`);
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
    const response = await authInstance.patch(`/admin/product/delete/${productId}`);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - sale] 판매 신청 관리 목록 전체 조회 (GET)
 */
export const allSaleFormGetApi = async () => {
  try {
    const response = await authInstance.get(`/admin/sales`);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - sale] 판매 신청 폼 반려하기 (PATCH)
 */
export const denySaleFormPatchApi = async (saleFormId) => {
  try {
    const response = await authInstance.patch(`/admin/sales/deny/${saleFormId}`);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - sale] 점검차량 정보 입력 (GET)
 */
export const oneApproveFormGetApi = async (saleFormId) => {
  try {
    const response = await authInstance.get(`/admin/sales/approve/${saleFormId}`);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - sale] 점검차량 정보 입력 후, patch 요청 (PATCH)
 */
export const oneApproveFormPatchApi = async (saleFormId, newApproveData) => {
  try {
    const response = await authInstance.patch(`/admin/sales/approve/${saleFormId}`, newApproveData);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - sale] 차량 게시글 등록을 위한 폼 데이터를 QLDB에서 조회 (GET)
 */
export const oneRegisterFormGetApi = async (saleFormId) => {
  try {
    const response = await authInstance.get(`/admin/sales/register/${saleFormId}`);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - sale] 차량 게시글을 등록 (POST)
 */
export const oneRegisterFormPostApi = async (saleFormId, registerInputForm) => {
  try {
    const response = await authFormInstance.post(
      `admin/sales/register/${saleFormId}`,
      registerInputForm,
    );
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - member] 관리자 페이지에서 한 명의 member 삭제 (PATCH)
 */
export const oneMemberDeletePatchApi = async (memberId) => {
  try {
    const response = await authInstance.patch(`/admin/members/delete/${memberId}`);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - log] 관리자 페이지에서 한 명의 member의 로그 내역 조회 (GET)
 */
export const oneMemberLogGetApi = async (memberId) => {
  try {
    const response = await authInstance.get(`/admin/members/log/${memberId}`);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - purchase] 거래 관리 요청 목록 조회 (GET)
 */
export const allPurchaseFormGetApi = async () => {
  try {
    const response = await authInstance.get(`/admin/purchase`);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - purchase] 거래 관리 검토여부 조회 (GET)
 */
export const onePurchaseConfirmFormGetApi = async (purchaseId) => {
  try {
    const response = await authInstance.get(`/admin/purchase/${purchaseId}`);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - purchase] 관리자 페이지의 구매 신청폼에서 선택한 희망 거래일 문자 전송 (PATCH)
 */
export const onePurchaseConfirmFormPatchApi = async (purchaseId, purchaseForm) => {
  try {
    const response = await authInstance.patch(`/admin/purchase/${purchaseId}`, purchaseForm);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [관리자 - purchase] 성사된 거래 정보를 transaction 테이블에 저장 (POST)
 */
export const onePurchaseTransactionFormPostApi = async (purchaseId) => {
  try {
    const response = await authInstance.post(`/admin/purchase/success/${purchaseId}`);
    return response;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};
