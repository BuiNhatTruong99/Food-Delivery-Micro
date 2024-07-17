export const PATH_PUBLIC = {
  ONBOARDING: '/onboarding',
  SIGN_IN: '/sign-in',
  SIGN_UP: '/sign-up',
  RESETPASSWORD: '/reset-password',
  VERIFICATION: '/verify-phone'
};

export const PATH_ROOT = {
  PHONEREGISTRATION: '/phone-registration',
  HOME: '/',
  CONFIRM_EMAIL: '/confirm-email'
};
export const PATHNAME = { ...PATH_PUBLIC, ...PATH_ROOT };
