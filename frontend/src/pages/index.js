import { useRouter } from 'next/router';
import MainProduct from '@/components/product/MainProduct';
import {
  responsiveFontSizes,
  ThemeProvider,
  Button,
  Card,
  CardActions,
  CardContent,
  CardMedia,
  CssBaseline,
  Grid,
  Stack,
  Box,
  Typography,
  Container,
} from '@mui/material';
import theme from '@/styles/theme';
import SearchBar from '@/components/product/SearchBar';
import { useEffect, useState } from 'react';
import SelectBox from '@/components/common/SelectBox';
import { allProductGetApi } from '@/services/productApi';
import axios from 'axios';

// Dummy Data
const cards = [1, 2, 3, 4, 5, 6, 7, 8, 9];
const mainFeaturedPost = {
  title: '내가 WON하는 금융상품 알아보기 ',
  description: '우리WON카에서 자동차 금융상품을 조회할 수 있어요!',
  image: 'https://www.wooriwoncar.com/webassets/img/pc/main-buy-img-bg.png',
  imageText: 'main image description',
  linkText: '대출상품 보러가기',
};

export default function Home() {
  const router = useRouter();
  const [allProducts, setAllProducts] = useState('');
  let responsiveFontTheme = responsiveFontSizes(theme);

  // TODO: API 요청
  useEffect(() => {
    axios.defaults.headers['Access-Control-Allow-Origin'] = '*';
    // 서로 다른 도메인간 쿠키 전달 허용
    axios.defaults.withCredentials = true;

    axios
      .get('http://13.125.32.208:8080/product')
      .then((Response) => {
        console.log(Response.data);
      })
      .catch((Error) => {
        console.log(Error);
      });

    // const resData = allProductGetApi();
    // console.log(resData);
    // setAllProducts(resData.productInfo);
  }, []);

  // TODO: select menu에 맞게 fix 예정
  const [selectMenuValue, setSelectMenuValue] = useState('');

  const selectBoxes = [
    {
      id: 'brand',
      label: '차종',
    },
    {
      id: 'best-model',
      label: '대표모델',
    },
    {
      id: 'sub-model',
      label: '세부모델',
    },
  ];

  const selectMenuItems = [
    {
      value: 1,
      content: '현대',
    },
    {
      value: 2,
      content: '기아',
    },
    {
      value: 3,
      content: '쉐보레',
    },
  ];

  const handleMoveBtn = (url) => {
    router.push(url);
  };

  const handleChangeSelect = (e) => {
    setSelectMenuValue(e.target.value);
  };

  return (
    <ThemeProvider theme={responsiveFontTheme}>
      <CssBaseline />
      {/* main page */}
      <main>
        <Box
          sx={{
            bgcolor: 'background.paper',
            pt: 8,
            pb: 6,
          }}>
          <Container maxWidth="sm">
            <Typography
              component="h1"
              variant="h2"
              align="center"
              color="text.primary"
              gutterBottom>
              우차차 메인페이지!
            </Typography>
            <Typography variant="h5" align="center" color="text.secondary" paragraph>
              메인 페이지입니당!
              <br />
              우차차 설명 및 광고
            </Typography>
            <Stack sx={{ pt: 4 }} direction="row" spacing={2} justifyContent="center">
              <Button onClick={() => handleMoveBtn('/products')} variant="contained">
                차량 보러가기
              </Button>
              <Button onClick={() => handleMoveBtn('/products/sale')} variant="outlined">
                내 차 판매하기
              </Button>
            </Stack>
          </Container>
        </Box>

        <Box
          sx={{
            maxWidth: 1000,
            margin: 'auto',
            backgroundColor: '#DEF2FF',
            boxShadow: 3,
            borderRadius: 7,
            opacity: 0.7,
            py: 8,
            px: 8,
            my: 10,
            height: '100%',
            width: '80%',
            display: 'flex',
            flexDirection: 'column',
          }}
          alignItems="center">
          <Grid container spacing={2} alignItems="center" mb={8}>
            {selectBoxes.map((item, idx) => {
              return (
                <Grid key={idx} item xs={4}>
                  <SelectBox
                    selectMenu={item}
                    selectMenuValue={selectMenuValue}
                    onChangeSelect={handleChangeSelect}
                    selectMenuItems={selectMenuItems}
                  />
                </Grid>
              );
            })}
          </Grid>
          <SearchBar />
        </Box>

        <MainProduct post={mainFeaturedPost} />

        <Container sx={{ py: 10 }} maxWidth="md">
          <Typography
            sx={{ mb: 4 }}
            component="h4"
            variant="h4"
            color="inherit"
            fontWeight="bold"
            gutterBottom>
            추천 차량 보기
          </Typography>
          {/* TODO: 차량 item card 컴포넌트로 분리 예정 */}
          {/* 차량 item */}
          <Grid container spacing={4}>
            {cards.map((card) => (
              <Grid item key={card} xs={12} sm={6} md={4}>
                <Card sx={{ height: '100%', display: 'flex', flexDirection: 'column' }}>
                  <CardMedia
                    component="div"
                    sx={{
                      // 16:9
                      pt: '56.25%',
                    }}
                    image="https://source.unsplash.com/random?car"
                  />
                  <CardContent sx={{ flexGrow: 1 }}>
                    <Typography gutterBottom variant="h5" component="h2">
                      차량명
                    </Typography>
                    <Typography>
                      상세설명입니다.
                      <br />
                      상세설명입니다.
                      <br />
                      상세설명입니다.
                    </Typography>
                  </CardContent>
                  <CardActions>
                    <Button size="small">보러가기</Button>
                  </CardActions>
                </Card>
              </Grid>
            ))}
          </Grid>
        </Container>
      </main>
    </ThemeProvider>
  );
}
