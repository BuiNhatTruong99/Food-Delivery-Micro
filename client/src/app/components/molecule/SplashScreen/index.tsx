'use client';

import { IconLogo } from '@/components/atom';
import { IMAGES_DEFAULT } from '@/constant';
import Image from 'next/image';
import React, { FC, useEffect } from 'react';

interface SplashScreenProps {
  finishLoading: () => void;
}

const SplashScreen: FC<SplashScreenProps> = ({ finishLoading }) => {
  useEffect(() => {
    const timerSplash = setTimeout(() => {
      finishLoading();
    }, 1500); // 1.5s

    return () => {
      clearTimeout(timerSplash);
    };
  }, [finishLoading]);

  return (
    <div className="w-full bg-primary h-[100dvh] flex items-center justify-center flex-col">
      <IconLogo sx={{ fontSize: '8.5rem' }} className="animate-scale" />
      <Image
        width={182}
        height={60}
        src={IMAGES_DEFAULT.splash.title}
        alt="image logo"
      />
    </div>
  );
};

export default SplashScreen;
