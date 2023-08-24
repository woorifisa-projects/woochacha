import React from 'react';
import { useRouter } from 'next/router';
import { useRecoilState } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import { Avatar, IconButton, Menu, MenuItem, Tooltip, Typography } from '@mui/material';
import { HEADER_LOGIN_USER_MENU, HEADER_UNLOGIN_USER_MENU } from '@/constants/string';
import LocalStorage from '@/utils/localStorage';

export default function ProfileAppbar() {
  const [userLoggedIn, setUserLoggedIn] = useRecoilState(userLoggedInState);
  const router = useRouter();
  const [anchorElUser, setAnchorElUser] = React.useState(null);

  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  const handleCloseMiniMenu = (url) => {
    if (url.includes('logout')) {
      LocalStorage.removeItem('loginToken');
      setUserLoggedIn(false);

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
      {userLoggedIn ? (
        <Tooltip title="홍길동님">
          <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
            <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg" />
          </IconButton>
        </Tooltip>
      ) : (
        <Tooltip title="로그인하기">
          <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
            <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg" />
          </IconButton>
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
        {userLoggedIn
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
