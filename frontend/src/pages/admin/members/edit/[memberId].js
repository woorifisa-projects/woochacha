import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import {
  Box,
  Button,
  Card,
  CardMedia,
  Stack,
  Typography,
  Checkbox,
  Radio,
  RadioGroup,
  FormControlLabel,
} from '@mui/material';
import withAdminAuth from '@/hooks/withAdminAuth';
import { useRouter } from 'next/router';
import { oneUserEditPatchApi, oneUserGetApi } from '@/services/adminpageApi';
import { SwalModals } from '@/utils/modal';

function AdminUserEdit(props) {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();

  const [editProfileValue, setEditProfileValue] = useState({
    imageUrl: null,
    nameValue: '',
    status: '', // 일반유저 or 제한유저
    isChecked: false, // 기본 이미지로 변경 체크 상태
  });

  const { memberId } = props;

  /**
   * 수정 form 제출
   */
  const handleSubmit = async (event) => {
    event.preventDefault();
    oneUserEditPatchApi(editProfileValue, memberId).then((res) => {
      if (res.status === 200) {
        SwalModals('success', '수정 완료', '수정이 완료되었습니다!', false).then(() =>
          router.push('/admin/members'),
        );
      }
    });
  };

  /**
   * radio change 함수
   */
  const handleRadioChange = (e) => {
    // 선택된 값을 상태에 업데이트
    setEditProfileValue({
      ...editProfileValue,
      status: e.target.value,
    });
  };

  /**
   * 체크박스 change 함수
   */
  const handleChangeCheckbox = (event) => {
    setEditProfileValue({
      ...editProfileValue,
      isChecked: event.target.checked, // 기본 이미지로 변경 체크 상태
    });
  };

  const handleMoveUrl = (url) => {
    router.push(url);
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    memberId &&
      oneUserGetApi(memberId).then((res) => {
        if (res.status === 200) {
          setEditProfileValue({
            imageUrl: res.data.memberInfoDto.profileImage,
            nameValue: res.data.memberInfoDto.name,
            status: res.data.memberInfoDto.isAvailable,
            isChecked: false, // 기본 이미지로 변경 체크 상태
          });
        }
      });
    setMounted(true);
  }, []);

  const adminUserProfileEditCss = {
    mypageTitle: {
      my: 4,
      color: '#1490ef',
      fontWeight: 'bold',
      borderBottom: '1.5px solid #1490ef',
      paddingBottom: '10px',
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
    subTitle: {
      fontWeight: 'bold',
      fontSize: '1rem',
      mt: 2,
      textAlign: 'left',
      color: 'gray',
    },
    subContent: {
      fontWeight: 'bold',
      fontSize: '1rem',
      mt: 2,
      textAlign: 'left',
      color: 'black',
    },
    buttonBox: { display: 'flex', justifyContent: 'center', mt: 0.5 },
    button: { mt: 3, mb: 2, marginRight: '10px' },
    radioBox: { mt: 1, justifyContent: 'center' },
    radioLeft: { marginRight: '70px' },
  };

  return (
    mounted &&
    editProfileValue.imageUrl && (
      <>
        <Card sx={{ ...adminUserProfileEditCss.card, marginTop: '25px' }}>
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
            <Typography
              sx={adminUserProfileEditCss.mypageTitle}
              component="h4"
              variant="h5"
              gutterBottom>
              수정하기
            </Typography>
            <Stack direction="column" alignItems="center" spacing={2} mb={1}>
              {
                <CardMedia
                  sx={adminUserProfileEditCss.cardMedia}
                  image={editProfileValue.imageUrl}
                  title="사용자 이미지"
                />
              }
              <label>
                <Checkbox
                  checked={editProfileValue.isChecked}
                  onChange={handleChangeCheckbox}
                  inputProps={{ 'aria-label': 'controlled' }}
                />
                기본이미지로 수정
              </label>
            </Stack>
            <Typography variant="body1" sx={adminUserProfileEditCss.subTitle}>
              회원이름
            </Typography>
            <Typography sx={adminUserProfileEditCss.subContent}>
              {editProfileValue.nameValue}
            </Typography>

            <Typography variant="body1" sx={adminUserProfileEditCss.subTitle}>
              상태
            </Typography>

            <RadioGroup
              row
              aria-label="회원 상태"
              name="userStatus"
              value={editProfileValue.userStatus} // 선택된 값
              defaultValue={editProfileValue.status}
              onChange={handleRadioChange}
              sx={adminUserProfileEditCss.radioBox} // 라디오 박스들을 중앙 정렬
            >
              <FormControlLabel
                value={1} // "일반"
                control={<Radio />} // 라디오 버튼
                label="일반" // 라벨 텍스트
                sx={adminUserProfileEditCss.radioLeft}
              />
              <FormControlLabel
                value={0} // 이용제한 값
                control={<Radio />} // 라디오 버튼
                label="이용제한" // 라벨 텍스트
              />
            </RadioGroup>
            <Box sx={adminUserProfileEditCss.buttonBox}>
              <Button
                onClick={handleSubmit}
                type="button"
                size="large"
                variant="contained"
                sx={adminUserProfileEditCss.button}>
                수정
              </Button>
              <Button
                type="button"
                size="large"
                variant="contained"
                sx={adminUserProfileEditCss.button}
                onClick={() => handleMoveUrl(`/admin/members/${memberId}`)}
                color="error">
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

export async function getServerSideProps(context) {
  const memberId = context.params.memberId;
  return {
    props: {
      memberId,
    },
  };
}
