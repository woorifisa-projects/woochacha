import { useRouter } from 'next/router';
import { useRecoilValue } from 'recoil';
import { userLoggedInState } from '@/atoms/userInfoAtoms';

import {
  CssBaseline,
  ThemeProvider,
  Box,
  Grid,
  ListItem,
  ListItemText,
  Typography,
} from '@mui/material';
import theme from '@/styles/theme';
import CopyRight from '@/components/common/CopyRight';
import { HEADER_MINI_MENU, HEADER_UNLOGIN_USER_MENU } from '@/constants/string';

export default function DefaultFooter() {
  const router = useRouter();
  const userLoginState = useRecoilValue(userLoggedInState);

  const handleMovePage = (url) => {
    router.push(url);
  };

  const defaultFooter = {
    footerBox: {
      pt: 4,
      pb: 2,
      px: 2,
      mt: 'auto',
      backgroundColor: '#FFF',
      boxShadow: 3,
      mx: 'auto',
      color: theme.palette.primary.main,
      fontSize: '14px',
    },
    gridItems: {
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      flexDirection: 'column',
    },
    listItem: {
      textAlign: 'center',
      py: 1,
      color: '#000',
    },
    listItemText: {
      '&:hover': {
        cursor: 'pointer',
        transition: '0.3s',
        transform: 'scale(1.03)',
      },
    },
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Box component="footer" sx={defaultFooter.footerBox}>
        <Grid container spacing={4} columns={16} mb={5}>
          <Grid item xs={4}>
            <Grid sx={defaultFooter.gridItems}>
              <Typography
                sx={defaultFooter.listItemText}
                onClick={() => handleMovePage('/products')}>
                구매
              </Typography>
            </Grid>
          </Grid>
          <Grid item xs={4}>
            <Grid sx={defaultFooter.gridItems}>
              <Typography sx={defaultFooter.listItemText}>판매</Typography>
            </Grid>
          </Grid>
          <Grid item xs={4}>
            <Grid sx={defaultFooter.gridItems}>
              <Typography
                sx={defaultFooter.listItemText}
                onClick={() => handleMovePage('/capitals')}>
                대출
              </Typography>
            </Grid>
          </Grid>
          <Grid item xs={4}>
            <Grid sx={defaultFooter.gridItems}>
              <Typography>마이페이지</Typography>
              {userLoginState.loginStatus && userLoginState.userName !== '관리자'
                ? HEADER_MINI_MENU.CONTENTS.map((menuItem, idx) => {
                    return (
                      <ListItem key={idx} sx={defaultFooter.listItem}>
                        <ListItemText
                          sx={defaultFooter.listItemText}
                          primary={menuItem.pageName}
                          onClick={() => handleMovePage(menuItem.pageUrl)}
                        />
                      </ListItem>
                    );
                  })
                : HEADER_UNLOGIN_USER_MENU.CONTENTS.map((menuItem, idx) => {
                    return (
                      <ListItem key={idx} sx={defaultFooter.listItem}>
                        <ListItemText
                          sx={defaultFooter.listItemText}
                          primary={menuItem.pageName}
                          onClick={() => handleMovePage(menuItem.pageUrl)}
                        />
                      </ListItem>
                    );
                  })}
            </Grid>
          </Grid>
        </Grid>
        <Grid sx={defaultFooter.gridItems}>
          <CopyRight />
        </Grid>
      </Box>
    </ThemeProvider>
  );
}
