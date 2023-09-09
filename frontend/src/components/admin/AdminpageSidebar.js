import { useEffect, useState } from 'react';
import { Box, Container, ListItemText, MenuItem, MenuList, Paper } from '@mui/material';
import { HEADER_ADMIN_MINI_MENU } from '@/constants/string';
import { useRouter } from 'next/router';

export default function AdminpageSidebar() {
  const router = useRouter();

  // 현재 선택된 메뉴 아이템을 나타내는 상태
  const [selectedMenu, setSelectedMenu] = useState('');

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

  // 페이지 이동 시 현재 선택된 메뉴 초기화
  useEffect(() => {
    // 현재 경로에 해당하는 메뉴 아이템을 찾아 선택된 메뉴를 업데이트
    const pathname = router.pathname;
    HEADER_ADMIN_MINI_MENU.CONTENTS.map((item) => {
      if (pathname.includes(item.page)) {
        setSelectedMenu(item.page);
      }
    });
  }, []);

  const handleMove = (url) => {
    router.push(url);
  };

  return (
    <Container sx={myPageSidebarCss.container}>
      <Box>
        <Paper sx={myPageSidebarCss.mypageMenuPaper}>
          <MenuList dense>
            {HEADER_ADMIN_MINI_MENU.CONTENTS.map((selectItem) => {
              return (
                <MenuItem
                  sx={{
                    ...myPageSidebarCss.mypageMenuItem,
                    ...(selectedMenu === selectItem.page && myPageSidebarCss.selectedMenuItem), // 선택된 메뉴 아이템에 스타일 적용
                  }}
                  key={selectItem.page}
                  onClick={() => handleMove(`${selectItem.pageUrl}`)}>
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
