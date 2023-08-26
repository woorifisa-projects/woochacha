import {
  responsiveFontSizes,
  ThemeProvider,
  CssBaseline,
  Box,
  Typography,
  Button,
} from '@mui/material';
import theme from '@/styles/theme';
import MainCard from '@/components/common/MainCard';
import MiniCard from '@/components/common/MiniCard';
import { CAPITAL_MAIN_CARD, CAPITAL_CONTENTS } from '@/constants/string';

export default function Capitals() {
  let responsiveFontTheme = responsiveFontSizes(theme);

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
                  component="h4"
                  variant="h4"
                  color="#1490ef"
                  fontWeight="bold"
                  gutterBottom>
                  {item.capitalTitle}
                </Typography>
                <Box
                  sx={{
                    display: 'flex',
                    flexDirection: 'row',
                    justifyContent: 'space-between',
                    alignItems: 'center',
                  }}>
                  <img width="40%" src={item.capitalImgUrl} />
                  <Box
                    sx={{
                      width: '40%',
                      display: 'flex',
                      flexDirection: 'column',
                      alignItems: 'flex-start',
                    }}>
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
                </Box>
              </MiniCard>
            </>
          );
        })}
      </main>
    </ThemeProvider>
  );
}
