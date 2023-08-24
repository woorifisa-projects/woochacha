import React from 'react';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';

const SelectBox = (props) => {
  const { selectMenu, selectMenuValue, onChangeSelect, selectMenuItems } = props;

  return (
    <FormControl fullWidth>
      <InputLabel id={selectMenu.id}>{selectMenu.label}</InputLabel>
      <Select
        labelId={selectMenu.id}
        id={selectMenu.id}
        value={selectMenuValue}
        label={selectMenu.label}
        onChange={onChangeSelect}>
        {selectMenuItems.map((item) => {
          return (
            <MenuItem value={item.value} key={item.value}>
              {item.content}
            </MenuItem>
          );
        })}
      </Select>
    </FormControl>
  );
};

export default SelectBox;
