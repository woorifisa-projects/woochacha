import { formInstance } from '@/utils/api';

export const submitEditProfile = async (event, imagefile, editProfileValue, userId) => {
  const formData = new FormData(event.currentTarget);
  formData.append('profileImage', imagefile); // 이미지 파일값 할당
  formData.append('name', editProfileValue.nameValue);

  // FormData의 value 확인용 console.log
  for (let value of formData.values()) {
    console.log(value);
  }
  try {
    const response = await formInstance.patch(`/mypage/profile/edit/${userId}`, formData);
    console.log(response);
  } catch (err) {
    console.log(err);
  }
};
