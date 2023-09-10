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

// 마이페이지 등록한 매물 조회 GET mypage/registered/{memberId}
export const mypageRegisteredProductsGetApi = async (memberId) => {
  try {
    const response = await authInstance.get(`/mypage/registered/${memberId}`);
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패: ', error);
    throw error;
  }
};

// 마이페이지 판매한 이력 조회 GET mypage/sale/{memberId}
export const mypageSoldProductsGetApi = async (memberId) => {
  try {
    const response = await authInstance.get(`/mypage/sale/${memberId}`);
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패: ', error);
    throw error;
  }
};

// 마이페이지 구매 이력 조회 GET mypage/purchase/{memberId}
export const mypagePurchasedProductsGetApi = async (memberId) => {
  try {
    const response = await authInstance.get(`/mypage/purchase/${memberId}`);
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패: ', error);
    throw error;
  }
};

// 마이페이지 판매 신청 내역 조회 GET mypage/sale-request/{memberId}
export const mypageSaleRequestListGetApi = async (memberId) => {
  try {
    const response = await authInstance.get(`/mypage/sale-request/${memberId}`);
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패: ', error);
    throw error;
  }
};

// 마이페이지 구매 신청 내역 조회 GET mypage/purchase-request/{memberId}
export const mypagePurchaseRequestListGetApi = async (memberId) => {
  try {
    const response = await authInstance.get(`/mypage/purchase-request/${memberId}`);
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패: ', error);
    throw error;
  }
};

// 마이페이지 상품 수정 신청폼 조회 GET mypage/registered/edit?memberId=4&productId=21
export const mypageProductEditRequestGetApi = async (memberId, productId) => {
  try {
    const response = await authInstance.get(`/mypage/registered/edit`, {
      params: {
        // query string
        memberId: memberId,
        productId: productId,
      },
    });
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패: ', error);
    throw error;
  }
};

// 마이페이지 상품 수정 신청폼 제출 PATCH mypage/registered/edit
export const mypageProductEditRequestPatchApi = async (updatePrice, memberId, productId) => {
  try {
    const response = await authInstance.patch(`/mypage/registered/edit`, updatePrice, {
      params: {
        memberId: memberId,
        productId: productId,
      },
    });
    return response;
  } catch (error) {
    console.log('실패: ', error);
    throw error;
  }
};

// 마이페이지 등록한 매물 삭제 요청 PATCH mypage/registered/delete/{productId}
export const mypageProductDeleteRequestPatchApi = async (productId, memberId) => {
  try {
    const response = await authInstance.patch(`/mypage/registered/delete/${productId}`, {
      memberId: memberId,
    });
    return response;
  } catch (error) {
    console.log('실패: ', error);
    throw error;
  }
};
