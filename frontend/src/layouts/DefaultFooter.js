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
import { HEADER_UNLOGIN_USER_MENU } from '@/constants/string';
import { useEffect, useState } from 'react';

export default function DefaultFooter() {
  const [mounted, setMouted] = useState(false);
  const router = useRouter();
  const userLoginState = useRecoilValue(userLoggedInState);

  const handleMovePage = (url) => {
    router.push(url);
  };

  useEffect(() => {
    setMouted(true);
  }, []);

  const FOOTER_MINI_MENU_LOGIN = {
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

  const defaultFooter = {
    footerBox: {
      pt: 4,
      pb: 2,
      px: 2,
      mt: 'auto',
      backgroundColor: '#FFF',
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
    mounted && (
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <Box component="footer" sx={defaultFooter.footerBox}>
          <Grid sx={defaultFooter.gridItems}>
            <CopyRight />
          </Grid>
        </Box>
      </ThemeProvider>
    )
  );
}
