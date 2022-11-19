/** @type {import('next').NextConfig} */
const nextConfig = {
  images: {
    loader: 'imgix',
    path: '',
  },
  reactStrictMode: true,
  swcMinify: true,
}

module.exports = nextConfig
