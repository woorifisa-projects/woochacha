import { Stack } from '@mui/material';
import Image from 'next/image';
import React from 'react';
import image404 from '../../public/assets/images/woochacha-404.svg';
import styles from './404.module.css';

export default function Custom404() {
  return (
    <div className={styles.box}>
      <Stack alignItems="center" className={styles.bannerImageContainer}>
        <Image src={image404} layout="fill" className={styles.bannerImage} alt="404-image" />
      </Stack>
    </div>
  );
}
