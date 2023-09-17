import React from 'react';
import styles from './LoadingBar.module.css';

export default function LoadingBar() {
  return (
    <div className={styles.box}>
      <div className={styles.ldsEllipsis}>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
      </div>
    </div>
  );
}
