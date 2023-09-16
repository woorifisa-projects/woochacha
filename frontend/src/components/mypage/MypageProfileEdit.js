import { useEffect, useRef, useState } from 'react';
import { Box, Button, Card, CardMedia, Grid, Stack, Typography } from '@mui/material';
import { handleFileUpload } from '../common/FileUpload';
import { submitEditProfile } from '@/services/profileApi';
import { memberProfileEditGetApi } from '@/services/mypageApi';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import { useRecoilState } from 'recoil';
import { useRouter } from 'next/router';
import Swal from 'sweetalert2';
import { debounce } from 'lodash';

export default function MypageProfileEdit() {
  const [mounted, setMounted] = useState(false);
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);
  const [profileButton, setProfileButton] = useState(false);

  // GET
  const [memberProfileEdit, setMemberProfileEdit] = useState({
    imageUrl: '',
    name: '',
  });
  //PATCH
  const [editProfileValue, setEditProfileValue] = useState({
    imageUrl: null,
  });
  const [imagefile, setImageFile] = useState(null);
  const fileInput = useRef(null);
  const router = useRouter();
  const memberId = userLoginState.userId;

  /**
   * 수정 form 제출
   */
  const handleSubmit = async (event) => {
    setProfileButton(true);
    event.preventDefault();
    if (imagefile === null) {
      Swal.fire({
        icon: 'error',
        title: '사진을 선택해주셔야합니다.',
        showConfirmButton: true,
        // timer: 1500,
      });
      setProfileButton(false);
      return;
    }
    submitEditProfile(imagefile, memberId).then((res) => {
      if (res.status === 200) {
        Swal.fire({
          icon: 'success',
          title: '수정이 완료되었습니다.',
          showConfirmButton: false,
          timer: 1500,
        }).then(() => {
          location.href = `/mypage/${memberId}`;
        });
      }
    });
  };
  // GET
  useEffect(() => {
    memberProfileEditGetApi(memberId).then((res) => {
      if (res.status === 200) {
        setMemberProfileEdit({
          imageUrl: res.data.imageUrl,
          name: res.data.name,
        });
        setEditProfileValue({
          ...editProfileValue,
          imageUrl: res.data.imageUrl,
        });
      }
    });
    setMounted(true);
  }, []);

  const mypageProfileCss = {
    card: {
      maxWidth: '100%',
      maxHeight: '100%',
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'center',
      alignItems: 'center',
      mb: 5,
      paddingY: 8,
      textAlign: 'center',
      boxShadow: 1,
      borderRadius: '3rem',
    },
    cardMedia: {
      width: '180px',
      height: '180px',
      borderRadius: '50%',
    },
    button: { mt: 3, mb: 2 },
    typography: {
      color: 'gray',
    },
  };
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
                    memberProfileEdit,
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
            <Typography sx={mypageProfileCss.typography} variant="h5" margin="normal" name="name">
              {`${memberProfileEdit.name} 님`}
            </Typography>
          </Grid>
          <Button
            type="submit"
            size="large"
            variant="contained"
            sx={mypageProfileCss.button}
            disabled={profileButton}>
            내 프로필 수정하기
          </Button>
        </Box>
      </Card>
    )
  );
}
