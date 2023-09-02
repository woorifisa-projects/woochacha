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
    cardMedia: { pt: '56.25%' },
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
        {productItems.map((item) => (
          <Grid item key={item.id} xs={12} sm={6} md={3}>
            <Card sx={productCardCss.card} onClick={() => handleMoveDetail(item.id)}>
              <CardMedia component="div" sx={productCardCss.cardMedia} image={item.imageUrl} />
              <CardContent sx={productCardCss.cardContent}>
                <Typography gutterBottom variant="h6" component="h6">
                  {item.title}
                </Typography>
                <Grid container my={1} gap={1}>
                  <Chip size="small" label={`${item.distance} km`} />
                  <Chip size="small" label={`${item.price} 만원`} />
                  <Chip size="small" label={`${item.branch}`} />
                </Grid>
              </CardContent>
              <CardActions>
                <Button size="small">보러가기</Button>
              </CardActions>
            </Card>
          </Grid>
        ))}
      </>
    )
  );
}
