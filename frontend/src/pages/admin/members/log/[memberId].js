import { useEffect, useState } from 'react';
import AdminPageLayout from '@/layouts/admin/AdminPageLayout';
import withAdminAuth from '@/hooks/withAdminAuth';
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
import { oneMemberLogGetApi } from '@/services/adminpageApi';
import Link from 'next/link';

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
function LogDetail() {
  const [mounted, setMounted] = useState(false);
  const [oneMemberLog, setOneMemberLog] = useState();
  const router = useRouter();
  const { memberId } = router.query;

  useEffect(() => {
    memberId &&
      oneMemberLogGetApi(memberId).then((data) => {
        setOneMemberLog(data);
        console.log(oneMemberLog);
      });
    setMounted(true);
  }, []);

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);

  //   const rows = oneMemberLog.content;
  // Avoid a layout jump when reaching the last page with empty rows.
  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - setOneMemberLog.content.length) : 0;

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
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

  const table_cell_data = [
    {
      headerLabel: '이메일',
      contentCell: 'email',
    },
    {
      headerLabel: '이름',
      contentCell: 'name',
    },
    {
      headerLabel: '시간',
      contentCell: 'date',
    },
    {
      headerLabel: '로그 내용',
      contentCell: 'description',
    },
    {
      headerLabel: '로그 확인',
      contentCell: 'etc',
    },
  ];

  return (
    mounted &&
    oneMemberLog &&(
      <TableContainer component={Paper} sx={{ my: 10 }}>
        <Table sx={{ minWidth: 500 }} aria-label="custom pagination table">
          <TableHead>
            <TableRow>
              {table_cell_data.map((headerItem, idx) => {
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
              ? oneMemberLog.content.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              : oneMemberLog.content
            ).map((row) => (
              <TableRow sx={basicTableCss.tableRow} key={row.id}>
                <TableCell align="center">{row[`${table_cell_data[0].contentCell}`]}</TableCell>
                <TableCell align="center">{row[`${table_cell_data[1].contentCell}`]}</TableCell>
                <TableCell align="center">{row[`${table_cell_data[2].contentCell}`]}</TableCell>
                <TableCell align="center">{row[`${table_cell_data[3].contentCell}`]}</TableCell>
                <TableCell align="center">
                    {row.etc !== null ? (
                        <Link href={`/${row.etc}`}>보러가기</Link>
                    ) : (
                        <span>링크 없음</span>
                    )}
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
                count={oneMemberLog.content.length}
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
      </TableContainer>,
    )
  );
}

// side menu 레이아웃
LogDetail.Layout = withAdminAuth(AdminPageLayout);
export default LogDetail;
