import { useState } from 'react';
import { useTheme } from '@mui/material/styles';
import { Box, MobileStepper, Button } from '@mui/material';
import KeyboardArrowLeft from '@mui/icons-material/KeyboardArrowLeft';
import KeyboardArrowRight from '@mui/icons-material/KeyboardArrowRight';

export default function DetailImageSlider(props) {
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
    sliderBox: {
      mt: 1,
      height: '300px', // 고정된 높이로 설정
      width: '400px', // 고정된 너비로 설정
    },
    box: {
      mt: 1,
      height: '100%',
      display: 'block',
      overflow: 'hidden',
      width: '100%',
      borderRadius: '3px',
    },
    img: {
      width: '100%', // 이미지 크기를 100%로 설정하여 부모 요소에 맞게 조절
      height: '100%', // 이미지 크기를 100%로 설정하여 부모 요소에 맞게 조절
      objectFit: 'cover', // 이미지를 부모 요소에 맞게 자르고 크기를 맞춤
    },
    mobileStepper: {
      display: 'flex',
      alignItems: 'center',
      height: 50,
      width: '100%',
      pl: 2,
      bgcolor: 'rgb(255,255,255,0.3)',
    },
  };

  return (
    <Box sx={imageSliderCss.sliderBox}>
      <Box sx={imageSliderCss.box}>
        <img src={image[activeStep]} alt={`Step ${activeStep}`} style={imageSliderCss.img} />
      </Box>
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
