'use client';

import { PageTransition } from '@/components/molecule';
import { PATHNAME } from '@/config';
import useRouterProgress from '@/hooks/useRouterProgress';
import { BackgroundAuth, FooterForAuth } from '@/layouts';
import { Box } from '@mui/material';
import '@styles/globals.css';
import { usePathname } from 'next/navigation';

const FOOTER_FOR_AUTH = [PATHNAME.SIGN_IN, PATHNAME.SIGN_UP];

const AuthLayout = ({ children }: { children: React.ReactNode }) => {
  const pathName = usePathname();
  useRouterProgress();

  if (pathName === PATHNAME.ONBOARDING) {
    return <div className="h-[100dvh]">{children}</div>;
  }
  return (
    <PageTransition>
      <Box className="h-[100dvh]">
        <BackgroundAuth>{children}</BackgroundAuth>
        {FOOTER_FOR_AUTH.includes(pathName) ? <FooterForAuth /> : null}
      </Box>
    </PageTransition>
  );
};

export default AuthLayout;
