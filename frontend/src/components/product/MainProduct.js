import Link from 'next/link';
import PropTypes from 'prop-types';
import { Paper, Typography, Grid, Box } from '@mui/material';

function MainProduct(props) {
  const { post } = props;

  return (
    <Paper
      sx={{
        position: 'relative',
        color: '#fff',
        mb: 4,
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'center',
        backgroundImage: `url(/assets/images/woochacha-ad01.svg)`,
      }}>
      {/* Increase the priority of the hero background image */}
      {<img style={{ display: 'none' }} src={post.image} alt={post.imageText} />}
      <Box
        sx={{
          position: 'absolute',
          top: 0,
          bottom: 0,
          right: 0,
          left: 0,
        }}
      />
      <Grid container justifyContent="flex-end" alignItems="flex-end">
        <Grid item md={8} justifyContent="flex-end" alignItems="flex-end">
          <Box
            sx={{
              position: 'relative',
              p: { xs: 4, md: 8 },
              pr: { md: 0 },
              color: '#FFF',
              fontWeight: 'bold',
            }}>
            <Typography component="h3" variant="h3" color="inherit" gutterBottom>
              {post.title}
            </Typography>
            <Typography component="h5" variant="h5" color="inherit" paragraph>
              {post.description}
            </Typography>
            <Link variant="subtitle1" color="inherit" href="/capitals">
              {post.linkText}
            </Link>
          </Box>
        </Grid>
      </Grid>
    </Paper>
  );
}

MainProduct.propTypes = {
  post: PropTypes.shape({
    description: PropTypes.string.isRequired,
    image: PropTypes.string.isRequired,
    imageText: PropTypes.string.isRequired,
    linkText: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
  }).isRequired,
};

export default MainProduct;
