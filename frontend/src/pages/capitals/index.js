import {
  responsiveFontSizes,
  ThemeProvider,
  CssBaseline,
  Box,
  Typography,
  Button,
  Grid,
  Stack,
} from '@mui/material';
import theme from '@/styles/theme';
import MainCard from '@/components/common/MainCard';
import MiniCard from '@/components/common/MiniCard';
import { CAPITAL_MAIN_CARD, CAPITAL_CONTENTS } from '@/constants/string';
import Image from 'next/image';
import styles from './capitals.module.css';
import BannerImage from '../../../public/assets/images/woochacha-capital01.svg';
import metabusImg from '../../../public/assets/images/woochacha-capital02.svg';

export default function Capitals() {
  let responsiveFontTheme = responsiveFontSizes(theme);

  const capitalCss = {
    mainCardTypo: {
      mb: 10,
      color: '#F95700',
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

        {/* banner */}
        <Stack alignItems="center" className={styles.bannerImageContainer}>
          <Image src={BannerImage} layout="fill" className={styles.bannerImage} />
        </Stack>

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
                  sx={capitalCss.mainCardTypo}
                  gutterBottom>
                  {item.capitalTitle}
                </Typography>
                <Box sx={capitalCss.mainCardBox}>
                  <Grid container maxWidth="xl" mx="auto" alignItems="center">
                    <Grid item md={6} xs={12}>
                      {item.capitalImgUrl === 'metabusImg' ? (
                        <Image src={metabusImg} width="100%" />
                      ) : (
                        <img src={item.capitalImgUrl} width="100%" />
                      )}
                    </Grid>
                    <Grid item md={6} xs={12}>
                      <Box sx={capitalCss.gridBox}>
                        <Box sx={capitalCss.gridBox}>
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
                            mx="auto"
                            size="large"
                            sx={{ fontSize: '1.5rem', fontWeight: 'bold', paddingX: '2rem' }}>
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
