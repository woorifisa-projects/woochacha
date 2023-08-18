const { Typography } = require('@mui/material');
import Link from '@mui/material/Link';

export default function Copyright() {
  return (
    <Typography variant="body2" color="text.secondary">
      {'Copyright © '}
      <Link color="inherit" href="https://mui.com/">
        Woochacha
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}
