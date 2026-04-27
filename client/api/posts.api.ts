import { createCrudApi } from "@/api/core/crud.api";
import type { PostRequest } from "@/types/request";
import type { PostResponse } from "@/types/response";

export const postsApi = createCrudApi<PostRequest, PostResponse>("/posts");
