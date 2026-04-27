import { createCrudApi } from "@/api/core/crud.api";
import type { OrderRequest } from "@/types/request";
import type { OrderResponse } from "@/types/response";

export const ordersApi = createCrudApi<OrderRequest, OrderResponse>("/orders");
