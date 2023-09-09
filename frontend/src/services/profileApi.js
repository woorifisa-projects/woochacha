import { authFormInstance } from '@/utils/api';

export const submitEditProfile = async (imagefile, userId) => {
  const formData = new FormData();
  formData.append('multipartFile', imagefile);
  try {
    const response = await authFormInstance.patch(`/mypage/profile/edit/${userId}`, formData);
    return response;
  } catch (err) {
    console.log(err);
  }
};
