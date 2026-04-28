import { axiosInstance } from "@/lib/axios";
import type { OrderItemRequest } from "@/types/request";
import type { OrderItemResponse } from "@/types/response";

export const adminOrderItemsApi = {
    async list(): Promise<OrderItemResponse[]> {
        const response = await axiosInstance.get<OrderItemResponse[]>("/order-items");
        return response.data;
    },

    async getById(id: number): Promise<OrderItemResponse> {
        const response = await axiosInstance.get<OrderItemResponse>("/order-items/" + id);
        return response.data;
    },

    async create(payload: OrderItemRequest): Promise<OrderItemResponse> {
        const response = await axiosInstance.post<OrderItemResponse>("/order-items", payload);
        return response.data;
    },

    async update(id: number, payload: OrderItemRequest): Promise<OrderItemResponse> {
        const response = await axiosInstance.put<OrderItemResponse>("/order-items/" + id, payload);
        return response.data;
    },

    async remove(id: number): Promise<void> {
        await axiosInstance.delete("/order-items/" + id);
    },
};
