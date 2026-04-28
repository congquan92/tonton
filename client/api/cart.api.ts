import { axiosInstance } from "@/lib/axios";
import type { CartItemQuantityRequest, CartItemUpsertRequest } from "@/types/request";
import type { CartResponse } from "@/types/response";

export const cartApi = {
    async getMyCart(): Promise<CartResponse> {
        const response = await axiosInstance.get<CartResponse>("/cart/me");
        return response.data;
    },

    async addItem(payload: CartItemUpsertRequest): Promise<CartResponse> {
        const response = await axiosInstance.post<CartResponse>("/cart/items", payload);
        return response.data;
    },

    async updateItemQuantity(itemId: number, payload: CartItemQuantityRequest): Promise<CartResponse> {
        const response = await axiosInstance.patch<CartResponse>("/cart/items/" + itemId, payload);
        return response.data;
    },

    async removeItem(itemId: number): Promise<CartResponse> {
        const response = await axiosInstance.delete<CartResponse>("/cart/items/" + itemId);
        return response.data;
    },

    async clearMyCart(): Promise<void> {
        await axiosInstance.delete("/cart/clear");
    },
};
