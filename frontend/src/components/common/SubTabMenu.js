import { useState } from 'react';
import { Box, Tab, Tabs } from '@mui/material';

export default function SubTabMenu(props) {
  const { currentVal, children } = props;
  const [value, setValue] = useState(currentVal); // submenu tab value

  const handleTabChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Box sx={{ width: '100%' }}>
      <Tabs
        value={value}
        onChange={handleTabChange}
        textColor="primary"
        indicatorColor="primary"
        aria-label="purchase-menu-tab">
        {children}
      </Tabs>
    </Box>
  );
}
