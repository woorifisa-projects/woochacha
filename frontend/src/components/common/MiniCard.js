import { Card } from '@mui/material';
import React from 'react';

export default function MiniCard({ children, colorVal, shadowVal, marginVal }) {
  return (
    <Card
      sx={{
        margin: 'auto',
        backgroundColor: '#FFF',
        boxShadow: '0px -25px 30px 3px rgba(0,0,0,0.05)',
        py: 10,
        px: 8,
        my: marginVal,
        height: '100%',
        width: '100%',
        display: 'flex',
        borderTopLeftRadius: '3rem',
        flexDirection: 'column',
      }}>
      {children}
    </Card>
  );
}
