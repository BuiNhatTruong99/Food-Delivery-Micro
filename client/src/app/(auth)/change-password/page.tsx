import ChangePasswordForm from '@/components/feature/ChangePassword';
import { PATHNAME } from '@/config';
import { CustomBackIcon } from '@/layouts';
import { Box, Typography } from '@mui/material';

const ChangePasswordPage = () => {
  return (
    <>
      <CustomBackIcon type="back" expectPath={PATHNAME.SIGN_IN} />
      <Box className="absolute w-full px-6 pb-6">
        <Box className="mt-40 w-full flex flex-col mb-14">
          <Typography variant="h1" className="h-[40px] mb-3.5">
            Change Password
          </Typography>
          <ChangePasswordForm />
        </Box>
      </Box>
    </>
  );
};

export default ChangePasswordPage;
