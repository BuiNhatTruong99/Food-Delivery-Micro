'use client';

import { useForm } from 'react-hook-form';
import { SignUpSchema, TSignUpSchema } from './validSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { Button, Form, InputField } from '@/components/atom';
import { Box } from '@mui/material';

const SignUpForm = () => {
  const {
    control,
    handleSubmit,
    formState: { errors }
  } = useForm<TSignUpSchema>({
    resolver: zodResolver(SignUpSchema),
    mode: 'all'
  });

  const onSubmit = () => {};
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
        // loading={isLoading}
        htmlType="submit"
        type="primary"
        size="large"
        // disabled={isLoading}
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
