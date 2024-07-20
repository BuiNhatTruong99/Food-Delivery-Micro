import path from 'path';

/** @type {import('next').NextConfig} */
const nextConfig = {
  webpack: (config) => {
    config.resolve.alias = {
      ...config.resolve.alias,
      '@': path.resolve('src/app'),
      '@images': path.resolve('public/images')
    };
    return config;
  },
  images: {
    domains: ['lh3.googleusercontent.com']
  }
};

export default nextConfig;
