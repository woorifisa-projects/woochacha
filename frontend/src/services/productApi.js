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
