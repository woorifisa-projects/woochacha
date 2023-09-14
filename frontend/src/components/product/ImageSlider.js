import { useState } from 'react';
import { useTheme } from '@mui/material/styles';
import { Box, MobileStepper, Button } from '@mui/material';
import KeyboardArrowLeft from '@mui/icons-material/KeyboardArrowLeft';
import KeyboardArrowRight from '@mui/icons-material/KeyboardArrowRight';

export default function ImageSlider(props) {
  const { image } = props;
  console.log(image);
  const theme = useTheme();
  const [activeStep, setActiveStep] = useState(0);
  const maxSteps = image.length;

  const handleNext = () => {
    setActiveStep((prevActiveStep) => prevActiveStep + 1);
  };

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };

  const imageSliderCss = {
    box: {
      mt: 1,
      height: '42rem',
      display: 'block',
      overflow: 'hidden',
      width: '76rem',
      borderRadius: '15px',
    },
    mobileStepper: {
      display: 'flex',
      alignItems: 'center',
      height: 50,
      width: '37rem',
      pl: 2,
      bgcolor: 'rgb(255,255,255,0.3)',
    },
  };

  return (
    <Box sx={{ maxWidth: 700, flexGrow: 1, marginLeft: 2}}>
      <Box component="img" sx={imageSliderCss.box} src={image[activeStep]} />
      <MobileStepper
        sx={imageSliderCss.mobileStepper}
        variant="text"
        steps={maxSteps}
        position="static"
        activeStep={activeStep}
        nextButton={
          <Button size="small" onClick={handleNext} disabled={activeStep === maxSteps - 1}>
            Next
            {theme.direction === 'rtl' ? <KeyboardArrowLeft /> : <KeyboardArrowRight />}
          </Button>
        }
        backButton={
          <Button size="small" onClick={handleBack} disabled={activeStep === 0}>
            {theme.direction === 'rtl' ? <KeyboardArrowRight /> : <KeyboardArrowLeft />}
            Back
          </Button>
        }
      />
    </Box>
  );
}
