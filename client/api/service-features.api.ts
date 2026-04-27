import { createCrudApi } from "@/api/core/crud.api";
import type { ServiceFeatureRequest } from "@/types/request";
import type { ServiceFeatureResponse } from "@/types/response";

export const serviceFeaturesApi = createCrudApi<ServiceFeatureRequest, ServiceFeatureResponse>(
  "/service-features",
);
