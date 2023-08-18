import React from 'react';
import { useRouter } from 'next/router';
import { Avatar, IconButton, Menu, MenuItem, Tooltip, Typography } from '@mui/material';
import { HEADER_USER_MENU } from '@/constants/String';

export default function ProfileAppbar() {
  const router = useRouter();
  const [anchorElUser, setAnchorElUser] = React.useState(null);

  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseUserMenu = (url) => {
    router.push(url);
    setAnchorElUser(null);
  };

  return (
    <>
      <Tooltip title="Open settings">
        <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
          <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg" />
        </IconButton>
      </Tooltip>
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
        {HEADER_USER_MENU.CONTENTS.map((userMenu) => (
          <MenuItem key={userMenu.pageName} onClick={() => handleCloseUserMenu(userMenu.pageUrl)}>
            <Typography textAlign="center">{userMenu.pageName}</Typography>
          </MenuItem>
        ))}
      </Menu>
    </>
  );
}
