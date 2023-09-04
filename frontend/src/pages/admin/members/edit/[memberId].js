import { useEffect, useRef, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { Box, Button, Card, CardMedia, Grid, Stack, TextField, Typography } from '@mui/material';
import withAdminAuth from '@/hooks/withAdminAuth';

function AdminUserEdit() {
  const [mounted, setMounted] = useState(false);

  const [editProfileValue, setEditProfileValue] = useState({
    imageUrl: null,
    nameValue: '',
  });
  const fileInput = useRef(null);

  const adminUserProfileEditCss = {
    mypageTitle: {
      my: 10,
      color: '#1490ef',
      fontWeight: 'bold',
    },
    card: {
      maxWidth: '50rem',
      maxHeight: '45rem',
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'center',
      alignItems: 'center',
      marginBottom: 10,
      paddingY: 8,
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
        <Typography
          sx={adminUserProfileEditCss.mypageTitle}
          component="h4"
          variant="h4"
          gutterBottom>
          관리자 페이지 - 유저 상세 정보 수정
        </Typography>
        <Card sx={adminUserProfileEditCss.card}>
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
            <Stack direction="column" alignItems="center" spacing={2} mb={5}>
              {editProfileValue.imageUrl && (
                <CardMedia
                  sx={adminUserProfileEditCss.cardMedia}
                  image={editProfileValue.imageUrl}
                  title="이미지 업로드"
                />
              )}
              <label htmlFor="upload-image">
                <input
                  id="upload-image"
                  hidden
                  accept="image/*"
                  type="file"
                  // onChange={(event) =>
                  //   handleFileUpload(
                  //     event,
                  //     dummyData,
                  //     setImageFile,
                  //     setEditProfileValue,
                  //     editProfileValue,
                  //   )
                  // }
                  ref={fileInput}
                />
                <Button size="large" variant="outlined" component="span">
                  사진 수정하기
                </Button>
              </label>
            </Stack>
            <Grid>
              <Typography variant="h5">{editProfileValue.nameValue}</Typography>
            </Grid>
            <Button
              type="submit"
              size="large"
              variant="contained"
              sx={adminUserProfileEditCss.button}>
              {dummyData.name} 님의 프로필 수정하기
            </Button>
          </Box>
        </Card>
      </>
    )
  );
}

// side menu 레이아웃
AdminUserEdit.Layout = withAdminAuth(AdminPageLayout);
export default AdminUserEdit;
