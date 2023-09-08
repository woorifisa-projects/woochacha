import { authInstance, jsonInstance } from '@/utils/api';

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
