import { axiosInstance } from "@/lib/axios";
import type { PublicPostsQuery, PublicProductsQuery, PublicProjectsQuery } from "@/types/request";
import type {
    PublicHomeResponse,
    PublicPostDetailResponse,
    PublicPostsPageResponse,
    PublicProductDetailResponse,
    PublicProductsPageResponse,
    PublicProjectDetailResponse,
    PublicProjectsPageResponse,
    PublicServiceFeatureResponse,
} from "@/types/response";

export const publicApi = {
    async getHomeContent(): Promise<PublicHomeResponse> {
        const response = await axiosInstance.get<PublicHomeResponse>("/public/home");
        return response.data;
    },

    async getProducts(params?: PublicProductsQuery): Promise<PublicProductsPageResponse> {
        const response = await axiosInstance.get<PublicProductsPageResponse>("/public/products", { params });
        return response.data;
    },

    async getProductById(id: number): Promise<PublicProductDetailResponse> {
        const response = await axiosInstance.get<PublicProductDetailResponse>("/public/products/" + id);
        return response.data;
    },

    async getPosts(params?: PublicPostsQuery): Promise<PublicPostsPageResponse> {
        const response = await axiosInstance.get<PublicPostsPageResponse>("/public/posts", { params });
        return response.data;
    },

    async getPostBySlug(slug: string): Promise<PublicPostDetailResponse> {
        const response = await axiosInstance.get<PublicPostDetailResponse>("/public/posts/" + slug);
        return response.data;
    },

    async getProjects(params?: PublicProjectsQuery): Promise<PublicProjectsPageResponse> {
        const response = await axiosInstance.get<PublicProjectsPageResponse>("/public/projects", { params });
        return response.data;
    },

    async getProjectBySlug(slug: string): Promise<PublicProjectDetailResponse> {
        const response = await axiosInstance.get<PublicProjectDetailResponse>("/public/projects/" + slug);
        return response.data;
    },

    async getServices(): Promise<PublicServiceFeatureResponse[]> {
        const response = await axiosInstance.get<PublicServiceFeatureResponse[]>("/public/services");
        return response.data;
    },

    async getServiceBySlug(slug: string): Promise<PublicServiceFeatureResponse> {
        const response = await axiosInstance.get<PublicServiceFeatureResponse>("/public/services/" + slug);
        return response.data;
    },
};
