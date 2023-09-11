import AdminpageSidebar from '@/components/admin/AdminpageSidebar';
import theme from '@/styles/theme';
import {
  Container,
  CssBaseline,
  Grid,
  ThemeProvider,
  responsiveFontSizes,
  styled,
} from '@mui/material';
import { useEffect, useState } from 'react';

export default function AdminPageLayout(props) {
  const { children } = props;
  const [mounted, setMounted] = useState(false);
  let responsiveFontTheme = responsiveFontSizes(theme);

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  const LayoutRoot = styled('div')(() => ({
    display: 'flex',
    flex: '1 1 auto',
    maxWidth: '100%',
    minWidth: '300px',
    paddingLeft: '0px',
  }));

  const LayoutContainer = styled('div')({
    display: 'flex',
    flex: '1 1 auto',
    flexDirection: 'column',
    width: '100%',
    minWidth: '300px',
  });

  const productsCss = {
    gridContent: {
      height: '100%',
      display: 'flex',
      flexDirection: 'row',
    },
  };

  return (
    mounted && (
      <>
        <ThemeProvider theme={responsiveFontTheme}>
          <CssBaseline />
          {/* main page */}
          <main>
            {/* 관리자페이지 전체적인 layouts */}
            <Grid mx="auto" spacing={2} container sx={productsCss.gridContent} maxWidth="xl" my={7}>
              {/* 관리자페이지 side menu */}
              <Grid item md={2} xs={12}>
                <AdminpageSidebar />
              </Grid>

              {/* 관리자페이지 세부 항목 관련 content */}
              <Grid item md={10} xs={12}>
                <LayoutRoot>
                  <LayoutContainer>
                    <Container sx={productsCss.contentContainer} maxWidth="md">
                      {children}
                    </Container>
                  </LayoutContainer>
                </LayoutRoot>
              </Grid>
            </Grid>
          </main>
        </ThemeProvider>
      </>
    )
  );
}
