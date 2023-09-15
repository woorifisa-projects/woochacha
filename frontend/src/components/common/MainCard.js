import { Box, Container, Typography } from '@mui/material';
import React from 'react';

export default function MainCard({ titleVal, subTitleVal, titleColor }) {
  return (
    <Box
      sx={{
        bgcolor: 'background.paper',
        my: 10,
        pt: 8,
        pb: 6,
      }}>
      <Container maxWidth="md">
        <Typography
          component="h4"
          variant="h4"
          align="center"
          fontWeight="bold"
          color={titleColor}
          mb={5}
          gutterBottom>
          {titleVal}
        </Typography>
        <Typography variant="h5" align="center" color="text.secondary" paragraph>
          {subTitleVal}
        </Typography>
      </Container>
    </Box>
  );
}
