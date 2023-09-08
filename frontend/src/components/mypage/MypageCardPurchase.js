import { useEffect, useState } from 'react';
import { Card, CardContent, CardMedia, Typography, Grid, Chip } from '@mui/material';
import { useRouter } from 'next/router';

export default function MypageCardPurchase(props) {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const { content } = props;

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

  // TODO: 기존 이미지 있었던 곳 공백 없애기, 신청일 dateFormat 수정
  return (
    mounted && (
      <Grid container spacing={3} sx={mypageCardCss.container}>
        {content.map((item) => (
          <Grid item key={item.id} xs={12} sm={12} md={12}>
            <Card sx={mypageCardCss.card}>
              <CardMedia component="div" sx={mypageCardCss.cardMedia} />
              <CardContent sx={mypageCardCss.cardContent}>
                <Typography gutterBottom variant="h5" component="h5">
                  {item.carNum}
                </Typography>
                <Grid container my={1} gap={1}>
                  <Chip size="small" label={`지점 : ${item.branch}`} />
                  <Chip size="small" label={`상태 : ${item.carStatus}`} />
                  <Chip size="small" label={`신청일 : ${item.createdAt}`} />
                </Grid>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    )
  );
}
