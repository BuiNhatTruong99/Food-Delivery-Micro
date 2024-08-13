'use client';

import { useForm } from 'react-hook-form';
import { SignUpSchema, TSignUpSchema } from './validSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { Button, Form, InputField } from '@/components/atom';
import { Box } from '@mui/material';
import { IApiErrorResponse, ISignUp } from '@/domain';
import { useRouter } from 'next/navigation';
import { useSignUpMutation } from '@/queries';
import { useMessage } from '@/hooks/useMessage';
import { useCallback } from 'react';
import { useAuthStore } from '@/stores';
import { PATHNAME } from '@/config';

const SignUpForm = () => {
  const {
    control,
    handleSubmit,
    formState: { errors }
  } = useForm<TSignUpSchema>({
    resolver: zodResolver(SignUpSchema),
    mode: 'all'
  });

  const { push } = useRouter();
  const { mutateAsync, isPending } = useSignUpMutation();
  const { setUserInfo, setTokens } = useAuthStore();
  const message = useMessage();

  const onSubmit = useCallback(
    (value: ISignUp) => {
      mutateAsync(value, {
        onSuccess: (res) => {
          if (res && res?.data) {
            const {
              id,
              email,
              isEmailVerified,
              role,
              fullName,
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
              imageUrl
            });
            setTokens({ accessToken, refreshToken });
          }
          push(PATHNAME.VERIFICATION);
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
          name="full_name"
          className="w-full"
          placeholder="John Doe"
          control={control}
          label="Full name"
          errorField={errors['full_name']}
        />
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
      <Button
        loading={isPending}
        htmlType="submit"
        type="primary"
        size="large"
        disabled={isPending}
        wrapperSx={{
          margin: '0px auto',
          marginTop: '0.5rem'
        }}
        className="shadow-primaryBtn"
      >
        SIGN UP
      </Button>
    </Form>
  );
};

export default SignUpForm;
