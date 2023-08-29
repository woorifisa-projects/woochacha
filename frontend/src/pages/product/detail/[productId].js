import { useEffect, useState } from 'react';
import { CssBaseline, ThemeProvider, responsiveFontSizes } from '@mui/material';
import theme from '@/styles/theme';
import MiniCard from '@/components/common/MiniCard';
import { useRouter } from 'next/router';

export default function ProductDetail() {
  const router = useRouter();
  const { productId } = router.query;
  let responsiveFontTheme = responsiveFontSizes(theme);
  const [mounted, setMounted] = useState(false);

  // TODO: API 호출 후, dummy data 수정 예정
  const dummyDetailData = {
    productBasicInfo: {
      title: '기아 올 뉴 카니발 2018년형',
      carNum: '22나2222',
      branch: '서울',
      price: 2690,
    },
    productDetailInfo: {
      capacity: 9,
      distance: 110000,
      carType: 'RV',
      fuelName: '디젤',
      transmissionName: '오토',
      produdctAccidentInfoList: [
        {
          type: '침수사고',
          count: 1,
        },
        {
          type: '교통사고',
          count: 2,
        },
      ],
      productExchangeInfoList: [
        {
          type: '본네트',
          count: 1,
        },
        {
          type: '뒷문',
          count: 1,
        },
      ],
    },
    productOptionInfo: [
      {
        option: '열선시트',
        whether: 1,
      },
      {
        option: '스마트키',
        whether: 0,
      },
      {
        option: '블랙박스',
        whether: 1,
      },
      {
        option: '네비게이션',
        whether: 0,
      },
      {
        option: '에어백',
        whether: 1,
      },
      {
        option: '썬루프',
        whether: 1,
      },
      {
        option: '하이패스',
        whether: 0,
      },
      {
        option: '후방카메라',
        whether: 1,
      },
    ],
    productOwnerInfo: null,
    carImageList: null,
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    mounted && (
      <ThemeProvider theme={responsiveFontTheme}>
        <CssBaseline />
        {/* main page */}
        <main>
          {/* 차량 디테일 페이지 */}
          <MiniCard colorVal="#def2ff" shadowVal={3} marginVal={10}>
            차량 {productId}의 디테일 페이지
          </MiniCard>
        </main>
      </ThemeProvider>
    )
  );
}
