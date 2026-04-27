import { axiosInstance } from "@/lib/axios";
import type { PostCategoryRequest } from "@/types/request";
import type { PostCategoryResponse } from "@/types/response";

export const adminPostCategoriesApi = {
  async list(): Promise<PostCategoryResponse[]> {
    const response = await axiosInstance.get<PostCategoryResponse[]>("/post-categories");
    return response.data;
  },

  async getById(id: number): Promise<PostCategoryResponse> {
    const response = await axiosInstance.get<PostCategoryResponse>("/post-categories/" + id);
    return response.data;
  },

  async create(payload: PostCategoryRequest): Promise<PostCategoryResponse> {
    const response = await axiosInstance.post<PostCategoryResponse>("/post-categories", payload);
    return response.data;
  },

  async update(id: number, payload: PostCategoryRequest): Promise<PostCategoryResponse> {
    const response = await axiosInstance.put<PostCategoryResponse>("/post-categories/" + id, payload);
    return response.data;
  },

  async remove(id: number): Promise<void> {
    await axiosInstance.delete("/post-categories/" + id);
  },
};
