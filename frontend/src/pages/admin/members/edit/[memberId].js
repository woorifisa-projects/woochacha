import { useEffect, useRef, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { Box, Button, Card, CardMedia, Stack, Typography } from '@mui/material';
import withAdminAuth from '@/hooks/withAdminAuth';
import { TextField, InputLabel } from '@mui/material';
import { Radio, RadioGroup, FormControlLabel } from '@mui/material';

function AdminUserEdit() {
  const [mounted, setMounted] = useState(false);

  const [editProfileValue, setEditProfileValue] = useState({
    imageUrl: null,
    nameValue: '',
    isDefaultImage: false, // 기본 이미지로 변경 체크 상태
  });
  const fileInput = useRef(null);

  const adminUserProfileEditCss = {
    mypageTitle: {
      my: 4,
      color: '#1490ef',
      fontWeight: 'bold',
    },
    card: {
      maxWidth: '90rem',
      maxHeight: '45rem',
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'center',
      alignItems: 'center',
      marginBottom: 10,
      paddingY: 1,
      textAlign: 'center',
      boxShadow: 7,
      borderRadius: '3rem',
    },
    cardMedia: {
      width: '180px',
      height: '180px',
      borderRadius: '50%',
    },
    button: { mt: 3, mb: 2 },
  };

  // DUMMY_DATA
  const dummyData = {
    profileImage: 'https://woochacha.s3.ap-northeast-2.amazonaws.com/profile/user1%40woorifisa.com',
    name: '홍길동',
    phone: '01022223333',
    email: 'user@woorifisa.com',
  };
  const userId = 1;

  /**
   * 수정 form 제출
   */
  const handleSubmit = async (event) => {
    event.preventDefault();
    // submitEditProfile(event, imagefile, editProfileValue, userId);
  };

  /**
   * "기본 이미지로 변경 체크박스 로직"
   */
  const handleFileUpload = (
    event,
    dummyData,
    setImageFile,
    setEditProfileValue,
    editProfileValue,
  ) => {
    const selectedFile = event.target.files[0];

    if (selectedFile) {
      // "기본 이미지로 변경" 체크 여부에 따라 다르게 처리
      if (editProfileValue.isDefaultImage) {
        // 기본 이미지로 변경하는 로직 추가
        setEditProfileValue({
          ...editProfileValue,
          imageUrl: dummyData.profileImage,
        });
      } else {
        // 선택한 이미지 사용
        setImageFile(selectedFile);
        const imageUrl = URL.createObjectURL(selectedFile);
        setEditProfileValue({
          ...editProfileValue,
          imageUrl,
        });
      }
    }
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
    setEditProfileValue({
      imageUrl: dummyData.profileImage,
      nameValue: dummyData.name,
    });
  }, []);

  return (
    mounted && (
      <>
        <Card sx={{ ...adminUserProfileEditCss.card, marginTop: '25px' }}>
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
            <Typography
              sx={{
                ...adminUserProfileEditCss.mypageTitle,
                borderBottom: '1.5px solid #1490ef', // 파란색 밑줄 추가
                paddingBottom: '10px', // 파란색 밑줄 위에 공백 추가
              }}
              component="h4"
              variant="h5"
              gutterBottom>
              수정하기
            </Typography>
            <Stack direction="column" alignItems="center" spacing={2} mb={1}>
              {editProfileValue.imageUrl && (
                <CardMedia
                  sx={adminUserProfileEditCss.cardMedia}
                  image={editProfileValue.imageUrl}
                  title="이미지 업로드"
                />
              )}
              <label htmlFor="upload-image">
                <input id="upload-image" hidden accept="image/*" type="file" ref={fileInput} />
              </label>
              <label className="checkbox-label">
                <input
                  type="checkbox"
                  checked={editProfileValue.isDefaultImage}
                  onChange={(e) =>
                    setEditProfileValue({
                      ...editProfileValue,
                      isDefaultImage: e.target.checked,
                    })
                  }
                />
                기본 이미지로 변경
              </label>
            </Stack>
            <InputLabel
              htmlFor="nameValue"
              sx={{ fontWeight: 'bold', fontSize: '1rem', mt: 0, mr: 24.5, color: 'gray' }}>
              회원 이름
            </InputLabel>
            <TextField
              variant="outlined"
              fullWidth
              disabled
              value={editProfileValue.nameValue}
              sx={{
                width: 'calc(100% - 0px)',
                height: '40px',
                margin: '0 0px',
                marginBottom: '10px',
              }}
            />
            <Typography
              variant="body1"
              sx={{
                fontWeight: 'bold',
                fontSize: '1rem',
                mt: 2,
                textAlign: 'left',
                color: 'gray',
              }}>
              상태
            </Typography>

            <RadioGroup
              row // 이 부분이 라디오 박스를 가로로 표시하도록 합니다.
              aria-label="회원 상태"
              name="userStatus"
              value={editProfileValue.userStatus} // 선택된 값
              onChange={(e) => {
                // 선택된 값을 상태에 업데이트
                setEditProfileValue({
                  ...editProfileValue,
                  userStatus: e.target.value,
                });
              }}
              sx={{ mt: 1, justifyContent: 'center', mt: 0 }} // 라디오 박스들을 중앙 정렬
            >
              <FormControlLabel
                value="normal" // "일반"
                control={<Radio />} // 라디오 버튼
                label="일반" // 라벨 텍스트
                sx={{ marginRight: '70px' }}
              />
              <FormControlLabel
                value="restricted" // "이용제한" 값
                control={<Radio />} // 라디오 버튼
                label="이용제한" // 라벨 텍스트
              />
            </RadioGroup>
            <Box sx={{ display: 'flex', justifyContent: 'center', mt: 0.5 }}>
              <Button
                type="button"
                size="large"
                variant="contained"
                sx={{ ...adminUserProfileEditCss.button, marginRight: '10px' }}>
                수정
              </Button>
              <Button
                type="button"
                size="large"
                variant="contained"
                sx={{ ...adminUserProfileEditCss.button, backgroundColor: 'navy' }}>
                취소
              </Button>
            </Box>
          </Box>
        </Card>
      </>
    )
  );
}

// side menu 레이아웃
AdminUserEdit.Layout = withAdminAuth(AdminPageLayout);
export default AdminUserEdit;
