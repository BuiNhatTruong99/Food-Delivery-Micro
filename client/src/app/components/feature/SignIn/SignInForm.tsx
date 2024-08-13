'use client';

import { useForm } from 'react-hook-form';
import { SignInSchema, TSignInSchema } from './validSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { Box, Typography } from '@mui/material';
import { Button, Form, InputField } from '@/components/atom';
import Link from 'next/link';
import { PATHNAME } from '@/config';
import { useCallback } from 'react';
import { IApiErrorResponse, ISignIn } from '@/domain';
import { useSignInMutation } from '@/queries';
import { useAuthStore } from '@/stores';
import { useRouter } from 'next/navigation';
import { useMessage } from '@/hooks/useMessage';

const SignInForm = () => {
  const {
    control,
    handleSubmit,
    formState: { errors }
  } = useForm<TSignInSchema>({
    resolver: zodResolver(SignInSchema),
    mode: 'all'
  });

  const { push } = useRouter();
  const { mutateAsync, isPending } = useSignInMutation();
  const { setUserInfo, setTokens } = useAuthStore();
  const message = useMessage();

  const onSubmit = useCallback(
    (value: ISignIn) => {
      mutateAsync(value, {
        onSuccess: (res) => {
          if (res && res?.data) {
            const {
              id,
              email,
              isEmailVerified,
              role,
              fullName,
              addresses,
              imageUrl,
              accessToken,
              refreshToken
            } = res.data;
            setUserInfo({
              id,
              email,
              isEmailVerified,
              role,
              fullName,
              imageUrl,
              addresses
            });
            setTokens({ accessToken, refreshToken });
            push(PATHNAME.HOME);
          }
        },
        onError: (err: IApiErrorResponse) => {
          message.error(err?.message);
        }
      });
    },
    [mutateAsync, message, setUserInfo, setTokens, push]
  );

  return (
    <Form onSubmit={handleSubmit(onSubmit)} className="flex gap-0 flex-col">
      <Box>
        <InputField
          name="email"
          className="w-full"
          placeholder="Your email"
          control={control}
          label="E-mail"
          errorField={errors['email']}
        />
        <InputField
          name="password"
          control={control}
          className="w-full"
          placeholder="Password"
          type="password"
          label="Password"
          errorField={errors['password']}
        />
      </Box>
      <Box className="text-right">
        <Link href={PATHNAME.RESETPASSWORD} className="p-0 max-w-fit">
          <Typography
            variant="body2"
            fontWeight={600}
            sx={{
              textTransform: 'none'
            }}
          >
            Forgot password?
          </Typography>
        </Link>
      </Box>
      <Button
        loading={isPending}
        htmlType="submit"
        type="primary"
        size="large"
        disabled={isPending}
        wrapperSx={{
          margin: '0px auto',
          marginTop: '1.5rem'
        }}
        className="shadow-primaryBtn"
      >
        LOGIN
      </Button>
    </Form>
  );
};

export default SignInForm;
