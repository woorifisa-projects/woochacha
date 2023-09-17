import React from 'react';
import styles from './NoData.module.css';
import { Stack } from '@mui/material';
import nodataImage from '../../../public/assets/images/woochacha-nodata.svg';
import Image from 'next/image';

export default function NoData() {
  return (
    <div className={styles.box}>
      <Stack alignItems="center" className={styles.bannerImageContainer}>
        <Image src={nodataImage} alt="no-data-image" layout="fill" className={styles.bannerImage} />
      </Stack>
    </div>
  );
}
