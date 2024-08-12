import { useEffect, useCallback } from 'react';
import { useRouter, usePathname } from 'next/navigation';
import { clientStorage, useAuthStore } from '@/stores';
import useRedirect from './useRedirect';
import { ONBOARDING_STORAGE_KEY } from '@/constant';
import { PATHNAME } from '@/config';

const useAuthenticated = () => {
  const { push } = useRouter();
  const { userInfo } = useAuthStore();
  const { onRedirect } = useRedirect();
  const pathname = usePathname();

  const redirectToHomeIfAuthenticated = useCallback(() => {
    const redirectPaths = [PATHNAME.SIGN_IN, PATHNAME.SIGN_UP];
    if (
      userInfo &&
      userInfo.isEmailVerified &&
      redirectPaths.includes(pathname)
    ) {
      push(PATHNAME.HOME);
      return true;
    }
    if (
      userInfo?.isEmailVerified === true &&
      pathname === PATHNAME.VERIFICATION
    ) {
      push(PATHNAME.HOME);
      return true;
    }
    return false;
  }, [userInfo, pathname, push]);

  const redirectToSignInIfUnauthenticated = useCallback(() => {
    if (!userInfo && pathname === PATHNAME.HOME) {
      push(PATHNAME.SIGN_IN);
      return true;
    }
    return false;
  }, [userInfo, pathname, push]);

  const redirectToOnboardingIfFirstTime = useCallback(() => {
    const isOnBoarding = clientStorage.get(ONBOARDING_STORAGE_KEY);
    if (!isOnBoarding) {
      push(PATHNAME.ONBOARDING);
      return true;
    }
    return false;
  }, [push]);

  useEffect(() => {
    if (redirectToHomeIfAuthenticated()) return;
    if (redirectToSignInIfUnauthenticated()) return;
    if (redirectToOnboardingIfFirstTime()) return;

    if (userInfo) {
      onRedirect(userInfo);
    }
  }, [
    userInfo,
    onRedirect,
    push,
    pathname,
    redirectToHomeIfAuthenticated,
    redirectToSignInIfUnauthenticated,
    redirectToOnboardingIfFirstTime
  ]);
};

export default useAuthenticated;
