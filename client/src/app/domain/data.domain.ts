interface IApiDataResponse<T> {
  code: number;
  message?: string;
  data?: T;
}

interface IApiErrorResponse {
  code: number;
  message: string;
}

export type { IApiDataResponse, IApiErrorResponse };
