import { axiosInstance } from "@/lib/axios";
import type { UserRequest } from "@/types/request";
import type { UserResponse } from "@/types/response";

export const adminUsersApi = {
  async list(): Promise<UserResponse[]> {
    const response = await axiosInstance.get<UserResponse[]>("/users");
    return response.data;
  },

  async getById(id: number): Promise<UserResponse> {
    const response = await axiosInstance.get<UserResponse>("/users/" + id);
    return response.data;
  },

  async create(payload: UserRequest): Promise<UserResponse> {
    const response = await axiosInstance.post<UserResponse>("/users", payload);
    return response.data;
  },

  async update(id: number, payload: UserRequest): Promise<UserResponse> {
    const response = await axiosInstance.put<UserResponse>("/users/" + id, payload);
    return response.data;
  },

  async remove(id: number): Promise<void> {
    await axiosInstance.delete("/users/" + id);
  },
};
