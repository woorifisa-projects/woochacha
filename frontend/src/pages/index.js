import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import {
  responsiveFontSizes,
  ThemeProvider,
  Button,
  CssBaseline,
  Grid,
  Stack,
  Box,
  Typography,
  Container,
} from '@mui/material';
import theme from '@/styles/theme';
import SearchBar from '@/components/product/SearchBar';
import MultipleSelect from '@/components/product/MultipleSelect';
import ProductCard from '@/components/product/ProductCard';
import MainProduct from '@/components/product/MainProduct';
import {
  allProductGetApi,
  filteringProductGetApi,
  keywordProductGetApi,
} from '@/services/productApi';

// Dummy Data
const mainFeaturedPost = {
  title: '내가 WON하는 금융상품 알아보기 ',
  description: '우리WON카에서 자동차 금융상품을 조회할 수 있어요!',
  image: 'https://www.wooriwoncar.com/webassets/img/pc/main-buy-img-bg.png',
  imageText: 'main image description',
  linkText: '대출상품 보러가기',
};

export default function Home(props) {
  const [mounted, setMounted] = useState(false);
  const [allProducts, setAllProducts] = useState();
  const [selectMenuValue, setSelectMenuValue] = useState({
    typeList: [],
    modelList: [],
    transmissionList: [],
  });
  const [selectMenus, setSelectMenus] = useState();
  const router = useRouter();
  let responsiveFontTheme = responsiveFontSizes(theme);

  // TODO: SSG RENDERING
  // const { fetchData } = props;

  /**
   * mount시, 모든 product api 요청
   */
  useEffect(() => {
    allProductGetApi().then((data) => {
      setAllProducts(data);
    });
    setMounted(true);
  }, []);

  /**
   * selectmenu의 키와 라벨을 매핑
   */
  const selectBoxLabelMap = {
    typeList: '차종',
    modelList: '모델',
    transmissionList: '변속기',
  };

  // selectBoxLabelMap 객체의 키 목록 가져오기
  const mainSelectMenus = Object.keys(selectBoxLabelMap);

  /**
   * allProducts가 업데이트될 때만 selectBoxes를 생성하도록 이동
   */
  useEffect(() => {
    if (allProducts) {
      // 여러 옵션 중, 메인페이지에 나올 selectMenus만 필터링
      const selectBoxes = mainSelectMenus
        .filter((key) => allProducts.productFilterInfo[key])
        .map((key) => ({
          id: key,
          label: selectBoxLabelMap[key],
        }));
      setSelectMenus(selectBoxes);
    }
  }, [allProducts]);

  /**
   * 검색어를 받아와서 API 호출 후 결과를 상태로 설정하는 함수
   * */
  const handleSearch = (keyword) => {
    keywordProductGetApi(keyword).then((data) => {
      setAllProducts({
        productInfo: data,
      });
    });
  };

  /**
   * 필터링 관련 함수
   */
  const handleFiltering = () => {
    filteringProductGetApi(selectMenuValue).then((data) => {
      setAllProducts({
        ...allProducts,
        productInfo: data,
      });
    });
  };

  /**
   * selectbox 선택 시, 선택한 selectItem 저장 함수
   */
  const handleChangeSelect = (e, selectId) => {
    const valueArr = e.target.value;
    const updatedValue = {
      ...selectMenuValue,
      [selectId]: valueArr.map((item) => {
        return {
          id: item,
        };
      }),
    };
    setSelectMenuValue(updatedValue);
  };

  /**
   * 상세 페이지 이동 함수
   */
  const handleMoveBtn = (url) => {
    router.push(url);
  };

  const MainPageCss = {
    mainBox: {
      bgcolor: 'background.paper',
      pt: 8,
      pb: 6,
    },
    mainBoxStack: { pt: 4 },
    filteringBox: {
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
    },
    filteringBtn: { mt: 2 },
    carContainer: { py: 10 },
    carSubTitle: { mb: 4, fontWeight: 'bold' },
  };

  return (
    allProducts &&
    mounted && (
      <ThemeProvider theme={responsiveFontTheme}>
        <CssBaseline />
        {/* main page */}
        <main>
          <Box sx={MainPageCss.mainBox}>
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
              <Stack
                sx={MainPageCss.mainBoxStack}
                direction="row"
                spacing={2}
                justifyContent="center">
                <Button onClick={() => handleMoveBtn('/products')} variant="contained">
                  차량 보러가기
                </Button>
                <Button onClick={() => handleMoveBtn('/products/sale')} variant="outlined">
                  내 차 판매하기
                </Button>
              </Stack>
            </Container>
          </Box>

          <Box sx={MainPageCss.filteringBox} alignItems="center">
            {/* filter item */}
            <Grid container spacing={2} alignItems="center" justifyContent="center" mb={8}>
              {selectMenus &&
                selectMenus.map((item, idx) => {
                  return (
                    <Grid key={idx} item xs={4}>
                      <MultipleSelect
                        key={idx}
                        selectMenu={item}
                        selectItems={allProducts.productFilterInfo[`${item.id}`]}
                        onChangeSelect={handleChangeSelect}
                      />
                    </Grid>
                  );
                })}

              <Button variant="contained" onClick={handleFiltering} sx={MainPageCss.filteringBtn}>
                필터링 검색
              </Button>
            </Grid>
            <SearchBar onSearch={handleSearch} />
          </Box>

          <MainProduct post={mainFeaturedPost} />

          <Container sx={MainPageCss.carContainer} maxWidth="md">
            <Typography
              sx={MainPageCss.carSubTitle}
              component="h4"
              variant="h4"
              color="inherit"
              gutterBottom>
              추천 차량 보기
            </Typography>
            {/* 차량 item */}
            <Grid container spacing={4}>
              <ProductCard productItems={allProducts.productInfo} />
            </Grid>
          </Container>
        </main>
      </ThemeProvider>
    )
  );
}

/*
export async function getStaticProps() {
  const response = await allProductGetApi();

  return {
    props: {
      fetchData: response,
    },
  };
}
*/
