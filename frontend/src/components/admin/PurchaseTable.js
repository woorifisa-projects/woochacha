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
  Button,
} from '@mui/material';
import FirstPageIcon from '@mui/icons-material/FirstPage';
import KeyboardArrowLeft from '@mui/icons-material/KeyboardArrowLeft';
import KeyboardArrowRight from '@mui/icons-material/KeyboardArrowRight';
import LastPageIcon from '@mui/icons-material/LastPage';
import { useRouter } from 'next/router';
import NoData from '../common/NoData';

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
export default function PurchaseTable(props) {
  const {
    headerData,
    contentData,
    callbackFunc,
    getConfirmData,
    setConfirmFlag,
    moveDetailUrl,
    setCurrentPurchaseId,
    page,
    setPage,
    size,
    setSize,
  } = props;
  const rows = contentData.content;
  const [mounted, setMounted] = useState(false);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const router = useRouter();

  const handleChangePage = (event, newPage) => {
    setPage(newPage); // 현재 page 번호
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
    setSize(event.target.value); // 페이지 크기 업데이트
  };

  const handleMove = (baseUrl, purchaseId) => {
    router.push(`${baseUrl}${purchaseId}`);
  };

  const handleCurrentPurchaseId = (purchaseId) => {
    setCurrentPurchaseId(purchaseId);
  };

  const handleConfirmProduct = (purchaseId) => {
    callbackFunc();
    getConfirmData(purchaseId); // 검토여부 get
    setConfirmFlag(true);
  };

  const handlePurchaseProduct = (purchaseId) => {
    callbackFunc();
    setConfirmFlag(false);
  };

  useEffect(() => {
    setMounted(true);
  }, []);

  const basicButtonTableCss = {
    button: {
      mx: 1,
      '&:hover': {
        boxShadow: 10,
        cursor: 'pointer',
        transition: '0.3s',
        transform: 'scale(1.01)',
      },
    },
  };

  return mounted && rows.length > 0 ? (
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
          {rows.map((row, idx) => (
            <TableRow
              sx={basicButtonTableCss.tableRow}
              key={idx}
              onClick={() => handleCurrentPurchaseId(row.purchaseId)}>
              <TableCell
                sx={basicButtonTableCss.button}
                onClick={() => handleMove(moveDetailUrl, row.productId)}
                align="center">
                {row[`${headerData[0].contentCell}`]}
              </TableCell>
              <TableCell align="center">{row[`${headerData[1].contentCell}`]}</TableCell>
              <TableCell align="center">{row[`${headerData[2].contentCell}`]}</TableCell>
              <TableCell align="center">
                {row[`${headerData[3].contentCell}`] === 1 ? (
                  <Button sx={basicButtonTableCss.button} variant="outlined" disabled>
                    확인
                  </Button>
                ) : (
                  <Button
                    onClick={() => handleConfirmProduct(row.purchaseId)}
                    sx={basicButtonTableCss.button}
                    variant="outlined"
                    color="error">
                    미확인
                  </Button>
                )}
              </TableCell>
              <TableCell align="center">
                {row[`${headerData[4].contentCell}`] === 0 ? (
                  <Button
                    onClick={() => handlePurchaseProduct(row.purchaseId)}
                    sx={basicButtonTableCss.button}
                    variant="outlined">
                    미성사
                  </Button>
                ) : (
                  <Button sx={basicButtonTableCss.button} variant="outlined" color="error" disabled>
                    성사
                  </Button>
                )}
              </TableCell>
            </TableRow>
          ))}
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
  ) : (
    <NoData />
  );
}
