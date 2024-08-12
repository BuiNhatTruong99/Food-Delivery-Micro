'use client';

import { useForm } from 'react-hook-form';
import { ResetPasswordSchema, TResetPasswordSchema } from './validSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { useCallback } from 'react';
import { Button, Form, InputField } from '@/components/atom';
import { Box } from '@mui/material';
import { useMessage } from '@/hooks/useMessage';
import { useResetPasswordMutation } from '@/queries';
import { IApiErrorResponse, ISendOtp } from '@/domain';

const ResetPasswordForm = () => {
  const {
    control,
    handleSubmit,
    formState: { errors }
  } = useForm<TResetPasswordSchema>({
    resolver: zodResolver(ResetPasswordSchema),
    mode: 'all'
  });

  const { mutateAsync, isPending } = useResetPasswordMutation();
  const message = useMessage();

  const onSubmit = useCallback(
    (values: ISendOtp) => {
      mutateAsync(values, {
        onSuccess: () => {
          message.success('Please check your email');
        },
        onError: (err: IApiErrorResponse) => {
          if (err?.code === 409) {
            message.warning(err?.message);
            return;
          }

          message.error(err?.message);
        }
      });
    },
    [message, mutateAsync]
  );

  return (
    <Form onSubmit={handleSubmit(onSubmit)} className="flex gap-8 flex-col">
      <Box>
        <InputField
          name="email"
          className="w-full"
          placeholder="Your email"
          control={control}
          label="E-mail"
          errorField={errors['email']}
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
        Reset Password
      </Button>
    </Form>
  );
};

export default ResetPasswordForm;
