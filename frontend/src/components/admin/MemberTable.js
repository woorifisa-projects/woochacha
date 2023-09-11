import { useEffect, useState } from 'react';
import { useTheme } from '@mui/material/styles';
import {
  Box,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableFooter,
  TablePagination,
  TableRow,
  Paper,
  IconButton,
  Typography,
} from '@mui/material';
import FirstPageIcon from '@mui/icons-material/FirstPage';
import KeyboardArrowLeft from '@mui/icons-material/KeyboardArrowLeft';
import KeyboardArrowRight from '@mui/icons-material/KeyboardArrowRight';
import LastPageIcon from '@mui/icons-material/LastPage';
import { useRouter } from 'next/router';
import Link from 'next/link';
import { allUserGetApi } from '@/services/adminpageApi';

/**
 * 페이지네이션 관련 함수
 */
function TablePaginationActions(props) {
  const theme = useTheme();
  const { count, page, rowsPerPage, onPageChange } = props;

  const handleFirstPageButtonClick = (event) => {
    onPageChange(event, 0);
  };

  const handleBackButtonClick = (event) => {
    onPageChange(event, page - 1);
  };

  const handleNextButtonClick = (event) => {
    onPageChange(event, page + 1);
  };

  const handleLastPageButtonClick = (event) => {
    onPageChange(event, Math.max(0, Math.ceil(count / rowsPerPage) - 1));
  };

  return (
    <Box sx={{ flexShrink: 0, ml: 2.5 }}>
      <IconButton
        onClick={handleFirstPageButtonClick}
        disabled={page === 0}
        aria-label="first page">
        {theme.direction === 'rtl' ? <LastPageIcon /> : <FirstPageIcon />}
      </IconButton>
      <IconButton onClick={handleBackButtonClick} disabled={page === 0} aria-label="previous page">
        {theme.direction === 'rtl' ? <KeyboardArrowRight /> : <KeyboardArrowLeft />}
      </IconButton>
      <IconButton
        onClick={handleNextButtonClick}
        disabled={page >= Math.ceil(count / rowsPerPage) - 1}
        aria-label="next page">
        {theme.direction === 'rtl' ? <KeyboardArrowLeft /> : <KeyboardArrowRight />}
      </IconButton>
      <IconButton
        onClick={handleLastPageButtonClick}
        disabled={page >= Math.ceil(count / rowsPerPage) - 1}
        aria-label="last page">
        {theme.direction === 'rtl' ? <FirstPageIcon /> : <LastPageIcon />}
      </IconButton>
    </Box>
  );
}

// basic component
export default function MemberTable(props) {
  const {
    headerData,
    contentData,
    moveUrl,
    onPageChange, // 페이지 번호 변경 핸들러 전달 받음
    onPageSizeChange, // 페이지 크기 변경 핸들러 전달 받음
  } = props;
  // const rows = contentData.content;
  const [mounted, setMounted] = useState(false);

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const router = useRouter();
  const [pageSize, setPageSize] = useState(10); // 페이지 크기 초기값 설정
  const [allUserInfo, setAllUserInfo] = useState();
  const [rows, setRows] = useState(contentData.content);

  // Avoid a layout jump when reaching the last page with empty rows.
  // console.log(rows);
  const emptyRows = page > 0 ? Math.max(0, (1 + page) * rowsPerPage - rows.length) : 0;

  const fetchDataForPage = async (newPage) => {
    try {
      setPage(newPage);
      const response = await allUserGetApi(newPage);

      // const nextPage = newPage + 1; // 다음 페이지 번호
      // const response = await allUserGetApi(page, pageSize); // 다음 페이지에 대한 데이터 가져오기
      if (response.status === 200) {
        setAllUserInfo(response.data);
        setRows(response.data.content);
      }

      // console.log(response.data.content);
      // setRows(response.data.content);
      console.log(newPage);
      console.log(rows);
      console.log(response.data.content);
    } catch (error) {
      console.error('데이터 가져오기 실패: ', error);
    }
  };

  const handleChangePage = (event, newPage) => {
    onPageChange(newPage); // 페이지 번호 변경 핸들러 호출
    fetchDataForPage(newPage); // 페이지 번호가 변경될 때 데이터 가져오기
  };

  const handleChangeRowsPerPage = (event) => {
    const newSize = parseInt(event.target.value, 10);
    onPageSizeChange(newSize); // 페이지 크기 변경 핸들러 호출
    fetchDataForPage(0, newSize);
  };

  const handleMove = (memberId) => {
    router.push(`${moveUrl}${memberId}`);
  };

  const handleLogMove = (memberId) => {
    router.push(`/admin/mebers/log/${memberId}`);
  };

  useEffect(() => {
    setMounted(true);
  }, []);

  const basicTableCss = {
    tableRow: {
      '&:hover': {
        boxShadow: 10,
        cursor: 'pointer',
        transition: '0.3s',
        transform: 'scale(1.01)',
      },
    },
  };

  return (
    mounted && (
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 500 }} aria-label="custom pagination table">
          <TableHead>
            <TableRow>
              {headerData.map((headerItem, idx) => {
                return (
                  <TableCell align="center" key={idx}>
                    {headerItem.headerLabel}
                  </TableCell>
                );
              })}
            </TableRow>
          </TableHead>
          <TableBody>
            {/* data map으로 반복 */}
            {(rowsPerPage > 0
              ? rows.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              : rows
            ).map((row) => (
              <TableRow sx={basicTableCss.tableRow} key={row.id}>
                <TableCell onClick={() => handleMove(row.id)} align="center">
                  {row[`${headerData[0].contentCell}`]}
                </TableCell>
                <TableCell align="center">{row[`${headerData[1].contentCell}`]}</TableCell>
                <TableCell align="center">{row[`${headerData[2].contentCell}`]}</TableCell>
                {row[headerData[3].contentCell] === 1 ? (
                  <TableCell align="center">
                    <Typography variant="body2" color="primary">{`일반 사용자`}</Typography>
                  </TableCell>
                ) : (
                  <TableCell align="center">
                    <Typography variant="body2" color="error">{`이용제한 사용자`}</Typography>
                  </TableCell>
                )}
                <TableCell align="center">
                  <Link href={`/admin/members/log/${row.id}`}>로그조회</Link>
                </TableCell>
              </TableRow>
            ))}
            {emptyRows > 0 && (
              <TableRow style={{ height: 53 * emptyRows }}>
                <TableCell colSpan={6} />
              </TableRow>
            )}
          </TableBody>
          <TableFooter>
            <TableRow>
              <TablePagination
                rowsPerPageOptions={[5, 10, 25, { label: 'All', value: -1 }]}
                colSpan={3}
                count={contentData.totalElements}
                rowsPerPage={rowsPerPage}
                page={page}
                SelectProps={{
                  inputProps: {
                    'aria-label': 'rows per page',
                  },
                  native: true,
                }}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
                ActionsComponent={TablePaginationActions}
              />
            </TableRow>
          </TableFooter>
        </Table>
      </TableContainer>
    )
  );
}
