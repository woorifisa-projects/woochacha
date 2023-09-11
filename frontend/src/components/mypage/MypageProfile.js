import { useEffect, useState } from 'react';
import { Button, Card, CardActions, CardContent, CardMedia, Grid, Typography } from '@mui/material';
import { useRouter } from 'next/router';
import { memberProfileGetApi } from '@/services/mypageApi';
import { useRecoilState } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';

export default function MypageProfile() {
  const router = useRouter();
  const [mounted, setMounted] = useState(false);
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);
  const [memberProfile, setMemberProfile] = useState({
    profileImage: '',
    name: '',
    phone: '',
    email: '',
  });

  const mypageProfileCss = {
    card: {
      maxWidth: '100%',
      maxHeight: '100%',
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'center',
      alignItems: 'center',
      mb: 5,
      paddingY: 5,
      textAlign: 'center',
      boxShadow: 1,
      borderRadius: '3rem',
    },
    cardMedia: {
      width: '180px',
      height: '180px',
      borderRadius: '50%',
    },
    profileTitle: { color: '#1490ef' },
    gridContainer: { marginTop: 5, width: 'fit-content', marginBottom: 5 },
    buttonCard: {
      marginBottom: 5,
    },
    itemTitle: {
      fontWeight: 'bold',
    },
  };

  const memberId = userLoginState.userId;

  const handleMove = (url) => {
    router.push(url);
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    memberProfileGetApi(memberId).then((res) => {
      if (res.status === 200) {
        setMemberProfile({
          profileImage: res.data.profileImage,
          name: res.data.name,
          phone: res.data.phone,
          email: res.data.email,
        });
      }
    });
    setMounted(true);
  }, []);

  return (
    mounted && (
      <Card sx={mypageProfileCss.card}>
        <CardMedia
          sx={mypageProfileCss.cardMedia}
          image={memberProfile.profileImage}
          title="profile-image"
        />
        <CardContent>
          <Typography gutterBottom variant="h4" component="div" sx={mypageProfileCss.profileTitle}>
            {memberProfile.name} 님
          </Typography>
          <Grid container spacing={2} sx={mypageProfileCss.gridContainer}>
            <Grid item xs={6} sx={mypageProfileCss.itemTitle}>
              전화번호
            </Grid>
            <Grid item xs={6}>
              {memberProfile.phone}
            </Grid>
            <Grid item xs={6} sx={mypageProfileCss.itemTitle}>
              이메일
            </Grid>
            <Grid item xs={6}>
              {memberProfile.email}
            </Grid>
          </Grid>
        </CardContent>
        <CardActions sx={mypageProfileCss.buttonCard}>
          <Button
            variant="contained"
            fullWidth
            onClick={() => handleMove(`/mypage/profile/edit/${memberId}`)}>
            수정하기
          </Button>
        </CardActions>
      </Card>
    )
  );
}
