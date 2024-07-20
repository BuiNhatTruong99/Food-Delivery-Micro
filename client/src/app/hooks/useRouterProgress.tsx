'use client';

import { usePathname, useSearchParams } from 'next/navigation';
import nProgress from 'nprogress';
import { useEffect } from 'react';

const useRouterProgress = () => {
  const pathname = usePathname();
  const searchParams = useSearchParams();

  useEffect(() => {
    nProgress.done();
    return () => {
      nProgress.start();
    };
  }, [pathname, searchParams]);
};

export default useRouterProgress;
