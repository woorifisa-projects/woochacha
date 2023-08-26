import {
  Container,
  CssBaseline,
  Grid,
  Pagination,
  ThemeProvider,
  Typography,
  responsiveFontSizes,
} from '@mui/material';
import theme from '@/styles/theme';
import MiniCard from '@/components/common/MiniCard';
import SearchBar from '@/components/product/SearchBar';
import ProductCard from '@/components/product/ProductCard';
import FilterSideBar from '@/components/product/FilterSideBar';
import { useEffect, useState } from 'react';

export default function Products() {
  const [mounted, setMounted] = useState(false);
  let responsiveFontTheme = responsiveFontSizes(theme);

  // TODO: API 조회 후 수정할 dummy data입니다.
  const itemDummyArr = [
    {
      productImgUrl: 'https://source.unsplash.com/random?car',
      carName: '멋쟁이 자동차1',
      price: '300',
      createdDate: '2023-07',
      distance: '58,972',
      carWhere: '인천',
      productUrl: '/products/detail/1',
    },
    {
      productImgUrl: 'https://source.unsplash.com/random?bmw',
      carName: '멋쟁이 자동차2',
      price: '3000',
      createdDate: '2023-05',
      distance: '58,999',
      carWhere: '대전',
      productUrl: '/products/detail/2',
    },
    {
      productImgUrl: 'https://source.unsplash.com/random?benz',
      carName: '멋쟁이 자동차3',
      price: '5000',
      createdDate: '2023-02',
      distance: '38,972',
      carWhere: '부산',
      productUrl: '/products/detail/3',
    },
    {
      productImgUrl: 'https://source.unsplash.com/random?car',
      carName: '멋쟁이 자동차4',
      price: '300',
      createdDate: '2023-07',
      distance: '58,972',
      carWhere: '인천',
      productUrl: '/products/detail/4',
    },
    {
      productImgUrl: 'https://source.unsplash.com/random?bmw',
      carName: '멋쟁이 자동차5',
      price: '3000',
      createdDate: '2023-05',
      distance: '58,999',
      carWhere: '대전',
      productUrl: '/products/detail/5',
    },
    {
      productImgUrl: 'https://source.unsplash.com/random?benz',
      carName: '멋쟁이 자동차6',
      price: '5000',
      createdDate: '2023-02',
      distance: '38,972',
      carWhere: '부산',
      productUrl: '/products/detail/6',
    },
    {
      productImgUrl: 'https://source.unsplash.com/random?car',
      carName: '멋쟁이 자동차7',
      price: '300',
      createdDate: '2023-07',
      distance: '58,972',
      carWhere: '인천',
      productUrl: '/products/detail/7',
    },
    {
      productImgUrl: 'https://source.unsplash.com/random?bmw',
      carName: '멋쟁이 자동차8',
      price: '3000',
      createdDate: '2023-05',
      distance: '58,999',
      carWhere: '대전',
      productUrl: '/products/detail/8',
    },
    {
      productImgUrl: 'https://source.unsplash.com/random?benz',
      carName: '멋쟁이 자동차9',
      price: '5000',
      createdDate: '2023-02',
      distance: '38,972',
      carWhere: '부산',
      productUrl: '/products/detail/9',
    },
    {
      productImgUrl: 'https://source.unsplash.com/random?benz',
      carName: '멋쟁이 자동차10',
      price: '5000',
      createdDate: '2023-02',
      distance: '38,972',
      carWhere: '부산',
      productUrl: '/products/detail/10',
    },
  ];

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  const productsCss = {
    gridContent: {
      height: '100%',
      display: 'flex',
      flexDirection: 'row',
    },
    contentContainer: {
      py: 3,
    },
    pagination: { display: 'flex', justifyContent: 'center', my: 8 },
  };

  return (
    mounted && (
      <ThemeProvider theme={responsiveFontTheme}>
        <CssBaseline />
        {/* main page */}
        <main>
          {/* 페이지 상단 serch box */}
          <MiniCard colorVal="#def2ff" shadowVal={3} marginVal={10}>
            <Typography gutterBottom variant="h5" component="h5" mb={3}>
              궁금한 차량 조회하기
            </Typography>
            <SearchBar />
          </MiniCard>

          {/* 메인 페이지 content */}
          <Grid sx={productsCss.gridContent}>
            {/* 구매 페이지 filltering side bar */}
            <FilterSideBar />

            {/* 차량게시글 관련 content */}
            <Container sx={productsCss.contentContainer} maxWidth="md">
              <ProductCard cardItems={itemDummyArr} />
            </Container>
          </Grid>

          {/* pagination */}
          <Grid sx={productsCss.pagination}>
            <Pagination count={10} />
          </Grid>
        </main>
      </ThemeProvider>
    )
  );
}
