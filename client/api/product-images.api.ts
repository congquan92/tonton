import { createCrudApi } from "@/api/core/crud.api";
import type { ProductImageRequest } from "@/types/request";
import type { ProductImageResponse } from "@/types/response";

export const productImagesApi = createCrudApi<ProductImageRequest, ProductImageResponse>(
  "/product-images",
);
