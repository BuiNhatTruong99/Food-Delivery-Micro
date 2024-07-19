import { Box, Typography } from '@mui/material';
import { CodeVerificationInput } from '@/components/feature/VerificationCode';
import { CustomBackIcon } from '@/layouts';
import { PATHNAME } from '@/config';

const VerifyCodePage = () => {
  return (
    <>
      <CustomBackIcon type="back" expectPath={PATHNAME.SIGN_IN} />
      <Box className="absolute w-full px-6 pb-6">
        <Box className="mt-40 w-full flex flex-col gap-10">
          <Box className="flex flex-col gap-2">
            <Typography variant="h1" className="h-[40px]">
              Verification Code
            </Typography>
            <Typography
              variant="body2"
              className="w-[247px] h-[40px] text-grayLight dark:text-grayDark"
            >
              Please type the verification code sent to prelookstudio@gmail.com
            </Typography>
          </Box>

          <CodeVerificationInput />
        </Box>
      </Box>
    </>
  );
};

export default VerifyCodePage;
