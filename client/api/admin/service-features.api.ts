import { axiosInstance } from "@/lib/axios";
import type { ServiceFeatureRequest } from "@/types/request";
import type { ServiceFeatureResponse } from "@/types/response";

export const adminServiceFeaturesApi = {
    async list(): Promise<ServiceFeatureResponse[]> {
        const response = await axiosInstance.get<ServiceFeatureResponse[]>("/service-features");
        return response.data;
    },

    async getById(id: number): Promise<ServiceFeatureResponse> {
        const response = await axiosInstance.get<ServiceFeatureResponse>("/service-features/" + id);
        return response.data;
    },

    async create(payload: ServiceFeatureRequest): Promise<ServiceFeatureResponse> {
        const response = await axiosInstance.post<ServiceFeatureResponse>("/service-features", payload);
        return response.data;
    },

    async update(id: number, payload: ServiceFeatureRequest): Promise<ServiceFeatureResponse> {
        const response = await axiosInstance.put<ServiceFeatureResponse>("/service-features/" + id, payload);
        return response.data;
    },

    async remove(id: number): Promise<void> {
        await axiosInstance.delete("/service-features/" + id);
    },
};
