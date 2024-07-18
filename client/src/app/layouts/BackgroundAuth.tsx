import { IMAGES_DEFAULT } from '@/constant';
import { Box } from '@mui/material';
import Image from 'next/image';
import { FC, ReactNode } from 'react';

const BackgroundAuth: FC<{ children: ReactNode }> = ({ children }) => {
  return (
    <Box className="w-full relative h-[100dvh]">
      <Box className="w-full relative">
        <Image
          width={60}
          src={IMAGES_DEFAULT.auth.background1}
          alt={IMAGES_DEFAULT.auth.background1.toString()}
          className="absolute top-[-5px] left-0 z-1"
        />
        <Image
          width={184}
          src={IMAGES_DEFAULT.auth.background2}
          alt={IMAGES_DEFAULT.auth.background2.toString()}
          className="absolute top-0 left-0 z-2"
        />
        <Image
          width={84}
          src={IMAGES_DEFAULT.auth.background3}
          alt={IMAGES_DEFAULT.auth.background3.toString()}
          className="absolute top-0 right-0"
        />
      </Box>
      {children}
    </Box>
  );
};

export default BackgroundAuth;
