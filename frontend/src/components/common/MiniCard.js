import { Card } from '@mui/material';
import React from 'react';

export default function MiniCard({ children, colorVal, shadowVal }) {
  return (
    <Card
      sx={{
        margin: 'auto',
        backgroundColor: colorVal,
        boxShadow: shadowVal,
        py: 8,
        px: 8,
        my: 25,
        height: '100%',
        width: '100%',
        display: 'flex',
        flexDirection: 'column',
      }}>
      {children}
    </Card>
  );
}
