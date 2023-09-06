import { formInstance } from '@/utils/api';

export const submitEditProfile = async (event, imagefile, editProfileValue, userId) => {
  const formData = new FormData(event.currentTarget);
  formData.append('multipartFile', imagefile); // 이미지 파일값 할당

  try {
    const response = await formInstance.patch(`/mypage/profile/edit/${userId}`, formData);
    return response;
  } catch (err) {
    console.log(err);
  }
};
