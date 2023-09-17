import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import {
  responsiveFontSizes,
  ThemeProvider,
  CssBaseline,
  Grid,
  Stack,
  Box,
  Typography,
  Container,
  Pagination,
} from '@mui/material';
import theme from '@/styles/theme';
import ProductCard from '@/components/product/ProductCard';
import MainProduct from '@/components/product/MainProduct';
import {
  allProductGetApi,
  filteringProductGetApi,
  keywordProductGetApi,
} from '@/services/productApi';
import styles from './main.module.css';
import Image from 'next/image';
import BannerImage from '../../public/assets/images/woochacha-banner01.svg';
import LoadingBar from '@/components/common/LoadingBar';

const mainFeaturedPost = {
  title: '우차차가 추천하는 금융상품 보러가기',
  description: '우리WON카의 자동차 금융상품을 조회할 수 있어요!',
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

  const { allPr } = props;

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
      setAllProducts(allPr);
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
    keywordProductGetApi(keyword).then((res) => {
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
              content: [...res.data.content],
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

  const MainPageCss = {
    mainBox: {
      bgcolor: 'background.paper',
      pt: 8,
      pb: 6,
      my: 4,
    },
    mainBoxStack: { pt: 4 },
    filteringBox: {
      margin: 'auto',
      borderTop: '1px solid gray',
      borderBottom: '1px solid gray',
      py: 8,
      px: 8,
      my: 10,
      height: '100%',
      width: '100%',
      display: 'flex',
      flexDirection: 'column',
    },
    filteringBtn: { mt: 2 },
    carContainer: { py: 10 },
    carSubTitle: { mb: 4, fontWeight: 'bold' },
    pagination: { display: 'flex', justifyContent: 'center', mb: 4 },
  };

  return allProducts && mounted ? (
    <ThemeProvider theme={responsiveFontTheme}>
      <CssBaseline />
      {/* main page */}
      <main className={styles.main}>
        <Stack alignItems="center" className={styles.bannerImageContainer}>
          <Image src={BannerImage} layout="fill" className={styles.bannerImage} />
        </Stack>

        <Box sx={MainPageCss.mainBox}>
          <Container maxWidth="md">
            <Typography component="h2" variant="h3" align="center" gutterBottom>
              우차차에서
            </Typography>
            <Typography component="h2" variant="h3" align="center">
              <span className={styles.strongColor}>허위매물 걱정 없는</span>
            </Typography>
            <Typography component="h2" variant="h3" align="center">
              자동차를 만나보세요
            </Typography>

            <br />

            <Typography variant="h5" align="center" my={2} paragraph className={styles.subTitle}>
              우차차는 중고차를 안정적으로 거래하실 수 있는 서비스입니다.
            </Typography>
          </Container>
        </Box>

        {/* 차량 보러가기 버튼 */}
        <div className={styles.wrapper}>
          <a className={styles.cta} href="#">
            <span className={styles.span}>차량 보러가기!</span>
            <span className={styles.span}>
              <svg
                width="66px"
                height="43px"
                viewBox="0 0 66 43"
                version="1.1"
                xmlns="http://www.w3.org/2000/svg"
                xmlnsXlink="http://www.w3.org/1999/xlink">
                <g id="arrow" stroke="none" strokeWidth="1" fill="none" fillRule="evenodd">
                  <path
                    className={styles.one}
                    d="M40.1543933,3.89485454 L43.9763149,0.139296592 C44.1708311,-0.0518420739 44.4826329,-0.0518571125 44.6771675,0.139262789 L65.6916134,20.7848311 C66.0855801,21.1718824 66.0911863,21.8050225 65.704135,22.1989893 C65.7000188,22.2031791 65.6958657,22.2073326 65.6916762,22.2114492 L44.677098,42.8607841 C44.4825957,43.0519059 44.1708242,43.0519358 43.9762853,42.8608513 L40.1545186,39.1069479 C39.9575152,38.9134427 39.9546793,38.5968729 40.1481845,38.3998695 C40.1502893,38.3977268 40.1524132,38.395603 40.1545562,38.3934985 L56.9937789,21.8567812 C57.1908028,21.6632968 57.193672,21.3467273 57.0001876,21.1497035 C56.9980647,21.1475418 56.9959223,21.1453995 56.9937605,21.1432767 L40.1545208,4.60825197 C39.9574869,4.41477773 39.9546013,4.09820839 40.1480756,3.90117456 C40.1501626,3.89904911 40.1522686,3.89694235 40.1543933,3.89485454 Z"
                    fill="#FFFFFF"></path>
                  <path
                    className={styles.two}
                    d="M20.1543933,3.89485454 L23.9763149,0.139296592 C24.1708311,-0.0518420739 24.4826329,-0.0518571125 24.6771675,0.139262789 L45.6916134,20.7848311 C46.0855801,21.1718824 46.0911863,21.8050225 45.704135,22.1989893 C45.7000188,22.2031791 45.6958657,22.2073326 45.6916762,22.2114492 L24.677098,42.8607841 C24.4825957,43.0519059 24.1708242,43.0519358 23.9762853,42.8608513 L20.1545186,39.1069479 C19.9575152,38.9134427 19.9546793,38.5968729 20.1481845,38.3998695 C20.1502893,38.3977268 20.1524132,38.395603 20.1545562,38.3934985 L36.9937789,21.8567812 C37.1908028,21.6632968 37.193672,21.3467273 37.0001876,21.1497035 C36.9980647,21.1475418 36.9959223,21.1453995 36.9937605,21.1432767 L20.1545208,4.60825197 C19.9574869,4.41477773 19.9546013,4.09820839 20.1480756,3.90117456 C20.1501626,3.89904911 20.1522686,3.89694235 20.1543933,3.89485454 Z"
                    fill="#FFFFFF"></path>
                  <path
                    className={styles.three}
                    d="M0.154393339,3.89485454 L3.97631488,0.139296592 C4.17083111,-0.0518420739 4.48263286,-0.0518571125 4.67716753,0.139262789 L25.6916134,20.7848311 C26.0855801,21.1718824 26.0911863,21.8050225 25.704135,22.1989893 C25.7000188,22.2031791 25.6958657,22.2073326 25.6916762,22.2114492 L4.67709797,42.8607841 C4.48259567,43.0519059 4.17082418,43.0519358 3.97628526,42.8608513 L0.154518591,39.1069479 C-0.0424848215,38.9134427 -0.0453206733,38.5968729 0.148184538,38.3998695 C0.150289256,38.3977268 0.152413239,38.395603 0.154556228,38.3934985 L16.9937789,21.8567812 C17.1908028,21.6632968 17.193672,21.3467273 17.0001876,21.1497035 C16.9980647,21.1475418 16.9959223,21.1453995 16.9937605,21.1432767 L0.15452076,4.60825197 C-0.0425130651,4.41477773 -0.0453986756,4.09820839 0.148075568,3.90117456 C0.150162624,3.89904911 0.152268631,3.89694235 0.154393339,3.89485454 Z"
                    fill="#FFFFFF"></path>
                </g>
              </svg>
            </span>
          </a>
        </div>

        <Container sx={MainPageCss.carContainer} maxWidth="lg">
          <Typography
            sx={MainPageCss.carSubTitle}
            component="h4"
            variant="h4"
            color="inherit"
            gutterBottom>
            <span className={styles.strongColor}>우차차</span>가 추천하는 차량
          </Typography>
          {/* 차량 item */}
          <Grid container spacing={4}>
            <ProductCard productItems={allProducts.productInfo} />
          </Grid>
        </Container>

        {/* pagination */}
        <Grid item md={12} xs={12} sx={MainPageCss.pagination}>
          {allProducts.productInfo.totalPages === 0 ? (
            ''
          ) : (
            <Pagination
              count={allProducts.productInfo.totalPages}
              page={page}
              onChange={handleChange}
            />
          )}
        </Grid>

        <MainProduct post={mainFeaturedPost} />
      </main>
    </ThemeProvider>
  ) : (
    <LoadingBar />
  );
}

export async function getServerSideProps() {
  const res = await allProductGetApi(0, 8).then((res) => res.data);
  return {
    props: {
      allPr: res,
    },
  };
}
