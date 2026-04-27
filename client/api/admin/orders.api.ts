import { axiosInstance } from "@/lib/axios";
import type { OrderRequest } from "@/types/request";
import type { OrderResponse } from "@/types/response";

export const adminOrdersApi = {
  async list(): Promise<OrderResponse[]> {
    const response = await axiosInstance.get<OrderResponse[]>("/orders");
    return response.data;
  },

  async getById(id: number): Promise<OrderResponse> {
    const response = await axiosInstance.get<OrderResponse>("/orders/" + id);
    return response.data;
  },

  async create(payload: OrderRequest): Promise<OrderResponse> {
    const response = await axiosInstance.post<OrderResponse>("/orders", payload);
    return response.data;
  },

  async update(id: number, payload: OrderRequest): Promise<OrderResponse> {
    const response = await axiosInstance.put<OrderResponse>("/orders/" + id, payload);
    return response.data;
  },

  async remove(id: number): Promise<void> {
    await axiosInstance.delete("/orders/" + id);
  },
};
