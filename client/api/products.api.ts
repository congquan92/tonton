import { createCrudApi } from "@/api/core/crud.api";
import type { ProductRequest } from "@/types/request";
import type { ProductResponse } from "@/types/response";

export const productsApi = createCrudApi<ProductRequest, ProductResponse>("/products");
