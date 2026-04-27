import { createCrudApi } from "@/api/core/crud.api";
import type { RoleRequest } from "@/types/request";
import type { RoleResponse } from "@/types/response";

export const rolesApi = createCrudApi<RoleRequest, RoleResponse>("/roles");
