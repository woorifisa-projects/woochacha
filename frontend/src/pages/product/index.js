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
import { allProductGetApi } from '@/services/productApi';
import { useRouter } from 'next/router';

export default function Products() {
  const [mounted, setMounted] = useState(false);
  const [allProducts, setAllProducts] = useState();
  const [selectMenuValue, setSelectMenuValue] = useState({
    typeList: [],
    modelList: [],
    transmissionList: [],
  });
  const router = useRouter();
  let responsiveFontTheme = responsiveFontSizes(theme);

  useEffect(() => {
    allProductGetApi().then((data) => {
      setAllProducts(data);
    });
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
    allProducts &&
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
              <Grid container spacing={4}>
                <ProductCard productItems={allProducts.productInfo} />
              </Grid>
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
