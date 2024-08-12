import {
  IApiDataResponse,
  IApiErrorResponse,
  IChangePassword,
  IGoogleSignIn,
  ISendOtp,
  ISignIn,
  ISignUp,
  IUserResponse,
  IVerificationEmail
} from '@/domain';
import {
  changePasswordService,
  googleSignInService,
  resetPasswordService,
  sendOtpService,
  signInService,
  signUpService,
  verifyOtpService
} from '@/services';
import { useMutation } from '@tanstack/react-query';
import { QUERIES_KEY } from '../key';

export const useSignInMutation = () => {
  return useMutation<IApiDataResponse<IUserResponse>, IApiErrorResponse, any>({
    mutationFn: (value: ISignIn) => signInService(value),
    mutationKey: [QUERIES_KEY.AUTH.SIGN_IN]
  });
};

export const useSignUpMutation = () => {
  return useMutation<IApiDataResponse<IUserResponse>, IApiErrorResponse, any>({
    mutationFn: (value: ISignUp) => signUpService(value),
    mutationKey: [QUERIES_KEY.AUTH.SIGN_UP]
  });
};

export const useSendOtpMutation = () => {
  return useMutation<IApiDataResponse<any>, IApiErrorResponse, any>({
    mutationFn: (value: ISendOtp) => sendOtpService(value),
    mutationKey: [QUERIES_KEY.AUTH.SEND_OTP]
  });
};

export const useVerificationEmailMutation = () => {
  return useMutation<IApiDataResponse<any>, IApiErrorResponse, any>({
    mutationFn: (value: IVerificationEmail) => verifyOtpService(value),
    mutationKey: [QUERIES_KEY.AUTH.VERIFICATION_EMAIL]
  });
};

export const useResetPasswordMutation = () => {
  return useMutation<IApiDataResponse<any>, IApiErrorResponse, any>({
    mutationFn: (value: ISendOtp) => resetPasswordService(value),
    mutationKey: [QUERIES_KEY.AUTH.RESET_PASSWORD]
  });
};

export const useChangePasswordMutation = () => {
  return useMutation<IApiDataResponse<any>, IApiErrorResponse, any>({
    mutationFn: (value: IChangePassword) => changePasswordService(value),
    mutationKey: [QUERIES_KEY.AUTH.CHANGE_PASSWORD]
  });
};

export const useGoogleSignInMutation = () => {
  return useMutation<IApiDataResponse<IUserResponse>, IApiErrorResponse, any>({
    mutationFn: (value: IGoogleSignIn) => googleSignInService(value),
    mutationKey: [QUERIES_KEY.AUTH.GOOGLE_SIGN_IN]
  });
};
