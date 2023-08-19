import { Fade, Menu, MenuItem } from '@mui/material';
import React from 'react';
import { useRouter } from 'next/router';
import { HEADER_MINI_MENU } from '@/constants/string';

export default function MenuAppbar(props) {
  const router = useRouter();

  const [anchorEl, setAnchorEl] = React.useState(null);

  const open = Boolean(anchorEl);
  const size = Number(props.size);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };
  const handleMiniMenu = (url) => {
    router.push(url);
    setAnchorEl(null);
  };

  return (
    <>
      {/* menu 내부 mini-menu */}
      <MenuItem
        id="fade-button"
        aria-controls={open ? 'fade-menu' : undefined}
        aria-haspopup="true"
        aria-expanded={open ? 'true' : undefined}
        sx={{ my: size, color: 'black', display: 'block' }}
        onClick={handleClick}>
        마이페이지
      </MenuItem>
      <Menu
        id="fade-menu"
        MenuListProps={{
          'aria-labelledby': 'fade-button',
        }}
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        TransitionComponent={Fade}>
        {HEADER_MINI_MENU.CONTENTS.map((page, idx) => (
          <MenuItem key={idx} onClick={() => handleMiniMenu(page.pageUrl)}>
            {page.pageName}
          </MenuItem>
        ))}
      </Menu>
    </>
  );
}
