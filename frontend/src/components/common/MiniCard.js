import { Card } from '@mui/material';
import React from 'react';

export default function MiniCard({ children, colorVal, shadowVal, marginVal }) {
  return (
    <Card
      sx={{
        margin: 'auto',
        backgroundColor: colorVal,
        boxShadow: shadowVal,
        py: 8,
        px: 8,
        my: marginVal,
        height: '100%',
        width: '80%',
        display: 'flex',
        flexDirection: 'column',
      }}>
      {children}
    </Card>
  );
}
