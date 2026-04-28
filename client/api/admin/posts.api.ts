import { axiosInstance } from "@/lib/axios";
import type { PostRequest } from "@/types/request";
import type { PostResponse } from "@/types/response";

export const adminPostsApi = {
    async list(): Promise<PostResponse[]> {
        const response = await axiosInstance.get<PostResponse[]>("/posts");
        return response.data;
    },

    async getById(id: number): Promise<PostResponse> {
        const response = await axiosInstance.get<PostResponse>("/posts/" + id);
        return response.data;
    },

    async create(payload: PostRequest): Promise<PostResponse> {
        const response = await axiosInstance.post<PostResponse>("/posts", payload);
        return response.data;
    },

    async update(id: number, payload: PostRequest): Promise<PostResponse> {
        const response = await axiosInstance.put<PostResponse>("/posts/" + id, payload);
        return response.data;
    },

    async remove(id: number): Promise<void> {
        await axiosInstance.delete("/posts/" + id);
    },
};
