'use client';

import { useForm } from 'react-hook-form';
import { SignInSchema, TSignInSchema } from './validSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { Box, Button as ButtonMUI, Typography } from '@mui/material';
import { Button, Form, InputField } from '@/components/atom';

const SignInForm = () => {
  const {
    control,
    handleSubmit,
    formState: { errors }
  } = useForm<TSignInSchema>({
    resolver: zodResolver(SignInSchema),
    mode: 'all'
  });

  const onSubmit = () => {};

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
        <ButtonMUI className="p-0 max-w-fit">
          <Typography
            variant="body2"
            fontWeight={600}
            sx={{
              textTransform: 'none'
            }}
          >
            Forgot password?
          </Typography>
        </ButtonMUI>
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
        LOGIN
      </Button>
    </Form>
  );
};

export default SignInForm;
