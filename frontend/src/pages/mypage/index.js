import withAuth from '@/hooks/withAuth';
import React from 'react';

function Mypage() {
  return <div>마이페이지</div>;
}

export default withAuth(Mypage);
