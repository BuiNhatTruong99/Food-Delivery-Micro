import { AUTH_STORAGE_KEY } from '@/constant';
import { ITokens, IUserInfo } from '@/domain';
import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface IAuthState {
  tokens: ITokens;
  userInfo?: IUserInfo;
  setTokens: (tokens: ITokens) => void;
  setUserInfo: (userInfo: IUserInfo) => void;
  reset: () => void;
}

export const useAuthStore = create<IAuthState>()(
  persist(
    (set) => ({
      tokens: { accessToken: '', refreshToken: '' },
      userInfo: undefined,

      setTokens: (tokens: ITokens) =>
        set((state) => ({
          ...state,
          tokens
        })),

      setUserInfo: (userInfo: IUserInfo) =>
        set((state) => ({
          ...state,
          userInfo
        })),

      reset: () =>
        set(() => ({
          tokens: { accessToken: '', refreshToken: '' },
          userInfo: undefined
        }))
    }),
    { name: AUTH_STORAGE_KEY }
  )
);
