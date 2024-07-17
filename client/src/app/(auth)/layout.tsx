'use client';

import { PATHNAME } from '@/config';
import { BackgroundAuthLayout, FooterForAuth, MainLayout } from '@/layout';
import Providers from '@/provider';
import '@styles/globals.css';
import { usePathname } from 'next/navigation';

const FOOTER_FOR_AUTH = [PATHNAME.SIGN_IN, PATHNAME.SIGN_UP];

const AuthLayout = ({ children }: { children: React.ReactNode }) => {
  const pathName = usePathname();
  if (pathName === PATHNAME.ONBOARDING) {
    return <div className="h-[100dvh]">{children}</div>;
  }
  return (
    <html lang="en">
      <body>
        <Providers>
          <MainLayout>
            <div className="h-[100dvh]">
              <BackgroundAuthLayout>{children}</BackgroundAuthLayout>
              {FOOTER_FOR_AUTH.includes(pathName) ? <FooterForAuth /> : null}
            </div>
          </MainLayout>
        </Providers>
      </body>
    </html>
  );
};

export default AuthLayout;
