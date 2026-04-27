import { createCrudApi } from "@/api/core/crud.api";
import type { PriceTierRequest } from "@/types/request";
import type { PriceTierResponse } from "@/types/response";

export const priceTiersApi = createCrudApi<PriceTierRequest, PriceTierResponse>("/price-tiers");
