import { useEffect, useState } from 'react';
import { Card, CardContent, Typography, Grid, Chip, CardMedia, Box } from '@mui/material';
import defaultCatImg from '../../../public/assets/images/defaultCarImg.svg';
import Image from 'next/image';

export default function MypageCardPurchase(props) {
  const [mounted, setMounted] = useState(false);
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
        transition: '0.3s',
        transform: 'scale(1.01)',
      },
    },
    cardMedia: { width: '20%', height: '100%' },
    cardContent: { flexGrow: 1 },
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    mounted && (
      <Grid container spacing={3} sx={mypageCardCss.container}>
        {content.map((item) => (
          <Grid item key={item.id} xs={12} sm={12} md={12}>
            <Card sx={mypageCardCss.card}>
              <Box sx={{ width: '20%', position: 'relative' }}>
                <Image src={defaultCatImg} alt="기본이미지" layout="fill" objectFit="cover" />
              </Box>
              <CardContent sx={mypageCardCss.cardContent}>
                <Typography gutterBottom variant="h5" component="h5">
                  {item.carNum}
                </Typography>
                <Grid container my={1} gap={1}>
                  <Chip size="small" label={`지점 : ${item.branch}`} />
                  <Chip
                    size="small"
                    color={
                      item.carStatus === '심사완료' < 5000
                        ? 'chipBlue'
                        : item.carStatus === '심사중' < 10000
                        ? 'chipYellow'
                        : 'chipRed'
                    }
                    label={`상태 : ${item.carStatus}`}
                  />
                  <Chip
                    size="small"
                    label={`신청일 : ${new Date(item.createdAt).toISOString().split('T')[0]}`}
                  />
                </Grid>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    )
  );
}
