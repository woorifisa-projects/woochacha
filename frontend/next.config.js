/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,

  // rewrite
  async rewrites() {
    return [
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
};

module.exports = nextConfig;
