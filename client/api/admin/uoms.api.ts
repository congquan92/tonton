import { axiosInstance } from "@/lib/axios";
import type { UomRequest } from "@/types/request";
import type { UomResponse } from "@/types/response";

export const adminUomsApi = {
    async list(): Promise<UomResponse[]> {
        const response = await axiosInstance.get<UomResponse[]>("/uoms");
        return response.data;
    },

    async getById(id: number): Promise<UomResponse> {
        const response = await axiosInstance.get<UomResponse>("/uoms/" + id);
        return response.data;
    },

    async create(payload: UomRequest): Promise<UomResponse> {
        const response = await axiosInstance.post<UomResponse>("/uoms", payload);
        return response.data;
    },

    async update(id: number, payload: UomRequest): Promise<UomResponse> {
        const response = await axiosInstance.put<UomResponse>("/uoms/" + id, payload);
        return response.data;
    },

    async remove(id: number): Promise<void> {
        await axiosInstance.delete("/uoms/" + id);
    },
};
