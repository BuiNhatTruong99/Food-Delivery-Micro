import { getNewTokenService } from '@/services';
import { HttpStatusCode } from 'axios';

interface IAccessToken {
  role: string;
  sub: string;
  iat: number;
  exp: number;
}

export const isTokenExpired = (token: string) => {
  try {
    // const { exp } = jwtDecode<IAccessToken>(token);
    // if (!exp) return true;

    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(
      window
        .atob(base64)
        .split('')
        .map(function (c) {
          return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        })
        .join('')
    );

    const { exp } = JSON.parse(jsonPayload);
    const currentTime = Date.now();
    return currentTime > exp * 1000;
  } catch (error) {
    return error;
  }
};

export const getNewToken = async (refreshToken: string) => {
  try {
    const response = await getNewTokenService(refreshToken);
    if (response && response?.code === HttpStatusCode.Unauthorized) {
      return Promise.reject(response);
    }
    return response;
  } catch (error) {
    console.log(error);
    throw error;
  }
};

// if (error.code === 'ERR_NETWORK' || error.code === 'ERR_BAD_RESPONSE') {
//   return Promise.reject(error?.response?.data);
// }
// if (error.response?.status === 401 || error.response?.status === 403) {
//   localStorage.removeItem(AUTH_STORAGE_KEY);
//   return Promise.reject(error.response.data);
// }
// return Promise.reject(error.response.data || error);
