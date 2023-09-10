import {
  Button,
  Card,
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
import { useEffect, useState } from 'react';
import {
  allProductGetApi,
  filteringProductGetApi,
  keywordProductGetApi,
} from '@/services/productApi';
import MultipleSelect from '@/components/product/MultipleSelect';
import SearchIcon from '@mui/icons-material/Search';

export default function Products() {
  const [mounted, setMounted] = useState(false);
  const [allProducts, setAllProducts] = useState();
  const [itemsPerPage, setItemsPerPage] = useState(10);
  const [selectMenus, setSelectMenus] = useState();
  const [selectMenuValue, setSelectMenuValue] = useState({});
  let responsiveFontTheme = responsiveFontSizes(theme);

  /**
   * selectmenu의 키와 라벨을 매핑
   */
  const selectBoxLabelMap = {
    typeList: '차종',
    modelList: '모델',
    carNameList: '차 이름',
    fuelList: '연료',
    colorList: '색상',
    transmissionList: '변속기',
    branchList: '차고지',
  };

  /**
   * 첫 렌더링시, 전체 product get
   */
  useEffect(() => {
    allProductGetApi().then((data) => {
      setAllProducts(data);
    });
    setMounted(true);
  }, []);

  /**
   * allProducts가 업데이트될 때만 selectBoxes를 생성하도록 이동
   */
  useEffect(() => {
    if (allProducts) {
      const selectBoxes = Object.keys(allProducts.productFilterInfo).map((key) => ({
        id: key,
        label: selectBoxLabelMap[key] || key,
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
        ...allProducts,
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

  const productsCss = {
    gridContent: {
      height: '100%',
      display: 'flex',
      flexDirection: 'row',
    },
    contentContainer: {
      py: 3,
    },
    filteringBox: {
      maxWidth: '100%',
    },
    filteringBtn: {
      float: 'right',
      marginTop: 3,
    },
    card: {
      padding: 2,
      width: '100%',
    },
    cardTypo: {
      display: 'flex',
      fontWeight: 'bold',
      my: 3,
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
          <Grid mx="auto" container maxWidth="xl">
            <MiniCard colorVal="#def2ff" shadowVal={3} marginVal={10}>
              <Typography gutterBottom variant="h5" component="h5" mb={3}>
                궁금한 차량 조회하기
              </Typography>
              <SearchBar onSearch={handleSearch} />
            </MiniCard>
          </Grid>

          {/* 메인 페이지 content */}
          <Grid mx="auto" container sx={productsCss.gridContent} maxWidth="xl">
            {/* 구매 페이지 filltering side bar */}
            <Grid item mx={4} xs={2} spacing={1} alignItems="center" justifyContent="center" mb={8}>
              <Card sx={productsCss.card}>
                <Typography variant="body1" sx={productsCss.cardTypo}>
                  우차차에서 내 차 찾기
                  <SearchIcon color="primary" fontSize="large" />
                </Typography>
                {selectMenus &&
                  selectMenus.map((item, idx) => {
                    return (
                      <Grid key={idx} item sx={productsCss.filteringBox}>
                        <MultipleSelect
                          sx={productsCss.filteringBox}
                          key={idx}
                          selectMenu={item}
                          selectItems={allProducts.productFilterInfo[`${item.id}`]}
                          onChangeSelect={handleChangeSelect}
                        />
                      </Grid>
                    );
                  })}

                <Button sx={productsCss.filteringBtn} variant="contained" onClick={handleFiltering}>
                  필터링 검색
                </Button>
              </Card>
            </Grid>

            {/* 차량게시글 관련 content */}
            <Grid item lg={8} mx={8} sx={productsCss.contentContainer} maxWidth="lg">
              <Grid container spacing={4}>
                <ProductCard productItems={allProducts.productInfo} />
              </Grid>
            </Grid>
          </Grid>

          {/* pagination */}
          <Grid sx={productsCss.pagination}>
            <Pagination count={Math.ceil(allProducts.productInfo.length / itemsPerPage) || 1} />
          </Grid>
        </main>
      </ThemeProvider>
    )
  );
}
