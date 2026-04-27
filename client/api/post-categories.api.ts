import { createCrudApi } from "@/api/core/crud.api";
import type { PostCategoryRequest } from "@/types/request";
import type { PostCategoryResponse } from "@/types/response";

export const postCategoriesApi = createCrudApi<PostCategoryRequest, PostCategoryResponse>(
  "/post-categories",
);
