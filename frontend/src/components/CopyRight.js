import { useRouter } from 'next/router';
const { Typography } = require('@mui/material');
import Link from '@mui/material/Link';

export default function Copyright() {
  const router = useRouter();
  const moveHome = () => {
    router.push('/');
  };

  return (
    <Typography variant="body2" color="text.secondary">
      {'Copyright © '}
      <Link color="inherit" onClick={moveHome}>
        Woochacha
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}
