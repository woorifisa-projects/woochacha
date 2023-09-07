import { useEffect, useState } from 'react';
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
} from '@mui/material';

export default function ApproveModal(props) {
  const [mounted, setMounted] = useState(false);
  const { onClickModal, isOpen, modalContent, callBackOneFunc, callBackTwoFunc, children } = props;

  const handleClickNo = () => {
    onClickModal(false);
    callBackTwoFunc(); // 반려 콜백함수 실행
  };

  const handleClickYes = () => {
    onClickModal(false);
    callBackOneFunc(); // 승인 콜백함수 실행
  };

  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    mounted && (
      <div>
        <Dialog
          maxWidth="sm"
          fullWidth
          open={isOpen}
          onClose={handleClickNo}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description">
          <DialogTitle id="alert-dialog-title">{modalContent.modalTitle}</DialogTitle>
          <DialogContent>
            <DialogContentText id="alert-dialog-description">
              {modalContent.modalContents}
            </DialogContentText>
            {children}
          </DialogContent>
          <DialogActions>
            <Button variant="contained" onClick={handleClickNo} color="error">
              {modalContent.modalNoBtn}
            </Button>
            <Button variant="contained" onClick={handleClickYes} color="primary">
              {modalContent.modalYesBtn}
            </Button>
          </DialogActions>
        </Dialog>
      </div>
    )
  );
}
