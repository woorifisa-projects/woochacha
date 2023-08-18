import { Roboto } from 'next/font/google';
import { createTheme } from '@mui/material/styles';
import { red, green } from '@mui/material/colors';

const roboto = Roboto({
  weight: ['300', '400', '500', '700'],
  subsets: ['latin'],
  display: 'swap',
});

// Create a theme instance.
const theme = createTheme({
  palette: {
    primary: {
      light: '#00aefa',
      main: '#007bc3',
      dark: '#005ba1',
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
  },
  typography: {
    fontFamily: roboto.style.fontFamily,
  },
});

export default theme;
