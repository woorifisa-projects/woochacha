import { useEffect, useState } from 'react';
import { Card, CardContent, CardMedia, Typography, Grid, Chip, Button } from '@mui/material';
import { useRouter } from 'next/router';
import BasicModal from '../common/BasicModal';
import { DELETE_MODAL } from '@/constants/string';

export default function MypageCardEdit(props) {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const { content, memberId } = props;
  console.log(content);

  // Modal 버튼 클릭 유무
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);

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

  const handleMoveEdit = (url, productId) => {
    router.push({
      pathname: url,
      query: { memberId, productId },
    });
  };

  const handleDeleteItem = () => {
    // TODO: 삭제 API 요청 - 삭제 modal => 삭제
    alert('게시글 삭제 요청이 완료되었습니다!');
    router.push(`/mypage/registered/${memberId}`);
  };

  return (
    mounted && (
      <Grid container spacing={3} sx={mypageCardCss.container}>
        {content.map((item) => (
          <Grid item key={item.id} xs={12} sm={12} md={12}>
            <Card sx={mypageCardCss.card}>
              <CardMedia
                component="div"
                sx={mypageCardCss.cardMedia}
                image={item.imageUrl}
                onClick={() => handleMoveDetail(`/product/detail/${item.id}`)}
              />
              <CardContent sx={mypageCardCss.cardContent}>
                <Typography
                  gutterBottom
                  variant="h5"
                  component="h5"
                  onClick={() => handleMoveDetail(`/product/detail/${item.id}`)}>
                  {item.title}
                </Typography>
                <Grid container spacing={2}>
                  <Grid
                    item
                    xs={9}
                    container
                    my={1}
                    gap={1}
                    onClick={() => handleMoveDetail(`/product/detail/${item.id}`)}>
                    <Chip size="small" label={`주행거리 : ${item.distance} km`} />
                    <Chip size="small" label={`가격 : ${item.price} 만원`} />
                    <Chip size="small" label={`지점 : ${item.branch}`} />
                    <Chip size="small" label={`상태 : ${item.status}`} />
                  </Grid>
                  <Grid item xs={3} container my={1} gap={1}>
                    <Button
                      onClick={() => handleMoveEdit(`/mypage/registered/edit`, item.productId)}>
                      수정요청
                    </Button>
                    <Button onClick={handleClickModal}>삭제요청</Button>
                  </Grid>
                </Grid>
              </CardContent>
            </Card>
          </Grid>
        ))}
        {showModal && (
          <BasicModal
            onClickModal={handleClickModal}
            isOpen={showModal}
            modalContent={DELETE_MODAL.CONTENTS}
            callBackFunc={handleDeleteItem}
          />
        )}
      </Grid>
    )
  );
}
