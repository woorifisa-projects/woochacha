import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { Button, Card, CardActions, CardContent, CardMedia, Grid, Typography } from '@mui/material';
import withAdminAuth from '@/hooks/withAdminAuth';
import { useRouter } from 'next/router';
import { oneUserGetApi, oneMemberDeletePatchApi } from '@/services/adminpageApi';
import BasicModal from '@/components/common/BasicModal';
import { MEMBER_DELETE_MODAL } from '@/constants/string';
import { SwalModals } from '@/utils/modal';
import LoadingBar from '@/components/common/LoadingBar';

function AdminUserDetail(props) {
  const [mounted, setMounted] = useState(false);
  const [userDetailInfo, setUserDetailInfo] = useState();
  const router = useRouter();

  const { memberId } = props;

  // Modal 버튼 클릭 유무 *
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);

  const [disabledSubmitBtn, setDisabledSubmitBtn] = useState(false); // 버튼 비활성화 여부
  const [isSubmitting, setIsSubmitting] = useState(false); // 진행 중 상태 확인

  const adminUserProfileCss = {
    card: {
      maxWidth: '100%',
      maxHeight: '100%',
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'center',
      alignItems: 'center',
      mb: 2,
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
    gridContainer: {
      textAlign: 'left',
      marginTop: 5,
      width: 'fit-content',
    },
    buttonCard: {
      marginBottom: 5,
    },
    itemTitle: {
      fontWeight: 'bold',
      color: '#F95700',
      borderBottom: '1px solid #F95700',
    },
    itemContent: {
      fontWeight: 'bold',
    },
  };

  const handleMove = (url) => {
    router.push(url);
  };

  const handleDeleteMember = () => {
    // 요청이 진행중인 경우
    if (isSubmitting) {
      return;
    }

    // 요청 시작
    setIsSubmitting(true);

    memberId &&
      oneMemberDeletePatchApi(memberId)
        .then((res) => {
          SwalModals('success', '삭제 완료', res.data, false);
          router.push('/admin/members');
        })
        .catch((error) => {
          console.log('실패: ', error);
        })
        .finally(() => {
          setIsSubmitting(false); // 요청 종료 후 상태 변경
        });
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    memberId &&
      oneUserGetApi(memberId).then((res) => {
        if (res.status === 200) {
          setUserDetailInfo(res.data);
        }
      });
    setMounted(true);
  }, []);

  return mounted && userDetailInfo ? (
    <>
      <Card sx={adminUserProfileCss.card}>
        <CardMedia
          sx={adminUserProfileCss.cardMedia}
          image={userDetailInfo.memberInfoDto.profileImage}
          title="profile-image"
        />
        <CardContent>
          <Typography gutterBottom variant="h4" component="div" color="primary" fontWeight="bold">
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
            onClick={() => handleMove(`/admin/members/edit/${memberId}`)}>
            수정
          </Button>
          <Button
            variant="contained"
            color="error"
            onClick={handleClickModal}
            disabled={disabledSubmitBtn || isSubmitting}>
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
  ) : (
    <LoadingBar />
  );
}

// side menu 레이아웃
AdminUserDetail.Layout = withAdminAuth(AdminPageLayout);
export default AdminUserDetail;

export async function getServerSideProps(context) {
  const memberId = context.params.memberId;
  // const res = await oneUserGetApi(memberId).then((data) => data);
  return {
    props: {
      memberId,
    },
  };
}
