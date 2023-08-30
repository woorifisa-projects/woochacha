import { useEffect, useState } from 'react';
import { Card, CardContent, CardMedia, Typography, Grid, Chip } from '@mui/material';
import { useRouter } from 'next/router';

export default function MypageCard() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();

  // TODO: API 조회 후 수정할 dummy data입니다.
  const itemDummyArr = [
    {
      id: 14,
      title: '기아 올 뉴 카니발 2018년형',
      distance: 110000,
      branch: '서울',
      price: 2690,
      imageUrl: 'https://woochacha.s3.ap-northeast-2.amazonaws.com/product/22%EB%82%982222/1',
    },
    {
      id: 15,
      title: '기아 모닝 2010년형',
      distance: 100000,
      branch: '서울',
      price: 270,
      imageUrl: 'https://woochacha.s3.ap-northeast-2.amazonaws.com/product/33%EB%8B%A43333/1',
    },
    {
      id: 16,
      title: '기아 올 뉴 카니발 2018년형',
      distance: 110000,
      branch: '서울',
      price: 2690,
      imageUrl: 'https://woochacha.s3.ap-northeast-2.amazonaws.com/product/22%EB%82%982222/1',
    },
    {
      id: 17,
      title: '기아 모닝 2010년형',
      distance: 100000,
      branch: '서울',
      price: 270,
      imageUrl: 'https://woochacha.s3.ap-northeast-2.amazonaws.com/product/33%EB%8B%A43333/1',
    },
    {
      id: 18,
      title: '기아 올 뉴 카니발 2018년형',
      distance: 110000,
      branch: '서울',
      price: 2690,
      imageUrl: 'https://woochacha.s3.ap-northeast-2.amazonaws.com/product/22%EB%82%982222/1',
    },
    {
      id: 19,
      title: '기아 모닝 2010년형',
      distance: 100000,
      branch: '서울',
      price: 270,
      imageUrl: 'https://woochacha.s3.ap-northeast-2.amazonaws.com/product/33%EB%8B%A43333/1',
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
      borderRadius: '1rem',
      '&:hover': {
        boxShadow: 10,
        cursor: 'pointer',
        transition: '0.3s',
        transform: 'scale(1.03)',
      },
    },
    cardMedia: { width: '20%', height: '100%' },
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
            <Card
              sx={mypageCardCss.card}
              onClick={() => handleMoveDetail(`/product/detail/${item.id}`)}>
              <CardMedia component="div" sx={mypageCardCss.cardMedia} image={item.imageUrl} />
              <CardContent sx={mypageCardCss.cardContent}>
                <Typography gutterBottom variant="h5" component="h5">
                  {item.title}
                </Typography>
                <Grid container my={1} gap={1}>
                  <Chip size="small" label={`주행거리 : ${item.distance} km`} />
                  <Chip size="small" label={`가격 : ${item.price} 만원`} />
                  <Chip size="small" label={`지점 : ${item.branch}`} />
                </Grid>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    )
  );
}
