const VERSION = 'v1';
const PREFIX_PATH = (pathname: string) => `/api/${VERSION}/${pathname}`;

const EndPoints = {
  AUTH: {
    signIn: PREFIX_PATH('identity/auth/sign-in'),
    signUp: PREFIX_PATH('identity/auth/sign-up'),
    sendOtp: PREFIX_PATH('identity/auth/send-otp'),
    resetPassword: PREFIX_PATH('identity/auth/reset-password'),
    verificationEmail: PREFIX_PATH('identity/users/verification-email'),
    changePassword: PREFIX_PATH('identity/auth/change-password'),
    googleSignIn: PREFIX_PATH('identity/auth/google-sign'),
    getNewAccessToken: PREFIX_PATH('identity/auth/new-token')
  },
  USER: {}
};

export default EndPoints;
