import { Fade, Menu, MenuItem } from '@mui/material';
import React from 'react';
import { useRouter } from 'next/router';
import { useRecoilValue } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import { debounce } from 'lodash';

export default function MenuAppbar(props) {
  const userLoginState = useRecoilValue(userLoggedInState);
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
  const handleMiniMenu = debounce((url) => {
    router.push(url);
    setAnchorEl(null);
  }, 300);

  const HEADER_MINI_MENU_LOGIN = {
    CONTENTS: [
      {
        pageName: '내 프로필',
        pageUrl: `/mypage/${userLoginState.userId}`,
      },
      {
        pageName: '구매정보',
        pageUrl: `/mypage/purchase/${userLoginState.userId}`,
      },
      {
        pageName: '판매정보',
        pageUrl: `/mypage/sale/${userLoginState.userId}`,
      },
      {
        pageName: '등록한 매물정보',
        pageUrl: `/mypage/registered/${userLoginState.userId}`,
      },
    ],
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
        {HEADER_MINI_MENU_LOGIN.CONTENTS.map((page, idx) => (
          <MenuItem key={idx} onClick={() => handleMiniMenu(page.pageUrl)}>
            {page.pageName}
          </MenuItem>
        ))}
      </Menu>
    </>
  );
}
