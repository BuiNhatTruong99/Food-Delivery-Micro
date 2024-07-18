'use client';

import NProgress from 'nprogress';
import { usePathname, useSearchParams } from 'next/navigation';
import { useEffect } from 'react';

const useRouterProgress = () => {
  const pathname = usePathname();
  const searchParams = useSearchParams();

  useEffect(() => {
    NProgress.done();
    return () => {
      NProgress.start();
    };
  }, [pathname, searchParams]);
};

export default useRouterProgress;
