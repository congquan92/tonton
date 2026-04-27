import { createCrudApi } from "@/api/core/crud.api";
import type { UomRequest } from "@/types/request";
import type { UomResponse } from "@/types/response";

export const uomsApi = createCrudApi<UomRequest, UomResponse>("/uoms");
