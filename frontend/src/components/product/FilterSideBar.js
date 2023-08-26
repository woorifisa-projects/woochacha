import { useEffect, useState } from 'react';
import {
  Container,
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Typography,
  Box,
  FormGroup,
  FormControlLabel,
  Checkbox,
  Button,
} from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

export default function FilterSideBar() {
  const [mounted, setMounted] = useState(false);
  const [expanded, setExpanded] = useState(false);

  // data 불러온 이후 필터링 data에 맞게 렌더링
  useEffect(() => {
    setMounted(true);
  }, []);

  const handleChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };

  // TODO: API 통신 이후, 수정할 data입니다.
  const dummySelectData = [
    {
      accordionId: 1,
      accordionPanel: 'panel1',
      typeTitle: '차종',
      selectItems: [
        {
          id: 1,
          carType: '경차',
        },
        {
          id: 2,
          carType: '소형',
        },
        {
          id: 3,
          carType: '준중형',
        },
      ],
    },
    {
      accordionId: 2,
      accordionPanel: 'panel2',
      typeTitle: '색상',
      selectItems: [
        {
          id: 1,
          carType: '빨강',
        },
        {
          id: 2,
          carType: '주황',
        },
        {
          id: 3,
          carType: '노랑',
        },
      ],
    },
    {
      accordionId: 3,
      accordionPanel: 'panel3',
      typeTitle: '차고지',
      selectItems: [
        {
          id: 1,
          carType: '경차',
        },
        {
          id: 2,
          carType: '소형',
        },
        {
          id: 3,
          carType: '준중형',
        },
      ],
    },
  ];

  const filteringCss = {
    container: {
      backgroundColor: '#FFF',
      width: '20%',
      minWidth: '200px',
      height: '80%',
      boxShadow: 3,
      padding: 3,
      borderRadius: '1rem',
    },
    accordionTitle: {
      width: '50%',
      flexShrink: 1,
    },
    filteringBtn: {
      fontSize: '1.1rem',
      my: 5,
    },
  };

  return (
    mounted && (
      <Container sx={filteringCss.container}>
        <Typography variant="h5" component="h5" mb={5}>
          필터링
        </Typography>
        <Box>
          {dummySelectData.map((selectItem) => {
            return (
              <Accordion
                key={selectItem.accordionId}
                expanded={expanded === selectItem.accordionPanel}
                onChange={handleChange(selectItem.accordionPanel)}>
                <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                  <Typography sx={filteringCss.accordionTitle}>{selectItem.typeTitle}</Typography>
                </AccordionSummary>
                <AccordionDetails>
                  <Typography>
                    {/* select box 모음 */}
                    <FormGroup>
                      {selectItem.selectItems.map((item) => {
                        return (
                          <FormControlLabel
                            key={item.id}
                            control={<Checkbox value={item.id} />}
                            label={item.carType}
                          />
                        );
                      })}
                    </FormGroup>
                  </Typography>
                </AccordionDetails>
              </Accordion>
            );
          })}
        </Box>
        <Button variant="contained" fullWidth sx={filteringCss.filteringBtn}>
          보러가기
        </Button>
      </Container>
    )
  );
}
