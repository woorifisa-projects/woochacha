import { useEffect, useState } from 'react';
import { Card, CardContent, CardMedia, Typography, Grid, Chip } from '@mui/material';
import { useRouter } from 'next/router';

// TODO: 수정해야함 
export default function MypageCard() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
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
        {mypageRegisteredProducts.content.map((item) => (
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
