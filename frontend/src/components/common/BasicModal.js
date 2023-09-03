import { useEffect, useState } from 'react';
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
} from '@mui/material';

export default function BasicModal(props) {
  const [mounted, setMounted] = useState(false);
  const { onClickModal, isOpen, modalContent, callBackFunc, children } = props;

  const handleClickNo = () => {
    onClickModal(false);
  };

  const handleClickYes = () => {
    onClickModal(false);
    callBackFunc(); // 콜백함수 실행
  };

  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    mounted && (
      <div>
        <Dialog
          maxWidth="xs"
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
