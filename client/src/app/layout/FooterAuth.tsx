'use client';

import { IMAGES_DEFAULT } from '@/constant';
import { Box, ButtonBase, Typography, useTheme } from '@mui/material';
import Image from 'next/image';

const FooterForAuth = () => {
  const theme = useTheme();

  return (
    <Box className="absolute bottom-6 flex flex-col gap-3 px-6 w-full">
      <Box className="flex items-center justify-between">
        <Box className="bg-opacity-50 bg-[#9796A1] w-28 h-[1px]"></Box>
        <Typography variant="body2">Or sign up with</Typography>
        <Box className="bg-opacity-50 bg-[#9796A1] w-28 h-[1px]"></Box>
      </Box>
      <Box className="flex justify-between">
        <ButtonBase
          className={`w-[148px] h-14 rounded-[28px]  p-4 ${
            theme.palette.mode === 'light'
              ? 'shadow-socialBtn'
              : 'bg-white text-black'
          }`}
        >
          <Image
            alt={IMAGES_DEFAULT.auth.facebookIcon.toString()}
            src={IMAGES_DEFAULT.auth.facebookIcon}
            className="w-9 h-9"
          />
          <Typography variant="h6" className="w-24 text-[13px]">
            FACEBOOK
          </Typography>
        </ButtonBase>
        <ButtonBase
          className={`w-[148px] h-14 rounded-[28px]  p-4 ${
            theme.palette.mode === 'light'
              ? 'shadow-socialBtn'
              : 'bg-white text-black'
          }`}
        >
          <Image
            alt={IMAGES_DEFAULT.auth.googleIcon.toString()}
            src={IMAGES_DEFAULT.auth.googleIcon}
            className="w-8 h-8"
          />
          <Typography variant="h6" className="w-24 text-[13px]">
            GOOGLE
          </Typography>
        </ButtonBase>
      </Box>
    </Box>
  );
};

export default FooterForAuth;
