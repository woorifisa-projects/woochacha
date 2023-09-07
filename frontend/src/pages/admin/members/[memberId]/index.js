import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { Button, Card, CardActions, CardContent, CardMedia, Grid, Typography } from '@mui/material';
import withAdminAuth from '@/hooks/withAdminAuth';
import { useRouter } from 'next/router';
import { oneUserGetApi, oneMemberDeletePatchApi } from '@/services/adminpageApi';
import BasicModal from '@/components/common/BasicModal';
import { MEMBER_DELETE_MODAL } from '@/constants/string';

function AdminUserDetail() {
  const [mounted, setMounted] = useState(false);
  const [userDetailInfo, setUserDetailInfo] = useState();
  const router = useRouter();
  const currentPath = router.query; // 현재 경로 읽어오기

  // Modal 버튼 클릭 유무 *
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);

  const adminUserProfileCss = {
    card: {
      marginTop: '25px',
      maxWidth: '100%',
      maxHeight: '100%',
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
      marginTop: 2,
      marginBottom: 2,
      width: '220px',
      height: '220px',
      borderRadius: '50%',
    },
    profileTitle: { color: '#1490ef' },
    gridContainer: {
      textAlign: 'left',
      marginTop: 5,
      width: 'fit-content',
      marginBottom: 5,
      paddingLeft: 3,
    },
    buttonCard: {
      marginBottom: 5,
    },
    itemTitle: {
      color: '#1490ef',
      fontWeight: 'bold',
      borderBottom: '1px solid #1490ef',
    },
    itemContent: {
      fontWeight: 'bold',
    },
  };

  const handleMove = (url) => {
    router.push(url);
  };

  const handleDeleteMember = () => {
    currentPath.memberId &&
      oneMemberDeletePatchApi(currentPath.memberId)
        .then((data) => {
          alert(data);
          router.push('/admin/members');
        })
        .catch((error) => {
          console.log('실패: ', error);
        });
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    currentPath.memberId &&
      oneUserGetApi(currentPath.memberId).then((data) => {
        setUserDetailInfo(data);
      });
    setMounted(true);
  }, []);

  return (
    mounted &&
    userDetailInfo && (
      <>
        <Card sx={adminUserProfileCss.card}>
          <CardMedia
            sx={adminUserProfileCss.cardMedia}
            image={userDetailInfo.memberInfoDto.profileImage}
            title="profile-image"
          />
          <CardContent>
            <Typography
              gutterBottom
              variant="h4"
              component="div"
              sx={adminUserProfileCss.profileTitle}>
              {userDetailInfo.memberInfoDto.name}
            </Typography>
            <Grid container spacing={2} sx={adminUserProfileCss.gridContainer}>
              <Grid item xs={12} sm={6}>
                <Grid container spacing={2} ml={0.5}>
                  <Grid item xs={6} sx={adminUserProfileCss.itemTitle}>
                    회원 정보
                  </Grid>
                  <Grid item xs={4} sx={adminUserProfileCss.itemTitle}></Grid>
                </Grid>

                <Grid container spacing={2} sx={adminUserProfileCss.gridContainer}>
                  <Grid item xs={4} sx={adminUserProfileCss.itemContent}>
                    상태
                  </Grid>
                  <Grid item xs={8}>
                    <Typography variant="body2" color="primary">
                      {userDetailInfo.memberInfoDto.isAvailable === 1 ? '정상' : '이용 제한'}
                    </Typography>
                  </Grid>
                  <Grid item xs={4} sx={adminUserProfileCss.itemContent}>
                    전화번호
                  </Grid>
                  <Grid item xs={8}>
                    {userDetailInfo.memberInfoDto.phone}
                  </Grid>
                  <Grid item xs={4} sx={adminUserProfileCss.itemContent}>
                    이메일
                  </Grid>
                  <Grid item xs={8}>
                    {userDetailInfo.memberInfoDto.email}
                  </Grid>
                  <Grid item xs={4} sx={adminUserProfileCss.itemContent}>
                    가입일
                  </Grid>
                  <Grid item xs={8}>
                    {userDetailInfo.memberInfoDto.createdAt}
                  </Grid>
                </Grid>
              </Grid>
              <Grid item xs={12} sm={6}>
                <Grid container spacing={2} ml={0.5}>
                  <Grid item xs={6} sx={adminUserProfileCss.itemTitle}>
                    회원 정보
                  </Grid>
                  <Grid item xs={4} sx={adminUserProfileCss.itemTitle}></Grid>
                </Grid>
                <Grid container spacing={2} sx={adminUserProfileCss.gridContainer}>
                  <Grid item xs={6} sx={adminUserProfileCss.itemContent}>
                    판매 중
                  </Grid>
                  <Grid item xs={6}>
                    {`${userDetailInfo.onSale} 건`}
                  </Grid>
                  <Grid item xs={6} sx={adminUserProfileCss.itemContent}>
                    판매 완료
                  </Grid>
                  <Grid item xs={6}>
                    {`${userDetailInfo.completeSale} 건`}
                  </Grid>
                  <Grid item xs={6} sx={adminUserProfileCss.itemContent}>
                    구매 대기
                  </Grid>
                  <Grid item xs={6}>
                    {`${userDetailInfo.onPurchase} 건`}
                  </Grid>
                  <Grid item xs={6} sx={adminUserProfileCss.itemContent}>
                    구매 완료
                  </Grid>
                  <Grid item xs={6}>
                    {`${userDetailInfo.completePurchase} 건`}
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
          </CardContent>
          <CardActions sx={adminUserProfileCss.buttonCard}>
            <Button
              variant="contained"
              fullWidth
              onClick={() => handleMove(`/admin/members/edit/${currentPath.memberId}`)}>
              수정
            </Button>
            <Button variant="contained" color="error" onClick={handleClickModal}>
              삭제
            </Button>
          </CardActions>
        </Card>
        {showModal && (
          <BasicModal
            onClickModal={handleClickModal}
            isOpen={showModal}
            modalContent={MEMBER_DELETE_MODAL.CONTENTS}
            callBackFunc={handleDeleteMember}
          />
        )}
      </>
    )
  );
}

// side menu 레이아웃
AdminUserDetail.Layout = withAdminAuth(AdminPageLayout);
export default AdminUserDetail;
