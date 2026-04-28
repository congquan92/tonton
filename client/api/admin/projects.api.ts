import { axiosInstance } from "@/lib/axios";
import type { ProjectRequest } from "@/types/request";
import type { ProjectResponse } from "@/types/response";

export const adminProjectsApi = {
    async list(): Promise<ProjectResponse[]> {
        const response = await axiosInstance.get<ProjectResponse[]>("/projects");
        return response.data;
    },

    async getById(id: number): Promise<ProjectResponse> {
        const response = await axiosInstance.get<ProjectResponse>("/projects/" + id);
        return response.data;
    },

    async create(payload: ProjectRequest): Promise<ProjectResponse> {
        const response = await axiosInstance.post<ProjectResponse>("/projects", payload);
        return response.data;
    },

    async update(id: number, payload: ProjectRequest): Promise<ProjectResponse> {
        const response = await axiosInstance.put<ProjectResponse>("/projects/" + id, payload);
        return response.data;
    },

    async remove(id: number): Promise<void> {
        await axiosInstance.delete("/projects/" + id);
    },
};
