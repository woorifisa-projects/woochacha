// import { Roboto } from 'next/font/google';
import { createTheme } from '@mui/material/styles';
import { red, green } from '@mui/material/colors';

// const roboto = Roboto({
//   weight: ['300', '400', '500', '700'],
//   subsets: ['latin'],
//   display: 'swap',
// });

// Create a theme instance.
const theme = createTheme({
  palette: {
    primary: {
      light: '#00aefa',
      main: '#F95700',
      dark: '#DF4000',
      contrastText: '#fff',
    },
    secondary: {
      light: green[400],
      main: green.A700,
      dark: green[900],
      contrastText: '#fff',
    },
    error: {
      light: red[400],
      main: red.A700,
      dark: red[900],
      contrastText: '#fff',
    },
    neutral: {
      light: '#FFCACA',
      main: '#90a4ae',
      dark: '#37474f',
      contrastText: '#ffffff',
    },
    chipRed: {
      main: '#ffcdd2',
      contrastText: '#000000',
    },
    chipYellow: {
      main: '#fff9c4',
      contrastText: '#000000',
    },
    chipBlue: {
      main: '#e1f5fe',
      contrastText: '#000000',
    },
  },

  breakpoints: {
    values: {
      xs: 0,
      sm: 600,
      md: 900,
      lg: 1200,
      xl: 1536,
    },
  },

  typography: {
    fontFamily:
      "'NanumGothic', 'NanumSquareRoundLight', 'NanumSquareRound','NanumSquareRoundBold','NanumSquareRoundExtraBold'",
  },
});

export default theme;
