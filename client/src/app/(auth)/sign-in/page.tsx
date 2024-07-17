import { SignInForm } from '@/components/feature/SignIn';
import { PATHNAME } from '@/config';
import { Box, Typography } from '@mui/material';
import Link from 'next/link';

const SignInPage = () => {
  return (
    <Box className="absolute w-full px-6 pb-6">
      <Box className="mt-36 w-full flex flex-col gap-7 mb-14">
        <Typography
          variant="h1"
          className="w-[247px] h-[40px] text-4xl font-bold"
        >
          Login
        </Typography>
        <SignInForm />
        <Typography variant="body2" className="font-medium text-center">
          Don’t have an account?
          <Link href={PATHNAME.SIGN_UP} className="font-bold text-primary">
            {' '}
            Sign Up{' '}
          </Link>
        </Typography>
      </Box>
    </Box>
  );
};

export default SignInPage;
