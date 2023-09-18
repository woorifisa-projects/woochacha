import {
  Box,
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
import SearchBar from '@/components/product/SearchBar';
import ProductCard from '@/components/product/ProductCard';
import { useEffect, useState } from 'react';
import {
  allProductGetApi,
  filteringProductGetApi,
  keywordProductGetApi,
} from '@/services/productApi';
import MultipleSelect from '@/components/product/MultipleSelect';

import DirectionsCarIcon from '@mui/icons-material/DirectionsCar';
import styles from './product.module.css';
import { debounce } from 'lodash';
import LoadingBar from '@/components/common/LoadingBar';
import NoData from '@/components/common/NoData';
export default function Products(props) {
  const [mounted, setMounted] = useState(false);
  const [allProducts, setAllProducts] = useState();
  const [selectMenus, setSelectMenus] = useState();
  const [selectMenuValue, setSelectMenuValue] = useState({});
  let responsiveFontTheme = responsiveFontSizes(theme);
  const { fetchData } = props;
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
   * 페이지네이션
   */
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(8);
  const handleChange = (event, value) => {
    setPage(value - 1);
  };
  /**
   * - 첫 렌더링시, 전체 product get
   * - page 변경 시, 재렌더링
   */
  useEffect(() => {
    if (!mounted) {
      setAllProducts(fetchData);
      setMounted(true);
    } else {
      allProductGetApi(page, pageSize).then((res) => {
        if (res.status === 200) {
          setAllProducts(res.data);
        }
      });
    }
  }, [page]);
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

  const handleSearchDebounced = debounce((keyword) => {
    keywordProductGetApi(keyword).then((res) => {
      if (res.status === 200) {
        setAllProducts((prevProducts) => ({
          ...prevProducts,
          productInfo: {
            ...prevProducts.productInfo,
            content: [...res.data.content],
          },
        }));
      }
    });
  }, 500);

  /**
   * 필터링 관련 함수
   */
  const handleFiltering = debounce(() => {
    filteringProductGetApi(selectMenuValue).then((res) => {
      if (res.status === 200) {
        setAllProducts((prevProducts) => {
          // 기존의 상태를 복사 -> content를 업데이트
          return {
            ...prevProducts,
            productInfo: {
              ...prevProducts.productInfo,
              content: [...res.data.content],
            },
          };
        });
      }
    });
  }, 200);
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
    mainBox: {
      bgcolor: 'background.paper',
      pt: 8,
      pb: 6,
      my: 4,
    },
    gridContent: {
      height: '100%',
      display: 'flex',
      flexDirection: 'row',
      justifyContent: 'center',
    },
    contentContainer: {
      // py: 3,
    },
    filteringBox: {
      maxWidth: '100%',
    },
    filteringBtn: {
      float: 'right',
      marginTop: 3,
      fontWeight: 'bold',
    },
    card: {
      padding: 2,
      width: '100%',
      boxShadow: 2,
    },
    cardTypo: {
      display: 'flex',
      fontWeight: 'bold',
      my: 3,
    },
    pagination: { display: 'flex', justifyContent: 'center', my: 8 },
  };
  return allProducts && mounted ? (
    <ThemeProvider theme={responsiveFontTheme}>
      <CssBaseline />
      {/* main page */}
      <main>
        {/* 페이지 상단 serch box */}
        <Grid
          container
          mx="auto"
          maxWidth="xl"
          justifyContent="center"
          className={styles.searchGrid}>
          <Box className={styles.searchBox}>
            <h5 className={styles.searchTitle}>우차차가 검증한 차량 조회하기</h5>
            <SearchBar onSearch={handleSearchDebounced} />
          </Box>
          {/* <MiniCard colorVal="#FFF" shadowVal={3} marginVal={10}></MiniCard> */}
        </Grid>
        {/* 메인 페이지 content */}
        <Grid container maxWidth="xl" mx="auto" justifyContent="center">
          <Grid mx="auto" container sx={productsCss.gridContent} maxWidth="xl">
            {/* 구매 페이지 filltering side bar */}
            <Grid
              item
              mx={4}
              lg={2}
              md={2}
              xs={12}
              spacing={1}
              alignItems="center"
              justifyContent="center"
              mb={8}>
              <Card sx={productsCss.card}>
                <Typography variant="body1" sx={productsCss.cardTypo}>
                  조건 검색
                  <Box ml={1}>
                    <DirectionsCarIcon color="primary" />
                  </Box>
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
                  검색
                </Button>
              </Card>
            </Grid>
            {/* 차량게시글 관련 content */}
            {allProducts.productInfo.content.length > 0 ? (
              <Grid
                item
                lg={8}
                md={8}
                xs={12}
                // mx={8}
                sx={productsCss.contentContainer}
                maxWidth="lg">
                <Grid container spacing={2}>
                  <ProductCard productItems={allProducts.productInfo} />
                </Grid>
              </Grid>
            ) : (
              <Grid
                item
                lg={8}
                md={8}
                xs={12}
                // mx={8}
                sx={productsCss.contentContainer}
                maxWidth="lg">
                <NoData />
              </Grid>
            )}
          </Grid>
          {/* pagination */}
          <Grid item md={12} xs={12} sx={productsCss.pagination}>
            {allProducts.productInfo.totalPages === 0 ? (
              ''
            ) : (
              <Pagination
                count={allProducts.productInfo.totalPages}
                page={page + 1}
                onChange={handleChange}
              />
            )}
          </Grid>
        </Grid>
      </main>
    </ThemeProvider>
  ) : (
    <LoadingBar />
  );
}
export async function getServerSideProps() {
  const data = await allProductGetApi(0, 8).then((res) => res.data);
  return {
    props: {
      fetchData: data,
    },
  };
}
