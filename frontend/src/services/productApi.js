import { authInstance, jsonInstance } from '@/utils/api';

/**
 * [상품] 모든 상품을 조회 (GET)
 */
export const allProductGetApi = async () => {
  try {
    const response = await jsonInstance.get('/product');
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [상품] 구매 요청 (PATCH)
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

/**
 * [상품] 상품 필터링 (POST)
 */
export const filteringProductGetApi = async (filteredItem) => {
  console.log(filteredItem);
  try {
    const response = await jsonInstance.post(`/product/filter`, filteredItem);
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [상품] 특정 상품의 상세 정보 조회 (GET)
 */
export const productDetailGetApi = async (productId) => {
  try {
    const response = await jsonInstance.get(`/product/${productId}`);
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [상품] 특정 상품의 구매 요청 (POST)
 */
export const purchaseRequest = async (purchaseForm) => {
  // userId,
  try {
    const response = await authInstance.post(`/product/purchase`, purchaseForm);
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [상품] 전체 상품의 키워드 조회 (GET)
 */
export const keywordProductGetApi = async (keyword) => {
  console.log(keyword);
  try {
    const response = await jsonInstance.get('/product/search', {
      params: { keyword: keyword },
    });
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [판매] 판매 폼 조회 시, 차고지 정보 불러오기 (GET)
 */
export const getBranchApi = async () => {
  try {
    const response = await authInstance.get('/products/sale');
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};

/**
 * [판매] 판매 폼 요청 (POST)
 */
export const saleFormRequest = async (saleForm) => {
  try {
    const response = await authInstance.post('/products/sale', saleForm);
    const data = response.data;
    return data;
  } catch (error) {
    console.log('실패 : ', error);
    throw error;
  }
};
