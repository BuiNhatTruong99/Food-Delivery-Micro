'use client';

import { useForm } from 'react-hook-form';
import { ResetPasswordSchema, TResetPasswordSchema } from './validSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { useCallback } from 'react';
import { Button, Form, InputField } from '@/components/atom';
import { Box } from '@mui/material';

const ResetPasswordForm = () => {
  const {
    control,
    handleSubmit,
    formState: { errors }
  } = useForm<TResetPasswordSchema>({
    resolver: zodResolver(ResetPasswordSchema),
    mode: 'all'
  });

  const onSubmit = useCallback(() => {}, []);

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
        // loading={isLoading}
        htmlType="submit"
        type="primary"
        size="large"
        // disabled={isLoading}
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
