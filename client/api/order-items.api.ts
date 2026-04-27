import { createCrudApi } from "@/api/core/crud.api";
import type { OrderItemRequest } from "@/types/request";
import type { OrderItemResponse } from "@/types/response";

export const orderItemsApi = createCrudApi<OrderItemRequest, OrderItemResponse>("/order-items");
