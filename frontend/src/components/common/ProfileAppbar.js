import { useState } from 'react';
import { useRouter } from 'next/router';
import { useRecoilState } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import { Button, Menu, MenuItem, Tooltip, Typography } from '@mui/material';
import {
  HEADER_LOGIN_ADMIN_MENU,
  HEADER_LOGIN_USER_MENU,
  HEADER_UNLOGIN_USER_MENU,
} from '@/constants/string';
import LocalStorage from '@/utils/localStorage';
import Swal from 'sweetalert2';
import { logoutApi, signoutApi } from '@/services/authApi';

export default function ProfileAppbar() {
  const router = useRouter();
  const [userLoginState, setUserLoginState] = useRecoilState(userLoggedInState);
  const [anchorElUser, setAnchorElUser] = useState(null);

  const profileAppbarCss = {
    btnColor: {
      backgroundColor: '#000',
      px: 3,
      fontWeight: 800,
      borderRadius: '22px',
    },
  };

  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  const handleCloseMiniMenu = (url) => {
    if (url.includes('logout')) {
      logoutApi(userLoginState.userId).then((res) => {
        if (res.status === 200) {
          // 로그아웃 성공 시 처리
          LocalStorage.removeItem('loginToken');
          setUserLoginState({
            loginStatus: false,
            userId: null,
            userName: null,
          });

          Swal.fire({
            icon: 'success',
            title: `로그아웃`,
            html: `로그아웃이 완료되었습니다.`,
            showConfirmButton: false,
            showClass: {
              popup: 'animate__animated animate__fadeInDown',
            },
            hideClass: {
              popup: 'animate__animated animate__fadeOutUp',
            },
            timer: 1500,
          }).then(() => router.push('/'));
        }
      });
      return;
    }

    if (url.includes('signout')) {
      Swal.fire({
        icon: 'warning',
        title: `정말 우차차를 떠나시겠어요?`,
        html: `탈퇴 버튼 클릭 시, 계정은 삭제됩니다`,
        showDenyButton: true,
        showConfirmButton: true,
        confirmButtonText: '탈퇴할래요',
        denyButtonText: '탈퇴안할게요',
      })
        .then((result) => {
          if (result.isConfirmed) {
            signoutApi(userLoginState.userId).then((res) => {
              if (res.status === 200) {
                // 회원탈퇴 성공 시 처리
                LocalStorage.removeItem('loginToken');
                setUserLoginState({
                  loginStatus: false,
                  userId: null,
                  userName: null,
                });

                Swal.fire({
                  icon: 'success',
                  title: `회원탈퇴`,
                  html: `회원탈퇴가 완료되었습니다.`,
                  showConfirmButton: false,
                  showClass: {
                    popup: 'animate__animated animate__fadeInDown',
                  },
                  hideClass: {
                    popup: 'animate__animated animate__fadeOutUp',
                  },
                  timer: 1500,
                }).then(() => router.push('/'));
              }
            });
          }
        })

        .then(() => {});
      return;
    }

    router.push(url);
    setAnchorElUser(null);
  };

  return (
    <>
      {userLoginState.loginStatus ? (
        <Tooltip title={userLoginState.userName}>
          <Button onClick={handleOpenUserMenu} variant="contained" sx={profileAppbarCss.btnColor}>
            {userLoginState.userName}
          </Button>
        </Tooltip>
      ) : (
        <Tooltip title="로그인하기">
          <Button onClick={handleOpenUserMenu} variant="contained" sx={profileAppbarCss.btnColor}>
            로그인
          </Button>
        </Tooltip>
      )}

      <Menu
        sx={{ mt: '45px' }}
        id="menu-appbar"
        anchorEl={anchorElUser}
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}
        keepMounted
        transformOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}
        open={Boolean(anchorElUser)}
        onClose={handleCloseUserMenu}>
        {userLoginState.loginStatus
          ? userLoginState.userName === '관리자'
            ? HEADER_LOGIN_ADMIN_MENU.CONTENTS.map((userMenu) => (
                <MenuItem
                  key={userMenu.pageName}
                  onClick={() => handleCloseMiniMenu(userMenu.pageUrl)}>
                  <Typography textAlign="center">{userMenu.pageName}</Typography>
                </MenuItem>
              ))
            : HEADER_LOGIN_USER_MENU.CONTENTS.map((userMenu) => (
                <MenuItem
                  key={userMenu.pageName}
                  onClick={() => handleCloseMiniMenu(userMenu.pageUrl)}>
                  <Typography textAlign="center">{userMenu.pageName}</Typography>
                </MenuItem>
              ))
          : HEADER_UNLOGIN_USER_MENU.CONTENTS.map((userMenu) => (
              <MenuItem
                key={userMenu.pageName}
                onClick={() => handleCloseMiniMenu(userMenu.pageUrl)}>
                <Typography textAlign="center">{userMenu.pageName}</Typography>
              </MenuItem>
            ))}
      </Menu>
    </>
  );
}
