import { useEffect, useState } from 'react';
import { Button, Card, CardActions, CardContent, CardMedia, Grid, Typography } from '@mui/material';
import { useRouter } from 'next/router';

export default function MypageProfile() {
  const router = useRouter();
  const [mounted, setMounted] = useState(false);

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
    profileTitle: { color: '#1490ef' },
    gridContainer: { marginTop: 5, width: 'fit-content', marginBottom: 5 },
    buttonCard: {
      marginBottom: 5,
    },
    itemTitle: {
      fontWeight: 'bold',
    },
  };

  const dummyData = {
    profileImage: 'https://woochacha.s3.ap-northeast-2.amazonaws.com/profile/user1%40woorifisa.com',
    name: '홍길동',
    phone: '01022223333',
    email: 'user@woorifisa.com',
  };

  const userId = 1; // DUMMY_DATA

  const handleMove = (url) => {
    router.push(url);
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    mounted && (
      <Card sx={mypageProfileCss.card}>
        <CardMedia
          sx={mypageProfileCss.cardMedia}
          image={dummyData.profileImage}
          title="profile-image"
        />
        <CardContent>
          <Typography gutterBottom variant="h4" component="div" sx={mypageProfileCss.profileTitle}>
            {dummyData.name} 님
          </Typography>
          <Grid container spacing={2} sx={mypageProfileCss.gridContainer}>
            <Grid item xs={6} sx={mypageProfileCss.itemTitle}>
              전화번호
            </Grid>
            <Grid item xs={6}>
              {dummyData.name}
            </Grid>
            <Grid item xs={6} sx={mypageProfileCss.itemTitle}>
              이메일
            </Grid>
            <Grid item xs={6}>
              {dummyData.email}
            </Grid>
          </Grid>
        </CardContent>
        <CardActions sx={mypageProfileCss.buttonCard}>
          <Button
            variant="contained"
            fullWidth
            onClick={() => handleMove(`/mypage/profile/edit/${userId}`)}>
            수정하기
          </Button>
        </CardActions>
      </Card>
    )
  );
}
