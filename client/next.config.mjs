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
  }
};

export default nextConfig;
