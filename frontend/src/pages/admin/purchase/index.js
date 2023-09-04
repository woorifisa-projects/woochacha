import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { Typography } from '@mui/material';
import withAdminAuth from '@/hooks/withAdminAuth';
import PurchaseTable from '@/components/admin/PurchaseTable';

function AdminPurchaseList() {
  const [mounted, setMounted] = useState(false);

  const mypageCss = {
    mypageTitle: {
      my: 10,
      color: '#1490ef',
      fontWeight: 'bold',
    },
  };

  const table_cell_data = [
    {
      headerLabel: '게시글',
      contentCell: 'productId',
    },
    {
      headerLabel: '구매자',
      contentCell: 'buyer',
    },
    {
      headerLabel: '판매자',
      contentCell: 'seller',
    },
    {
      headerLabel: '검토여부',
      contentCell: 'isConfirm',
    },
    {
      headerLabel: '거래여부',
      contentCell: 'transactionStatus',
    },
  ];

  // TODO: axios로 data 받아온 데이터라고 가정
  const dummy_content_data = {
    content: [
      {
        productId: '12',
        buyer: '김구매',
        seller: '김판매',
        isConfirm: 0,
        transactionStatus: 0,
      },
      {
        productId: '13',
        buyer: '박구매',
        seller: '박판매',
        isConfirm: 1,
        transactionStatus: 1,
      },
      {
        productId: '14',
        buyer: '이구매',
        seller: '이판매',
        isConfirm: 0,
        transactionStatus: 0,
      },
    ],
  };

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    mounted && (
      <>
        <Typography sx={mypageCss.mypageTitle} component="h4" variant="h4" gutterBottom>
          관리자 페이지 - [거래관리] 구매 요청 리스트
        </Typography>
        <PurchaseTable
          headerData={table_cell_data}
          contentData={dummy_content_data.content}
          moveDetailUrl={`/product/detail/`}
          moveMatchUrl={`/admin/purchase/`}
          moveDealUrl={`/admin/purchase/`}
        />
      </>
    )
  );
}

// side menu 레이아웃
AdminPurchaseList.Layout = withAdminAuth(AdminPageLayout);
export default AdminPurchaseList;
