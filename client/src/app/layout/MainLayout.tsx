'use client';

import { SplashScreen } from '@/components/molecule';
import { PATH_PUBLIC, PATHNAME } from '@/config';
import { IMAGES_DEFAULT } from '@/constant';
import useScreenMode from '@/hooks/useScreenMode';
import { IconButton } from '@mui/material';
import Image from 'next/image';
import { usePathname, useRouter } from 'next/navigation';
import { useCallback, useMemo, useState } from 'react';

const PATH_CONTAINS_SPLASH_SCREEN = [
  PATHNAME.HOME,
  ...Object.values(PATH_PUBLIC)
];

const useLayout = () => {
  const pathName = usePathname();
  const [isLoading, setIsLoading] = useState(true);
  const isSplash = PATH_CONTAINS_SPLASH_SCREEN.includes(pathName);

  const finishLoading = () => {
    setIsLoading(false);
  };

  return {
    finishLoading,
    isSplash,
    isLoading
  };
};

const MainLayout = ({ children }: { children: React.ReactNode }) => {
  const { isMobile } = useScreenMode();
  const { finishLoading, isSplash, isLoading } = useLayout();

  return (
    <div
      className={`w-full mx-auto min-h-screen relative  ${
        isMobile ? '' : 'max-w-[23.4375rem]'
      }`}
    >
      {isLoading && isSplash ? (
        <SplashScreen finishLoading={finishLoading} />
      ) : (
        children
      )}
    </div>
  );
};

export default MainLayout;

export const CustomBackIcon = ({
  expectPath,
  type
}: {
  expectPath?: string;
  type: 'sign-out' | 'back';
}) => {
  const { push } = useRouter();
  // const { reset } = useAuthStore();

  const onExit = useCallback(() => {
    // reset();
    push(PATHNAME.SIGN_IN);
  }, [push]);

  const renderByType = useMemo(
    () => ({
      'sign-out': (
        <IconButton
          onClick={onExit}
          className="absolute text-sm z-[99] bg-white h-[36px] rounded-[12px] top-12 left-6 shadow-[0_8px_30px_rgb(0,0,0,0.12)] hover:bg-primary hover:text-white"
        >
          Exit
        </IconButton>
      ),
      back: (
        <IconButton
          onClick={() => push(expectPath as string)}
          className="absolute z-[99] bg-white w-[36px] h-[36px] rounded-[12px] top-12 left-6 shadow-[0_8px_30px_rgb(0,0,0,0.12)] hover:bg-primary hover:text-white"
        >
          <Image alt="" src={IMAGES_DEFAULT.auth.backBtn} width={5} />
        </IconButton>
      )
    }),
    [expectPath, onExit, push]
  );
  return renderByType[type];
};
