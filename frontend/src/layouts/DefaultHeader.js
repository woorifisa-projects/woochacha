import {
  AppBar,
  Box,
  Toolbar,
  IconButton,
  Typography,
  Menu,
  Container,
  MenuItem,
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';

import { useRouter } from 'next/router';
import { HEADER_MENU } from '@/constants/string';
import MenuAppbar from '@/components/common/MenuAppbar';
import ProfileAppbar from '@/components/common/ProfileAppbar';

import { useRecoilValue } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';
import AdminMenuAppbar from '@/components/admin/AdminMenuAppbar';
import { useEffect, useState } from 'react';
import styles from './DefaultHeader.module.css';
import Image from 'next/image';

import LogoImage from '../../public/assets/images/logo.png';

function DefaultHeader() {
  const [mounted, setMounted] = useState(false);
  const userLoginState = useRecoilValue(userLoggedInState);
  const router = useRouter();

  const [anchorElNav, setAnchorElNav] = useState(null);

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };

  const handleCloseNavMenu = (url) => {
    router.push(url);
    setAnchorElNav(null);
  };

  useEffect(() => {
    setMounted(true);
  }, []);

  const defaultHeaderCss = {
    headerContainer: {
      py: 1.5,
    },
    xsHeaderLogo: {
      mr: 2,
      display: { xs: 'flex', md: 'none' },
      flexGrow: 1,
      fontWeight: 800,
      letterSpacing: '.3rem',
      color: 'inherit',
      textDecoration: 'none',
    },
    mdHeaderLogo: {
      mr: 2,
      display: { xs: 'none', md: 'flex' },
      fontWeight: 800,
      letterSpacing: '.3rem',
      color: 'inherit',
      textDecoration: 'none',
    },
    xsHeaderBox: { flexGrow: 1, display: { xs: 'flex', md: 'none' } },
    mdHeaderBox: {
      flexGrow: 1,
      display: { xs: 'none', md: 'flex', justifyContent: 'center' },
    },
    xsHeaderMenuItem: {
      display: { xs: 'block', md: 'none', fontSize: '3rem' },
    },
    mdHeaderMenuItem: {
      my: 2,
      mx: 2,
      color: 'black',
      display: 'block',
      fontWeight: 500,
      fontSize: '1.1rem',
    },
  };

  return (
    mounted && (
      <AppBar
        elevation={0}
        color="transparent"
        position="sticky"
        className={styles.headerContainer}
        maxWidth="xl">
        <Container maxWidth="xl" className={styles.headerContainer}>
          <Toolbar disableGutters>
            <Typography
              variant="h5"
              noWrap
              component="a"
              href="/"
              sx={defaultHeaderCss.mdHeaderLogo}>
              <Image src={LogoImage} width={180} height={60} alt="woochacha-logo" />
            </Typography>

            <Box sx={defaultHeaderCss.xsHeaderBox}>
              <IconButton
                size="large"
                aria-label="account of current user"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                onClick={handleOpenNavMenu}
                color="inherit">
                <MenuIcon />
              </IconButton>
              <Menu
                id="menu-appbar"
                anchorEl={anchorElNav}
                anchorOrigin={{
                  vertical: 'bottom',
                  horizontal: 'left',
                }}
                keepMounted
                transformOrigin={{
                  vertical: 'top',
                  horizontal: 'left',
                }}
                open={Boolean(anchorElNav)}
                onClose={handleCloseNavMenu}
                sx={defaultHeaderCss.xsHeaderMenuItem}>
                {HEADER_MENU.CONTENTS.map((page) => (
                  <MenuItem key={page.pageName} onClick={() => handleCloseNavMenu(page.pageUrl)}>
                    <Typography textAlign="center">{page.pageName}</Typography>
                  </MenuItem>
                ))}

                {/* mini-menu app bar compo */}
                {userLoginState.loginStatus ? (
                  userLoginState.userName === '관리자' ? (
                    <AdminMenuAppbar size="0" />
                  ) : (
                    <MenuAppbar size="0" />
                  )
                ) : (
                  ''
                )}
              </Menu>
            </Box>
            <Typography
              variant="h5"
              noWrap
              component="a"
              href="/"
              sx={defaultHeaderCss.xsHeaderLogo}>
              <Image src={LogoImage} width={200} height={80} alt="woochacha-logo" />
            </Typography>
            <Box sx={defaultHeaderCss.mdHeaderBox}>
              {HEADER_MENU.CONTENTS.map((page) => (
                <MenuItem
                  key={page.pageName}
                  onClick={() => handleCloseNavMenu(page.pageUrl)}
                  sx={defaultHeaderCss.mdHeaderMenuItem}>
                  {page.pageName}
                </MenuItem>
              ))}

              {/* mini-menu app bar compo */}
              {userLoginState.loginStatus ? (
                userLoginState.userName === '관리자' ? (
                  <AdminMenuAppbar size="2" />
                ) : (
                  <MenuAppbar size="2" />
                )
              ) : (
                ''
              )}
            </Box>

            <Box sx={{ flexGrow: 0 }}>
              {/* profile app bar compo */}
              <ProfileAppbar />
            </Box>
          </Toolbar>
        </Container>
      </AppBar>
    )
  );
}
export default DefaultHeader;
