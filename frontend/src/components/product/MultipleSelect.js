import { useState } from 'react';
import { useTheme } from '@mui/material/styles';
import { Box, OutlinedInput, InputLabel, MenuItem, FormControl, Select, Chip } from '@mui/material';

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
    },
  },
};

function getStyles(name, itemName, theme) {
  return {
    fontWeight:
      itemName.indexOf(name) === -1
        ? theme.typography.fontWeightRegular
        : theme.typography.fontWeightMedium,
  };
}

export default function MultipleSelect(props) {
  const { selectMenu, selectItems, onChangeSelect } = props;
  const theme = useTheme();
  const [itemName, setItemName] = useState([]);

  const handleChange = (event, itemId) => {
    const {
      target: { value },
    } = event;
    setItemName(typeof value === 'string' ? value.split(',') : value);
    onChangeSelect(event, itemId);
  };

  const multipleBoxCss = {
    formControl: {
      mt: 1,
      width: '100%',
    },
    selectBox: {
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
    },
    box: { display: 'flex', flexWrap: 'wrap', gap: 0.5 },
  };

  return (
    <FormControl sx={multipleBoxCss.formControl}>
      <InputLabel id="demo-multiple-chip-label">{selectMenu.label}</InputLabel>
      <Select
        labelId="demo-multiple-chip-label"
        id="demo-multiple-chip"
        multiple
        sx={multipleBoxCss.selectBox}
        value={itemName}
        onChange={(e) => handleChange(e, selectMenu.id)}
        input={<OutlinedInput id="select-multiple-chip" label={selectMenu.label} />}
        renderValue={(selected) => (
          <Box sx={multipleBoxCss.box}>
            {selected.map((value) => (
              <Chip
                key={value}
                label={selectItems.find((item) => item.id == value)['name']}
                variant="light"
                color="primary"
                size="small"
              />
            ))}
          </Box>
        )}
        MenuProps={MenuProps}>
        {selectItems.map((itemChip) => (
          <MenuItem
            key={itemChip.id}
            value={itemChip.id}
            style={getStyles(itemChip, itemName, theme)}>
            {itemChip.name}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  );
}
