import { useEffect, useRef, useState } from 'react';
import { Box, Button, Card, CardMedia, Grid, Stack, TextField } from '@mui/material';
import { handleFileUpload } from '../common/FileUpload';
import { submitEditProfile } from '@/services/profileApi';

export default function MypageProfileEdit() {
  const [mounted, setMounted] = useState(false);
  const [editProfileValue, setEditProfileValue] = useState({
    imageUrl: null,
    nameValue: '',
  });
  const [imagefile, setImageFile] = useState(null);
  const fileInput = useRef(null);

  // DUMMY_DATA
  const dummyData = {
    profileImage: 'https://woochacha.s3.ap-northeast-2.amazonaws.com/profile/user1%40woorifisa.com',
    name: '홍길동',
    phone: '01022223333',
    email: 'user@woorifisa.com',
  };
  const userId = 1;

  /**
   * 이름 변경
   */
  const handleChangeName = (e) => {
    setEditProfileValue({
      ...editProfileValue,
      nameValue: e.target.value,
    });
  };

  /**
   * 수정 form 제출
   */
  const handleSubmit = async (event) => {
    event.preventDefault();
    submitEditProfile(event, imagefile, editProfileValue, userId);
  };

  const mypageProfileCss = {
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
      <Card sx={mypageProfileCss.card}>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <Stack direction="column" alignItems="center" spacing={2} mb={5}>
            {editProfileValue.imageUrl && (
              <CardMedia
                sx={mypageProfileCss.cardMedia}
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
                onChange={(event) =>
                  handleFileUpload(
                    event,
                    dummyData,
                    setImageFile,
                    setEditProfileValue,
                    editProfileValue,
                  )
                }
                ref={fileInput}
              />
              <Button size="large" variant="outlined" component="span">
                사진 수정하기
              </Button>
            </label>
          </Stack>
          <Grid>
            <TextField
              margin="normal"
              required
              name="name"
              label="이름 변경"
              value={editProfileValue.nameValue}
              onChange={(e) => handleChangeName(e)}
            />
          </Grid>
          <Button type="submit" size="large" variant="contained" sx={mypageProfileCss.button}>
            내 프로필 수정하기
          </Button>
        </Box>
      </Card>
    )
  );
}
