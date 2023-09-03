import { useState } from 'react';
import { useRouter } from 'next/router';
import { useRecoilState } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import { Button, Menu, MenuItem, Tooltip, Typography } from '@mui/material';
import { HEADER_LOGIN_USER_MENU, HEADER_UNLOGIN_USER_MENU } from '@/constants/string';
import LocalStorage from '@/utils/localStorage';

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
      LocalStorage.removeItem('loginToken');
      setUserLoginState({
        loginStatus: false,
        userId: null,
        userName: null,
      });

      // TODO: 로그아웃 modal 처리
      alert('로그아웃 완료!');

      router.push('/');
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
            로그아웃
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
          ? HEADER_LOGIN_USER_MENU.CONTENTS.map((userMenu) => (
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
