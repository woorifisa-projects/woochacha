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
  const [pageSize, setPageSize] = useState(10);

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

  /**
   * 검색어를 받아와서 API 호출 후 결과를 상태로 설정하는 함수
   * */
  const handleSearch = (keyword) => {
    keywordProductGetApi(keyword).then((res) => {
      if (res.status === 200) {
        setAllProducts((prevProducts) => {
          // 기존의 상태를 복사 -> content를 업데이트
          return {
            ...prevProducts,
            productInfo: {
              ...prevProducts.productInfo,
              content: [...res.data],
            },
          };
        });
      }
    });
  };

  /**
   * 필터링 관련 함수
   */
  const handleFiltering = () => {
    filteringProductGetApi(selectMenuValue).then((res) => {
      if (res.status === 200) {
        setAllProducts((prevProducts) => {
          // 기존의 상태를 복사 -> content를 업데이트
          return {
            ...prevProducts,
            productInfo: {
              ...prevProducts.productInfo,
              content: [...res.data],
            },
          };
        });
      }
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
      justifyContent: 'center',
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

                  <Button
                    sx={productsCss.filteringBtn}
                    variant="contained"
                    onClick={handleFiltering}>
                    필터링 검색
                  </Button>
                </Card>
              </Grid>

              {/* 차량게시글 관련 content */}
              <Grid
                item
                lg={8}
                md={8}
                xs={12}
                // mx={8}
                sx={productsCss.contentContainer}
                maxWidth="lg">
                <Grid container spacing={4}>
                  <ProductCard productItems={allProducts.productInfo} />
                </Grid>
              </Grid>
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
    )
  );
}

export async function getServerSideProps() {
  const data = await allProductGetApi(0, 10).then((res) => res.data);

  return {
    props: {
      fetchData: data,
    },
  };
}
