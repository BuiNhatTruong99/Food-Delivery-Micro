'use client';

import { PATHNAME } from '@/config';
import { IMAGES_DEFAULT } from '@/constant';
import { IGoogleSignIn } from '@/domain';
import { useMessage } from '@/hooks/useMessage';
import { googleSignInService } from '@/services';
import { useAuthStore } from '@/stores';
import { Box, ButtonBase, Typography, useTheme } from '@mui/material';
import { signIn, useSession } from 'next-auth/react';
import Image from 'next/image';
import { useCallback, useEffect, useRef } from 'react';

const FooterForAuth = () => {
  const theme = useTheme();
  const { data: session, status } = useSession();
  const { setTokens, setUserInfo } = useAuthStore();
  const message = useMessage();
  const _executed = useRef(false);

  const handleSignIn = useCallback(
    async (value: IGoogleSignIn) => {
      try {
        const resp = await googleSignInService(value);
        if (resp?.data) {
          const {
            id,
            email,
            isEmailVerified,
            role,
            accessToken,
            refreshToken
          } = resp.data;
          setUserInfo({ id, email, isEmailVerified, role });
          setTokens({ accessToken, refreshToken });
          message.success('Successfully signed in with Google.');
        } else {
          message.error('Failed to sign in with Google.');
        }
      } catch (error) {
        message.error((error as Error).message);
      }
    },
    [message, setTokens, setUserInfo]
  );

  useEffect(() => {
    if (!_executed.current) {
      if (
        session?.user &&
        session?.user?.accessToken &&
        status === 'authenticated'
      ) {
        const {
          userId,
          userEmail,
          isEmailVerified,
          role,
          accessToken,
          refreshToken
        } = session.user;
        setTokens({
          accessToken: accessToken,
          refreshToken: refreshToken
        });
        setUserInfo({
          id: userId,
          email: userEmail,
          isEmailVerified,
          role
        });
        message.success('Successfully signed in with Google.');
      }
      _executed.current = true;
    }
  }, [setTokens, session, status, message, setUserInfo]);

  const handleGoogleSignIn = async () => {
    await signIn('google', { callbackUrl: PATHNAME.HOME });
  };

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
          onClick={handleGoogleSignIn}
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
