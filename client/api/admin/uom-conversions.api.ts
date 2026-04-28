import { axiosInstance } from "@/lib/axios";
import type { UomConversionRequest } from "@/types/request";
import type { UomConversionResponse } from "@/types/response";

export const adminUomConversionsApi = {
    async list(): Promise<UomConversionResponse[]> {
        const response = await axiosInstance.get<UomConversionResponse[]>("/uom-conversions");
        return response.data;
    },

    async getById(id: number): Promise<UomConversionResponse> {
        const response = await axiosInstance.get<UomConversionResponse>("/uom-conversions/" + id);
        return response.data;
    },

    async create(payload: UomConversionRequest): Promise<UomConversionResponse> {
        const response = await axiosInstance.post<UomConversionResponse>("/uom-conversions", payload);
        return response.data;
    },

    async update(id: number, payload: UomConversionRequest): Promise<UomConversionResponse> {
        const response = await axiosInstance.put<UomConversionResponse>("/uom-conversions/" + id, payload);
        return response.data;
    },

    async remove(id: number): Promise<void> {
        await axiosInstance.delete("/uom-conversions/" + id);
    },
};
