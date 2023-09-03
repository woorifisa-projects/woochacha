import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import { Typography } from '@mui/material';
import withAdminAuth from '@/hooks/withAdminAuth';
import BasicModal from '@/components/common/BasicModal';
import { DELETE_MODAL } from '@/constants/string';
import { useRouter } from 'next/router';
import BasicButtonTable from '@/components/admin/ProductTable';

function AdminProductList() {
  const [mounted, setMounted] = useState(false);
  const router = useRouter();

  // Modal 버튼 클릭 유무
  const [showModal, setShowModal] = useState(false);
  const handleClickModal = () => setShowModal(!showModal);

  /**
   * 수정 form 제출
   */
  const handleDelete = async () => {
    try {
      // await adminDeleteProduct(memberId, productId, updateData);
      alert('삭제 완료');
      router.push(`/admin/product`);
    } catch (error) {
      console.log('실패');
    }
  };

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
      contentCell: 'id',
    },
    {
      headerLabel: '판매자',
      contentCell: 'seller',
    },
    {
      headerLabel: '상태',
      contentCell: 'status',
    },
    {
      headerLabel: '관리',
      contentCell: 'isAvailable',
    },
  ];

  // TODO: axios로 data 받아온 데이터라고 가정
  const dummy_content_data = {
    content: [
      {
        id: '1',
        seller: '홍길길',
        status: '김김김',
        isAvailable: 0,
      },
      {
        id: '2',
        seller: '홍김김',
        status: '김김김',
        isAvailable: 1,
      },
      {
        id: '3',
        seller: '왕길길',
        status: '김김김',
        isAvailable: 0,
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
          관리자 페이지 - 매물 관리 리스트
        </Typography>
        <BasicButtonTable
          headerData={table_cell_data}
          contentData={dummy_content_data.content}
          moveUrl={`/admin/product/edit/`}
          callbackFunc={handleClickModal}
        />
        {showModal && (
          <BasicModal
            onClickModal={handleClickModal}
            isOpen={showModal}
            modalContent={DELETE_MODAL.CONTENTS}
            callBackFunc={handleDelete}
          />
        )}
      </>
    )
  );
}

// side menu 레이아웃
AdminProductList.Layout = withAdminAuth(AdminPageLayout);
export default AdminProductList;
