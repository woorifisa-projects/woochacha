import * as React from 'react';
import { CssBaseline, ThemeProvider, Box, Grid } from '@mui/material';
import theme from '@/styles/theme';
import CopyRight from '@/components/CopyRight';

export default function DefaultFooter() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Box
        component="footer"
        sx={{
          py: 6,
          px: 2,
          mt: 'auto',
          backgroundColor: '#aaa',
          mx: 'auto',
        }}>
        <Grid container spacing={4} columns={16} mb={5}>
          <Grid item xs={4}>
            <Grid display="flex" justifyContent="center" alignItems="center">
              구매
            </Grid>
          </Grid>
          <Grid item xs={4}>
            <Grid display="flex" justifyContent="center" alignItems="center">
              판매
            </Grid>
          </Grid>
          <Grid item xs={4}>
            <Grid display="flex" justifyContent="center" alignItems="center">
              대출
            </Grid>
          </Grid>
          <Grid item xs={4}>
            <Grid display="flex" justifyContent="center" alignItems="center">
              마이페이지
            </Grid>
          </Grid>
        </Grid>
        <Grid display="flex" justifyContent="center" alignItems="center">
          <CopyRight />
        </Grid>
      </Box>
    </ThemeProvider>
  );
}
