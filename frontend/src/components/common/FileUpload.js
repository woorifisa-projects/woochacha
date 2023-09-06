/**
 * image
 */
export const handleFileUpload = (
  event,
  data,
  setImageFile,
  setEditProfileValue,
  editProfileValue,
) => {
  if (event.target.files[0]) {
    setImageFile(event.target.files[0]);
  } else {
    setEditProfileValue({
      ...editProfileValue,
      imageUrl: data.imageUrl,
    });
    return;
  }

  // image - preview
  const reader = new FileReader();

  reader.onloadend = () => {
    setEditProfileValue({
      ...editProfileValue,
      imageUrl: reader.result,
    });
  };

  reader.readAsDataURL(event.target.files[0]);
};
