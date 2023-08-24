import * as React from 'react';
import { Grid, Button, TextField } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';

export default function SearchBar() {
  return (
    <>
      <Grid container spacing={2} alignItems="center">
        <Grid item>
          <SearchIcon color="inherit" sx={{ display: 'block' }} />
        </Grid>
        <Grid item xs>
          <TextField
            fullWidth
            placeholder="검색할 차량명을 입력해주세요."
            InputProps={{
              disableUnderline: true,
              sx: {
                fontSize: 'default',
                backgroundColor: '#fff',
                opacity: 0.7,
                borderRadius: 1,
                p: 1,
                color: '#000',
              },
            }}
            variant="standard"
          />
        </Grid>
        <Grid item>
          <Button color="inherit" variant="contained" sx={{ mr: 1 }}>
            검색하기
          </Button>
        </Grid>
      </Grid>
    </>
  );
}
