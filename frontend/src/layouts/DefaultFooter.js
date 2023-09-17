import { CssBaseline, ThemeProvider, Box, Grid } from '@mui/material';
import theme from '@/styles/theme';
import CopyRight from '@/components/common/CopyRight';
import { useEffect, useState } from 'react';

export default function DefaultFooter() {
  const [mounted, setMouted] = useState(false);

  useEffect(() => {
    setMouted(true);
  }, []);

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
      borderTop: '1px solid rgb(235, 235, 235)',
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
