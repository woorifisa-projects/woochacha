import { useEffect, useState } from 'react';
import { CssBaseline, ThemeProvider, responsiveFontSizes } from '@mui/material';
import theme from '@/styles/theme';
import MiniCard from '@/components/common/MiniCard';
import { useRouter } from 'next/router';
import { productDetailGetApi } from '@/services/productApi';
import DetailProduct from '@/components/product/DetailProduct';

export default function ProductDetail() {
  const router = useRouter();
  const { productId } = router.query;
  let responsiveFontTheme = responsiveFontSizes(theme);
  const [mounted, setMounted] = useState(false);
  const [detailProduct, setDetailProduct] = useState();

  useEffect(() => {
    productId &&
      productDetailGetApi(productId).then((data) => {
        setDetailProduct(data);
      });
    setMounted(true);
  }, []);

  return (
    detailProduct &&
    mounted && (
      <ThemeProvider theme={responsiveFontTheme}>
        <CssBaseline />
        {/* main page */}
        <main>
          {/* 차량 디테일 페이지 */}
          <MiniCard colorVal="#FFF" shadowVal={3} marginVal={10}>
            <DetailProduct detailItem={detailProduct} />
          </MiniCard>
        </main>
      </ThemeProvider>
    )
  );
}
