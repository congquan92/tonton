import { axiosInstance } from "@/lib/axios";
import type { RoleRequest } from "@/types/request";
import type { RoleResponse } from "@/types/response";

export const adminRolesApi = {
  async list(): Promise<RoleResponse[]> {
    const response = await axiosInstance.get<RoleResponse[]>("/roles");
    return response.data;
  },

  async getById(id: number): Promise<RoleResponse> {
    const response = await axiosInstance.get<RoleResponse>("/roles/" + id);
    return response.data;
  },

  async create(payload: RoleRequest): Promise<RoleResponse> {
    const response = await axiosInstance.post<RoleResponse>("/roles", payload);
    return response.data;
  },

  async update(id: number, payload: RoleRequest): Promise<RoleResponse> {
    const response = await axiosInstance.put<RoleResponse>("/roles/" + id, payload);
    return response.data;
  },

  async remove(id: number): Promise<void> {
    await axiosInstance.delete("/roles/" + id);
  },
};
