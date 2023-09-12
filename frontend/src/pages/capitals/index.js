import {
  responsiveFontSizes,
  ThemeProvider,
  CssBaseline,
  Box,
  Typography,
  Button,
  Grid,
} from '@mui/material';
import theme from '@/styles/theme';
import MainCard from '@/components/common/MainCard';
import MiniCard from '@/components/common/MiniCard';
import { CAPITAL_MAIN_CARD, CAPITAL_CONTENTS } from '@/constants/string';

export default function Capitals() {
  let responsiveFontTheme = responsiveFontSizes(theme);

  const capitalCss = {
    mainCardTypo: {
      mb: 10,
      color: '#1490ef',
      fontWeight: 'bold',
    },
    mainCardBox: {
      display: 'flex',
      flexDirection: 'row',
      justifyContent: 'space-between',
      alignItems: 'center',
    },
    imageBox: {
      width: '100%',
    },
    gridBox: {
      width: '100%',
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
    },
  };

  return (
    <ThemeProvider theme={responsiveFontTheme}>
      <CssBaseline />
      {/* main page */}
      <main>
        {/* 페이지 상단 box */}
        {CAPITAL_MAIN_CARD.map((item, idx) => {
          return (
            <MainCard
              titleVal={item.title}
              subTitleVal={item.content}
              titleColor={item.titleColor}
              key={idx}
            />
          );
        })}
        {/* 대출 관련 content */}
        {CAPITAL_CONTENTS.map((item) => {
          return (
            <>
              <MiniCard
                colorVal={item.miniCardColor}
                shadowVal={item.miniCardShadow}
                marginVal={item.miniCardMarginY}>
                <Typography
                  mb={10}
                  component="h5"
                  variant="h5"
                  sx={capitalCss.mainCardTypo}
                  gutterBottom>
                  {item.capitalTitle}
                </Typography>
                <Box sx={capitalCss.mainCardBox}>
                  <Grid container maxWidth="xl" mx="auto">
                    <Grid item md={6} xs={12}>
                      <img src={item.capitalImgUrl} width="100%" />
                    </Grid>
                    <Grid item md={6} xs={12}>
                      <Box sx={capitalCss.gridBox}>
                        <Box>
                          <Typography
                            mb={4}
                            component="h4"
                            variant="h4"
                            color="inherit"
                            fontWeight="bold"
                            gutterBottom>
                            {item.capitalSubTitle}
                          </Typography>
                          <Typography sx={{ mb: '5rem' }}>{item.capitalSubContent}</Typography>
                          <Button
                            href={item.wonCarUrl}
                            variant="contained"
                            fullWidth
                            sx={{ fontSize: '1.3rem' }}>
                            보러가기
                          </Button>
                        </Box>
                      </Box>
                    </Grid>
                  </Grid>
                </Box>
              </MiniCard>
            </>
          );
        })}
      </main>
    </ThemeProvider>
  );
}
