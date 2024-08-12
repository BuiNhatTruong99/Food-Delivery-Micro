import { EndPoints } from '@/apis';
import createHttpClient from '@/apis/httpClient';
import {
  IApiDataResponse,
  IChangePassword,
  IGoogleSignIn,
  ISendOtp,
  ISignIn,
  ISignUp,
  ITokens,
  IUserResponse,
  IVerificationEmail
} from '@/domain';

const httpClient = createHttpClient();

export const signInService = async (
  value: ISignIn
): Promise<IApiDataResponse<IUserResponse>> => {
  const resp = await httpClient.post<ISignIn, IApiDataResponse<IUserResponse>>(
    EndPoints.AUTH.signIn,
    {
      email: value.email,
      password: value.password
    }
  );
  return resp;
};

export const signUpService = async (
  value: ISignUp
): Promise<IApiDataResponse<IUserResponse>> => {
  const resp = await httpClient.post<ISignUp, IApiDataResponse<IUserResponse>>(
    EndPoints.AUTH.signUp,
    {
      fullName: value.full_name,
      email: value.email,
      password: value.password
    }
  );
  return resp;
};

export const sendOtpService = async (
  value: ISendOtp
): Promise<IApiDataResponse<void>> => {
  const resp = await httpClient.post<ISendOtp, IApiDataResponse<void>>(
    EndPoints.AUTH.sendOtp,
    {
      email: value.email
    }
  );
  return resp;
};

export const verifyOtpService = async (
  value: IVerificationEmail
): Promise<IApiDataResponse<IUserResponse>> => {
  const resp = await httpClient.post<ISendOtp, IApiDataResponse<IUserResponse>>(
    EndPoints.AUTH.verificationEmail,
    {
      otp: value.otp,
      userId: value.userId
    }
  );
  return resp;
};

export const resetPasswordService = async (
  value: ISendOtp
): Promise<IApiDataResponse<void>> => {
  const resp = await httpClient.post<ISendOtp, IApiDataResponse<void>>(
    EndPoints.AUTH.resetPassword,
    {
      email: value.email
    }
  );
  return resp;
};

export const changePasswordService = async (
  value: IChangePassword
): Promise<IApiDataResponse<void>> => {
  const resp = await httpClient.patch<IChangePassword, IApiDataResponse<void>>(
    EndPoints.AUTH.changePassword,
    {
      otp: value.otp,
      email: value.email,
      password: value.password
    }
  );
  return resp;
};

export const googleSignInService = async (
  value: IGoogleSignIn
): Promise<IApiDataResponse<IUserResponse>> => {
  const resp = await httpClient.post<
    IGoogleSignIn,
    IApiDataResponse<IUserResponse>
  >(EndPoints.AUTH.googleSignIn, {
    token: value.token,
    provider: value.provider
  });
  return resp;
};

export const getNewTokenService = async (
  value: string
): Promise<IApiDataResponse<ITokens>> => {
  const resp = await httpClient.post<string, IApiDataResponse<ITokens>>(
    EndPoints.AUTH.getNewAccessToken,
    {
      refreshToken: value
    }
  );
  return resp;
};
