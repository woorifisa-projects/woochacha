import * as React from 'react';
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
import { HEADER_MENU } from '@/constants/String';
import MenuAppbar from '@/components/common/MenuAppbar';
import ProfileAppbar from '@/components/common/ProfileAppbar';

import { useRecoilValue } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';

function DefaultHeader() {
  const userLoggedIn = useRecoilValue(userLoggedInState);
  const router = useRouter();

  const [anchorElNav, setAnchorElNav] = React.useState(null);

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };

  const handleCloseNavMenu = (url) => {
    router.push(url);
    setAnchorElNav(null);
  };

  return (
    <AppBar color="default" position="sticky">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="/"
            sx={{
              mr: 2,
              display: { xs: 'none', md: 'flex' },
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none',
            }}>
            {HEADER_MENU.LOGO}
          </Typography>

          <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
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
              sx={{
                display: { xs: 'block', md: 'none' },
              }}>
              {HEADER_MENU.CONTENTS.map((page) => (
                <MenuItem key={page.pageName} onClick={() => handleCloseNavMenu(page.pageUrl)}>
                  <Typography textAlign="center">{page.pageName}</Typography>
                </MenuItem>
              ))}

              {/* mini-menu app bar compo */}
              {userLoggedIn ? <MenuAppbar size="0" /> : ''}
            </Menu>
          </Box>
          <Typography
            variant="h5"
            noWrap
            component="a"
            href="/"
            sx={{
              mr: 2,
              display: { xs: 'flex', md: 'none' },
              flexGrow: 1,
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none',
            }}>
            {HEADER_MENU.LOGO}
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
            {HEADER_MENU.CONTENTS.map((page) => (
              <MenuItem
                key={page.pageName}
                onClick={() => handleCloseNavMenu(page.pageUrl)}
                sx={{ my: 2, color: 'black', display: 'block' }}>
                {page.pageName}
              </MenuItem>
            ))}
            {/* mini-menu app bar compo */}
            {userLoggedIn ? <MenuAppbar size="2" /> : ''}
          </Box>

          <Box sx={{ flexGrow: 0 }}>
            {/* profile app bar compo */}
            <ProfileAppbar />
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  );
}
export default DefaultHeader;
