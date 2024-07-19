export const PATH_PUBLIC = {
  ONBOARDING: '/onboarding',
  SIGN_IN: '/sign-in',
  SIGN_UP: '/sign-up',
  RESETPASSWORD: '/reset-password',
  VERIFICATION: '/verification-code'
};

export const PATH_ROOT = {
  HOME: '/',
  CONFIRM_EMAIL: '/confirm-email'
};
export const PATHNAME = { ...PATH_PUBLIC, ...PATH_ROOT };
