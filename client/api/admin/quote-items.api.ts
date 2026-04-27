import { axiosInstance } from "@/lib/axios";
import type { QuoteItemRequest } from "@/types/request";
import type { QuoteItemResponse } from "@/types/response";

export const adminQuoteItemsApi = {
  async list(): Promise<QuoteItemResponse[]> {
    const response = await axiosInstance.get<QuoteItemResponse[]>("/quote-items");
    return response.data;
  },

  async getById(id: number): Promise<QuoteItemResponse> {
    const response = await axiosInstance.get<QuoteItemResponse>("/quote-items/" + id);
    return response.data;
  },

  async create(payload: QuoteItemRequest): Promise<QuoteItemResponse> {
    const response = await axiosInstance.post<QuoteItemResponse>("/quote-items", payload);
    return response.data;
  },

  async update(id: number, payload: QuoteItemRequest): Promise<QuoteItemResponse> {
    const response = await axiosInstance.put<QuoteItemResponse>("/quote-items/" + id, payload);
    return response.data;
  },

  async remove(id: number): Promise<void> {
    await axiosInstance.delete("/quote-items/" + id);
  },
};
