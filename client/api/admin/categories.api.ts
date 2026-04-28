import { axiosInstance } from "@/lib/axios";
import type { CategoryRequest } from "@/types/request";
import type { CategoryResponse } from "@/types/response";

export const adminCategoriesApi = {
    async list(): Promise<CategoryResponse[]> {
        const response = await axiosInstance.get<CategoryResponse[]>("/categories");
        return response.data;
    },

    async getById(id: number): Promise<CategoryResponse> {
        const response = await axiosInstance.get<CategoryResponse>("/categories/" + id);
        return response.data;
    },

    async create(payload: CategoryRequest): Promise<CategoryResponse> {
        const response = await axiosInstance.post<CategoryResponse>("/categories", payload);
        return response.data;
    },

    async update(id: number, payload: CategoryRequest): Promise<CategoryResponse> {
        const response = await axiosInstance.put<CategoryResponse>("/categories/" + id, payload);
        return response.data;
    },

    async remove(id: number): Promise<void> {
        await axiosInstance.delete("/categories/" + id);
    },
};
