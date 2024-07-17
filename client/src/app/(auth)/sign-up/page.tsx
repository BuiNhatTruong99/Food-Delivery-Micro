import { SignUpForm } from '@/components/feature/SignUp';
import { PATHNAME } from '@/config';
import { CustomBackIcon } from '@/layout';
import { Box, Typography } from '@mui/material';
import Link from 'next/link';

const SignUpPage = () => {
  return (
    <>
      <CustomBackIcon type="back" expectPath={PATHNAME.SIGN_IN} />
      <Box className="absolute w-full px-6 pb-6">
        <Box className="mt-28 w-full flex flex-col gap-6 mb-14">
          <Typography
            variant="h1"
            className="w-[247px] h-[40px] text-4xl font-bold"
          >
            Sign Up
          </Typography>
          <SignUpForm />
          <Typography variant="body2" className="font-medium text-center">
            Already have an account?
            <Link href={PATHNAME.SIGN_IN} className="font-bold text-primary">
              {' '}
              Login{' '}
            </Link>
          </Typography>
        </Box>
      </Box>
    </>
  );
};

export default SignUpPage;
