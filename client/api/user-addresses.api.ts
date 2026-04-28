import { axiosInstance } from "@/lib/axios";
import type { UserAddressRequest } from "@/types/request";
import type { UserAddressResponse } from "@/types/response";

export const userAddressesApi = {
    async list(): Promise<UserAddressResponse[]> {
        const response = await axiosInstance.get<UserAddressResponse[]>("/user-addresses");
        return response.data;
    },

    async getById(id: number): Promise<UserAddressResponse> {
        const response = await axiosInstance.get<UserAddressResponse>("/user-addresses/" + id);
        return response.data;
    },

    async create(payload: UserAddressRequest): Promise<UserAddressResponse> {
        const response = await axiosInstance.post<UserAddressResponse>("/user-addresses", payload);
        return response.data;
    },

    async update(id: number, payload: UserAddressRequest): Promise<UserAddressResponse> {
        const response = await axiosInstance.put<UserAddressResponse>("/user-addresses/" + id, payload);
        return response.data;
    },

    async remove(id: number): Promise<void> {
        await axiosInstance.delete("/user-addresses/" + id);
    },
};
