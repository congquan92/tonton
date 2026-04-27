import { axiosInstance } from "@/lib/axios";
import type { ProductRequest } from "@/types/request";
import type { ProductResponse } from "@/types/response";

export const adminProductsApi = {
  async list(): Promise<ProductResponse[]> {
    const response = await axiosInstance.get<ProductResponse[]>("/products");
    return response.data;
  },

  async getById(id: number): Promise<ProductResponse> {
    const response = await axiosInstance.get<ProductResponse>("/products/" + id);
    return response.data;
  },

  async create(payload: ProductRequest): Promise<ProductResponse> {
    const response = await axiosInstance.post<ProductResponse>("/products", payload);
    return response.data;
  },

  async update(id: number, payload: ProductRequest): Promise<ProductResponse> {
    const response = await axiosInstance.put<ProductResponse>("/products/" + id, payload);
    return response.data;
  },

  async remove(id: number): Promise<void> {
    await axiosInstance.delete("/products/" + id);
  },
};
