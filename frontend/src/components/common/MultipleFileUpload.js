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
  let imageUploadUrlLists = [];

  for (let i = 0; i < imageLists.length; i++) {
    const currentImageUrl = URL.createObjectURL(imageLists[i]);
    imageUrlLists.push(currentImageUrl);
  }

  if (imageLists) {
    // upload
    for (let i = 0; i < imageLists.length; i++) {
      imageUploadUrlLists.push(imageLists[i]);
    }
    setPreviewImageList(imageUrlLists); // preview 값 저장
    setUploadFileValue(imageUploadUrlLists); // image file 값 저장
  }
};
