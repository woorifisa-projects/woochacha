import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { Button, Card, CardActions, CardContent, CardMedia, Grid, Typography } from '@mui/material';
import withAdminAuth from '@/hooks/withAdminAuth';
import { useRouter } from 'next/router';

function AdminUserDetail() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();

  const adminUserProfileCss = {
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
      <>
        <Typography sx={adminUserProfileCss.mypageTitle} component="h4" variant="h4" gutterBottom>
          관리자 페이지 - 유저 상세조회
        </Typography>
        <Card sx={adminUserProfileCss.card}>
          <CardMedia
            sx={adminUserProfileCss.cardMedia}
            image={dummyData.profileImage}
            title="profile-image"
          />
          <CardContent>
            <Typography
              gutterBottom
              variant="h4"
              component="div"
              sx={adminUserProfileCss.profileTitle}>
              {dummyData.name} 님
            </Typography>
            <Grid container spacing={2} sx={adminUserProfileCss.gridContainer}>
              <Grid item xs={6} sx={adminUserProfileCss.itemTitle}>
                전화번호
              </Grid>
              <Grid item xs={6}>
                {dummyData.phone}
              </Grid>
              <Grid item xs={6} sx={adminUserProfileCss.itemTitle}>
                이메일
              </Grid>
              <Grid item xs={6}>
                {dummyData.email}
              </Grid>
            </Grid>
          </CardContent>
          <CardActions sx={adminUserProfileCss.buttonCard}>
            <Button
              variant="contained"
              fullWidth
              onClick={() => handleMove(`/admin/members/edit/${userId}`)}>
              수정하기
            </Button>
          </CardActions>
        </Card>
      </>
    )
  );
}

// side menu 레이아웃
AdminUserDetail.Layout = withAdminAuth(AdminPageLayout);
export default AdminUserDetail;
