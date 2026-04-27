import { axiosInstance } from "@/lib/axios";
import type { QuoteRequest } from "@/types/request";
import type { QuoteResponse } from "@/types/response";

export const adminQuotesApi = {
  async list(): Promise<QuoteResponse[]> {
    const response = await axiosInstance.get<QuoteResponse[]>("/quotes");
    return response.data;
  },

  async getById(id: number): Promise<QuoteResponse> {
    const response = await axiosInstance.get<QuoteResponse>("/quotes/" + id);
    return response.data;
  },

  async create(payload: QuoteRequest): Promise<QuoteResponse> {
    const response = await axiosInstance.post<QuoteResponse>("/quotes", payload);
    return response.data;
  },

  async update(id: number, payload: QuoteRequest): Promise<QuoteResponse> {
    const response = await axiosInstance.put<QuoteResponse>("/quotes/" + id, payload);
    return response.data;
  },

  async remove(id: number): Promise<void> {
    await axiosInstance.delete("/quotes/" + id);
  },
};
