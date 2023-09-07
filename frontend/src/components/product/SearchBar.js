import * as React from 'react';
import { Grid, Button, TextField } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';

export default function SearchBar({ onSearch }) {
  const [keyword, setKeyword] = React.useState('');

  const handleInputChange = (event) => {
    setKeyword(event.target.value);
    onSearch(event.target.value);
  };

  const handleSearchClick = () => {
    onSearch(keyword);
  };

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
            value={keyword}
            onChange={handleInputChange}
            variant="standard"
          />
        </Grid>
      </Grid>
    </>
  );
}