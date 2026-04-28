import { axiosInstance } from "@/lib/axios";
import type { CheckoutRequest } from "@/types/request";
import type { CheckoutResponse } from "@/types/response";

export const checkoutApi = {
    async placeOrder(payload: CheckoutRequest): Promise<CheckoutResponse> {
        const response = await axiosInstance.post<CheckoutResponse>("/checkout", payload);
        return response.data;
    },
};
