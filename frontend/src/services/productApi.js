import { jsonInstance } from '@/utils/api';

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
