/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,
  cssModules: true,
  cssLoaderOptions: {
    importLoaders: 1,
    localIdentName: '[]',
  },

  images: {
    domains: ['woochacha.s3.ap-northeast-2.amazonaws.com'],
  },

  // rewrite
  async rewrites() {
    return [
      // {
      //   source: '/:path*',
      //   destination: 'http://13.125.32.208:8080/:path*',
      // },
      // {
      //   source: '/test1',
      //   destination: '/test1',
      // },
      // {
      //   source: '/test2',
      //   destination: '/test2',
      // },
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

  async headers() {
    return [
      {
        // matching all API routes
        source: '/api/:path*',
        headers: [
          { key: 'Access-Control-Allow-Credentials', value: 'true' },
          { key: 'Access-Control-Allow-Origin', value: '*' },
          { key: 'Access-Control-Allow-Methods', value: 'GET,OPTIONS,PATCH,DELETE,POST,PUT' },
          {
            key: 'Access-Control-Allow-Headers',
            value:
              'X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5, Content-Type, Date, X-Api-Version',
          },
        ],
      },
    ];
  },
};

module.exports = nextConfig;
