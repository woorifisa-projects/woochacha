import { useEffect, useState } from 'react';
import { Card, CardContent, CardMedia, Chip, Grid, Typography } from '@mui/material';
import { useRouter } from 'next/router';
import styles from './ProductCard.module.css';

export default function ProductCard(props) {
  const { productItems } = props;
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
    cardMedia: { pt: '80%' },
    cardContent: { flexGrow: 1 },
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  const handleMoveDetail = (url) => {
    router.push(`/product/detail/${url}`);
  };

  return (
    mounted && (
      <>
        {productItems.content.map((item) => (
          <Grid item key={item.id} xs={12} sm={6} md={3}>
            <Card sx={productCardCss.card} onClick={() => handleMoveDetail(item.id)}>
              <CardMedia component="div" sx={productCardCss.cardMedia} image={item.imageUrl} />
              <CardContent sx={productCardCss.cardContent}>
                <Grid container height="100%" alignContent="space-between">
                  <Grid item>
                    <Typography gutterBottom variant="h6" component="h6">
                      {item.title}
                    </Typography>
                  </Grid>
                  <Grid item>
                    <Typography
                      gutterBottom
                      variant="body1"
                      component="body1"
                      color="primary"
                      fontWeight="bold">
                      {`${Number(item.price).toLocaleString()}`}
                    </Typography>
                    <span>{` 만원`}</span>
                  </Grid>
                  <Grid item container my={1} gap={1}>
                    <Chip
                      size="small"
                      label={`${Number(item.distance).toLocaleString()} km`}
                      color={
                        Number(item.distance) < 5000
                          ? 'chipBlue'
                          : Number(item.distance) < 10000
                          ? 'chipYellow'
                          : 'chipRed'
                      }
                    />
                    <Chip
                      size="small"
                      label={`${item.branch}`}
                      color={
                        item.branch === '서울'
                          ? 'chipBlue'
                          : item.branch === '부산'
                          ? 'chipYellow'
                          : 'chipRed'
                      }
                    />
                  </Grid>
                </Grid>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </>
    )
  );
}
