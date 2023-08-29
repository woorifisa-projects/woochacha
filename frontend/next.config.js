/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,

  // rewrite
  async rewrites() {
    return [
      {
        source: '/:path*',
        destination: 'http://13.125.32.208:8080/:path*',
      },
      {
        source: '/test1',
        destination: '/test1',
      },
      {
        source: '/test2',
        destination: '/test2',
      },
    ];
  },

  // redirect
  async redirects() {
    return [
      {
        source: '/test3',
        destination: '/test3',
        permanent: false,
      },
      {
        source: '/test4',
        destination: '/test4',
        permanent: false,
      },
    ];
  },
  // build시, eslint ignore
  eslint: {
    ignoreDuringBuilds: true,
  },
};

module.exports = nextConfig;
