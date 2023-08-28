import { useEffect, useState } from 'react';
import { Card, CardContent, CardMedia, Typography, Grid, Chip } from '@mui/material';
import { useRouter } from 'next/router';

export default function MypageCard() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();

  // TODO: API 조회 후 수정할 dummy data입니다.
  const itemDummyArr = [
    {
      productImgUrl: 'https://source.unsplash.com/random?car',
      carName: '멋쟁이 자동차1 게시글',
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

  const mypageCardCss = {
    container: {
      mb: 10,
    },
    card: {
      height: '100%',
      display: 'flex',
      flexDirection: 'row',
      '&:hover': {
        boxShadow: 10,
        cursor: 'pointer',
        transition: '0.3s',
        transform: 'scale(1.03)',
      },
    },
    cardMedia: { width: '30%' },
    cardContent: { flexGrow: 1 },
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  const handleMoveDetail = (url) => {
    router.push(url);
  };

  return (
    mounted && (
      <Grid container spacing={3} sx={mypageCardCss.container}>
        {itemDummyArr.map((item) => (
          <Grid item key={item.id} xs={12} sm={12} md={12}>
            <Card sx={mypageCardCss.card} onClick={() => handleMoveDetail(item.productUrl)}>
              <CardMedia component="div" sx={mypageCardCss.cardMedia} image={item.productImgUrl} />
              <CardContent sx={mypageCardCss.cardContent}>
                <Typography gutterBottom variant="h5" component="h5">
                  {item.carName}
                </Typography>
                <Grid container my={1} gap={1}>
                  <Chip size="small" label={`연식 : ${item.createdDate}`} />
                  <Chip size="small" label={`주행거리 : ${item.distance} km`} />
                  <Chip size="small" label={`가격 : ${item.price} 만원`} />
                  <Chip size="small" label={`지점 : ${item.carWhere}`} />
                  <Chip size="small" label={`작성일 : ${item.createdDate}`} />
                </Grid>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    )
  );
}
