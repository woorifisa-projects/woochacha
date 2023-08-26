import { useEffect, useState } from 'react';
import {
  Button,
  Card,
  CardActions,
  CardContent,
  CardMedia,
  Chip,
  Grid,
  Typography,
} from '@mui/material';
import { useRouter } from 'next/router';

export default function ProductCard({ cardItems }) {
  const router = useRouter();
  const [mounted, setMounted] = useState(false);
  const productCardCss = {
    card: {
      height: '100%',
      display: 'flex',
      flexDirection: 'column',
      '&:hover': {
        boxShadow: 10,
        cursor: 'pointer',
        transition: '0.3s',
        transform: 'scale(1.03)',
      },
    },
    cardMedia: { pt: '100%' },
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
      <Grid container spacing={3}>
        {cardItems.map((item) => (
          <Grid item key={item.id} xs={12} sm={6} md={3}>
            <Card sx={productCardCss.card} onClick={() => handleMoveDetail(item.productUrl)}>
              <CardMedia component="div" sx={productCardCss.cardMedia} image={item.productImgUrl} />
              <CardContent sx={productCardCss.cardContent}>
                <Typography gutterBottom variant="h5" component="h5">
                  {item.carName}
                </Typography>
                <Grid container my={1} gap={1}>
                  <Chip variant="outlined" label={`${item.price} 만원`} />
                  <Chip variant="outlined" label={`${item.createdDate}`} />
                  <Chip variant="outlined" label={`${item.distance} km`} />
                  <Chip variant="outlined" label={`${item.carWhere} 차고지`} />
                </Grid>
              </CardContent>
              <CardActions>
                <Button size="small">보러가기</Button>
              </CardActions>
            </Card>
          </Grid>
        ))}
      </Grid>
    )
  );
}
