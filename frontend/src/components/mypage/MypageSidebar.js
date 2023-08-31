import React from 'react';
import { Box, Container, ListItemText, MenuItem, MenuList, Paper } from '@mui/material';
import { HEADER_MINI_MENU } from '@/constants/string';
import { useRouter } from 'next/router';

export default function MypageSidebar() {
  const router = useRouter();
  const userId = 1; // DUMMY_DATA

  const myPageSidebarCss = {
    container: {
      width: '20%',
      height: '80%',
      mt: 3,
    },
    mypageMenuPaper: {
      backgroundColor: '#D6E6F5',
      borderRadius: '1rem',
      minWidth: '150px',
      maxWidth: '200px',
    },
    mypageMenuItem: {
      borderRadius: '0px',
      minWidth: '150px',
      maxWidth: '200px',
      paddingTop: 3,
      paddingBottom: 3,
      '&:hover': {
        cursor: 'pointer',
        backgroundColor: '#B2D8FA',
      },
    },
  };

  const handleMove = (url) => {
    router.push(url);
  };

  return (
    <Container sx={myPageSidebarCss.container}>
      <Box>
        <Paper sx={myPageSidebarCss.mypageMenuPaper}>
          <MenuList dense>
            {HEADER_MINI_MENU.CONTENTS.map((selectItem, idx) => {
              return (
                <MenuItem
                  sx={myPageSidebarCss.mypageMenuItem}
                  key={idx}
                  onClick={() => handleMove(`${selectItem.pageUrl}/${userId}`)}>
                  <ListItemText inset>{selectItem.pageName}</ListItemText>
                </MenuItem>
              );
            })}
          </MenuList>
        </Paper>
      </Box>
    </Container>
  );
}
