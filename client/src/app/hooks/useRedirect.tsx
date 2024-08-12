import { PATHNAME } from '@/config';
import { IUserInfo } from '@/domain';
import { useRouter } from 'next/navigation';
import { useCallback } from 'react';

const useRedirect = () => {
  const { push } = useRouter();

  const onRedirect = useCallback(
    (user: IUserInfo) => {
      if (!user.isEmailVerified) {
        push(PATHNAME.VERIFICATION);
        return;
      }
    },
    [push]
  );

  return { onRedirect };
};

export default useRedirect;
