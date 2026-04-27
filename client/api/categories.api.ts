import { createCrudApi } from "@/api/core/crud.api";
import type { CategoryRequest } from "@/types/request";
import type { CategoryResponse } from "@/types/response";

export const categoriesApi = createCrudApi<CategoryRequest, CategoryResponse>("/categories");
