/**
 * multiple image
 */

// upload
export const handleMultipleFileUpload = (
  e,
  setUploadFileValue,
  previewImageList,
  setPreviewImageList,
) => {
  let imageLists = e.target.files;
  // let imageUrlLists = [...previewImageList];
  let imageUrlLists = [];

  for (let i = 0; i < imageLists.length; i++) {
    const currentImageUrl = URL.createObjectURL(imageLists[i]);
    imageUrlLists.push(currentImageUrl);
  }

  if (imageLists) {
    if (imageLists.length > 4) {
      alert('4개까지만 등록하실 수 있습니다!');
      return;
    }
    setUploadFileValue(imageLists); // file upload 값 저장
    setPreviewImageList(imageUrlLists); // preview 값 저장

    // upload
    const formData = new FormData();
    for (let i = 0; i < imageLists.length; i++) {
      formData.append('files', imageLists[i]);
    }

    // FormData의 value 확인용 콘솔
    for (let value of formData.values()) {
      console.log(value);
    }
    return formData;
  }
};
