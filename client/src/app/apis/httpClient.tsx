import { PATHNAME } from '@/config';
import { AUTH_STORAGE_KEY, REFRESH_TOKEN_EXPIRED_MESSAGE } from '@/constant';
import { IApiErrorResponse } from '@/domain';
import { getNewTokenService } from '@/services';
import { useAuthStore } from '@/stores';
import axios, { AxiosError, CreateAxiosDefaults, HttpStatusCode } from 'axios';
import { signOut } from 'next-auth/react';

// Create an instance of axios with optional config
const createHttpClient = (config?: CreateAxiosDefaults) => {
  const client = axios.create({
    baseURL: process.env.NEXT_PUBLIC_API_URL,
    ...config,
    headers: {
      'Content-Type': 'application/json',
      ...(config?.headers || {})
    }
  });

  // Request interceptor to add Authorization header
  client.interceptors.request.use(
    (config) => {
      const { accessToken } = useAuthStore.getState().tokens;
      if (accessToken) {
        config.headers.Authorization = `Bearer ${accessToken}`;
      }

      return config;
    },
    (error: AxiosError) => {
      return Promise.reject(error);
    }
  );

  // Response interceptor to handle errors
  client.interceptors.response.use(
    (response) => {
      return response.data;
    },
    async (error) => {
      if (error.code === 'ERR_NETWORK' || error.code === 'ERR_BAD_RESPONSE') {
        return Promise.reject({
          message: 'Network or server error. Please try again'
        } as IApiErrorResponse);
      }
      if (error.response?.status === HttpStatusCode.Forbidden) {
        localStorage.removeItem(AUTH_STORAGE_KEY);
        return Promise.reject(error.response.data);
      }
      if (
        error.response &&
        error.response?.status === HttpStatusCode.Unauthorized &&
        error.response?.data.message !== REFRESH_TOKEN_EXPIRED_MESSAGE
      ) {
        if (error.config.url.includes(PATHNAME.SIGN_IN)) {
          return Promise.reject(error.response.data || error);
        }
        const {
          tokens: { refreshToken },
          setTokens
        } = useAuthStore.getState();
        try {
          const newTokens = await getNewTokenService(refreshToken!);
          if (newTokens && newTokens.data) {
            setTokens(newTokens.data);
            client.defaults.headers.common[
              'Authorization'
            ] = `Bearer ${newTokens?.data?.accessToken}`;
            return client(error.config);
          }
        } catch (error) {
          console.log(error);
          localStorage.removeItem(AUTH_STORAGE_KEY);
          signOut({ callbackUrl: PATHNAME.SIGN_IN });
          return Promise.reject(error);
        }
      }
      return Promise.reject(error.response.data || error);
    }
  );
  return client;
};

export default createHttpClient;
