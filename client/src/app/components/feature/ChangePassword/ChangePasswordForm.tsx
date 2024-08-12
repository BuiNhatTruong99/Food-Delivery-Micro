'use client';

import { Button, Form, InputField } from '@/components/atom';
import { useMessage } from '@/hooks/useMessage';
import { zodResolver } from '@hookform/resolvers/zod';
import { Box } from '@mui/material';
import { useCallback } from 'react';
import { useForm } from 'react-hook-form';
import { ChangePasswordSchema, TChangePasswordSchema } from './validSchema';
import { useRouter, useSearchParams } from 'next/navigation';
import { useChangePasswordMutation } from '@/queries';
import { IApiErrorResponse, IChangePassword } from '@/domain';
import { PATHNAME } from '@/config';

interface IFormValues {
  password: string;
  confirmPassword: string;
}

const ChangePasswordForm = () => {
  const {
    control,
    handleSubmit,
    formState: { errors }
  } = useForm<TChangePasswordSchema>({
    resolver: zodResolver(ChangePasswordSchema),
    mode: 'all'
  });

  const { push } = useRouter();
  const message = useMessage();
  const { mutateAsync, isPending } = useChangePasswordMutation();
  const params = useSearchParams();
  const email = params.get('email');
  const otpString = params.get('otp');
  const otp = otpString ? parseInt(otpString) : 0;

  const onSubmit = useCallback(
    (formValues: IFormValues) => {
      const { password } = formValues;
      const values = { password, email, otp };

      mutateAsync(values, {
        onSuccess: () => {
          message.success('Password changed successfully');
          push(PATHNAME.SIGN_IN);
        },
        onError: (err: IApiErrorResponse) => {
          message.error(err?.message);
        }
      });
    },
    [message, mutateAsync, email, otp, push]
  );

  return (
    <Form onSubmit={handleSubmit(onSubmit)} className="flex gap-8 flex-col">
      <Box>
        <InputField
          name="password"
          className="w-full"
          placeholder="Your password"
          control={control}
          label="Password"
          type="password"
          errorField={errors['password']}
        />
        <InputField
          name="confirmPassword"
          className="w-full"
          placeholder="Confirm Password"
          control={control}
          label="Confirm Password"
          type="password"
          errorField={errors['confirmPassword']}
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
          marginTop: '1.5rem'
        }}
        className="shadow-primaryBtn"
      >
        Change Password
      </Button>
    </Form>
  );
};

export default ChangePasswordForm;
