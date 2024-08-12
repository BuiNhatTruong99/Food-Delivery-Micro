interface IUserInfo {
  id?: number;
  email?: string;
  role?: string;
  isEmailVerified?: boolean;
}

interface ITokens {
  accessToken?: string;
  refreshToken?: string;
}

interface ISignIn {
  email: string;
  password: string;
}

interface ISignUp {
  full_name: string;
  email: string;
  password: string;
}

interface ISendOtp {
  email?: string;
}

interface IVerificationEmail {
  otp: number;
  userId?: number;
}

interface IChangePassword {
  email: string;
  otp: number;
  password: string;
}

interface IGoogleSignIn {
  token: string;
  provider: string;
}

interface IUserResponse extends ITokens, IUserInfo {}

export type {
  IUserInfo,
  ITokens,
  ISignIn,
  IUserResponse,
  ISignUp,
  ISendOtp,
  IVerificationEmail,
  IChangePassword,
  IGoogleSignIn
};
