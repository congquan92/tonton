import { createCrudApi } from "@/api/core/crud.api";
import type { UserRequest } from "@/types/request";
import type { UserResponse } from "@/types/response";

export const usersApi = createCrudApi<UserRequest, UserResponse>("/users");
