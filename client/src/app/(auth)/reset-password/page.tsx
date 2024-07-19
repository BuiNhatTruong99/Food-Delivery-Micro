import ResetPasswordForm from '@/components/feature/ResetPassword';
import { Box, Typography } from '@mui/material';

const ResetPasswordPage = () => {
  return (
    <Box className="absolute w-full px-6 pb-6">
      <Box className="mt-40 w-full flex flex-col mb-14">
        <Typography variant="h1" className="h-[40px] mb-3.5">
          Resset Password
        </Typography>
        <Typography
          variant="body2"
          className="w-[247px] h-[40px] mb-10 text-[#9796A1]"
        >
          Please enter your email address to request a password reset
        </Typography>
        <ResetPasswordForm />
      </Box>
    </Box>
  );
};

export default ResetPasswordPage;
