import { axiosInstance } from "@/lib/axios";
import type { ProductImageRequest } from "@/types/request";
import type { ProductImageResponse } from "@/types/response";

export const adminProductImagesApi = {
  async list(): Promise<ProductImageResponse[]> {
    const response = await axiosInstance.get<ProductImageResponse[]>("/product-images");
    return response.data;
  },

  async getById(id: number): Promise<ProductImageResponse> {
    const response = await axiosInstance.get<ProductImageResponse>("/product-images/" + id);
    return response.data;
  },

  async create(payload: ProductImageRequest): Promise<ProductImageResponse> {
    const response = await axiosInstance.post<ProductImageResponse>("/product-images", payload);
    return response.data;
  },

  async update(id: number, payload: ProductImageRequest): Promise<ProductImageResponse> {
    const response = await axiosInstance.put<ProductImageResponse>("/product-images/" + id, payload);
    return response.data;
  },

  async remove(id: number): Promise<void> {
    await axiosInstance.delete("/product-images/" + id);
  },
};
