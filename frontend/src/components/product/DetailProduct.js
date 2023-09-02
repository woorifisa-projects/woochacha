import React from 'react';

export default function DetailProduct(props) {
  const { detailItem } = props;
  return <div>{detailItem.productBasicInfo.title}</div>;
}
