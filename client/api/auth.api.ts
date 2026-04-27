import { axiosInstance, tokenStorage } from "@/lib/axios";
import type {
  AuthLoginRequest,
  AuthRegisterRequest,
} from "@/types/request";
import type { AuthResponse, AuthUserResponse } from "@/types/response";

export const authApi = {
  async register(payload: AuthRegisterRequest): Promise<AuthResponse> {
    const response = await axiosInstance.post<AuthResponse>("/auth/register", payload);
    if (response.data?.accessToken) {
      tokenStorage.set(response.data.accessToken);
    }
    return response.data;
  },

  async login(payload: AuthLoginRequest): Promise<AuthResponse> {
    const response = await axiosInstance.post<AuthResponse>("/auth/login", payload);
    if (response.data?.accessToken) {
      tokenStorage.set(response.data.accessToken);
    }
    return response.data;
  },

  async refresh(): Promise<AuthResponse> {
    const response = await axiosInstance.post<AuthResponse>("/auth/refresh");
    if (response.data?.accessToken) {
      tokenStorage.set(response.data.accessToken);
    }
    return response.data;
  },

  async logout(): Promise<void> {
    await axiosInstance.post("/auth/logout");
    tokenStorage.clear();
  },

  async me(): Promise<AuthUserResponse> {
    const response = await axiosInstance.get<AuthUserResponse>("/auth/me");
    return response.data;
  },
};
