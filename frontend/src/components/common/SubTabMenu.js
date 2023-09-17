import { useState } from 'react';
import { Box, Tabs } from '@mui/material';

export default function SubTabMenu(props) {
  const { currentVal, children } = props;
  const [value, setValue] = useState(currentVal); // submenu tab value

  const handleTabChange = (event, newValue) => {
    setValue(newValue);
  };

  const subTabMenuCss = {
    box: {
      width: '100%',
      my: 5,
    },
  };

  return (
    <Box sx={subTabMenuCss.box}>
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
