import * as React from 'react';
import { Grid, Button, TextField } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import styles from './SearchBar.module.css';

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
      <Grid container alignItems="center" sx={{ position: 'relative' }} gap={2}>
        <Grid item xs>
          <input
            className={styles.textField}
            placeholder="모델 또는 차량명으로 검색해주세요."
            value={keyword}
            onChange={handleInputChange}
          />
        </Grid>
        <Grid item sx={{ position: 'absolute', top: 10, right: 5 }}>
          <SearchIcon sx={{ color: '#727272' }} fontSize="medium" />
        </Grid>
      </Grid>
    </>
  );
}
