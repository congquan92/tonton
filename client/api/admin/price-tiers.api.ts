import { axiosInstance } from "@/lib/axios";
import type { PriceTierRequest } from "@/types/request";
import type { PriceTierResponse } from "@/types/response";

export const adminPriceTiersApi = {
  async list(): Promise<PriceTierResponse[]> {
    const response = await axiosInstance.get<PriceTierResponse[]>("/price-tiers");
    return response.data;
  },

  async getById(id: number): Promise<PriceTierResponse> {
    const response = await axiosInstance.get<PriceTierResponse>("/price-tiers/" + id);
    return response.data;
  },

  async create(payload: PriceTierRequest): Promise<PriceTierResponse> {
    const response = await axiosInstance.post<PriceTierResponse>("/price-tiers", payload);
    return response.data;
  },

  async update(id: number, payload: PriceTierRequest): Promise<PriceTierResponse> {
    const response = await axiosInstance.put<PriceTierResponse>("/price-tiers/" + id, payload);
    return response.data;
  },

  async remove(id: number): Promise<void> {
    await axiosInstance.delete("/price-tiers/" + id);
  },
};
