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
      my: 2,
      paddingY: 2,
      textAlign: 'center',
      boxShadow: 'none',
      borderRadius: '3rem',
    },
    cardMedia: {
      width: '200px',
      height: '200px',
      borderRadius: '15px',
      boxShadow: 3,
    },
    name: {
      padding: 3,
      borderRadius: '15px',
      border: '1px solid #F95700',
      boxShadow: '70px 0px 50px -50px rgba(0,0,0,0.1)',
    },
    gridContainer: { marginTop: 5, width: 'fit-content', marginBottom: 5, textAlign: 'start' },
    buttonCard: {
      marginBottom: 5,
    },
    itemTitle: {
      fontWeight: 'bold',
      textAlign: 'center',
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
          <Typography
            gutterBottom
            variant="h4"
            component="div"
            color="primary"
            sx={mypageProfileCss.name}>
            {memberProfile.name} 님
          </Typography>
          <Grid container spacing={2} sx={mypageProfileCss.gridContainer}>
            <Grid item xs={6} sx={mypageProfileCss.itemTitle}>
              전화번호
            </Grid>
            <Grid item xs={6}>
              {memberProfile.phone.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`)}
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
